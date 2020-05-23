package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviememoir.entity.Credentialstable;
import com.example.mymoviememoir.entity.Usertable;
import com.example.mymoviememoir.networkconnection.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Signup extends AppCompatActivity {
    private String gender = "Male";
    private String stateInput = "";
    private String signupDate;
    private String DOB;
    private int userCount; //count the number of user to calculate the new user id
    private Map<String, EditText> etMap = new HashMap<>(); //use hashmap to store key value pair
    private List<String> usernameList = new ArrayList<>(); //get all current username to check if userinput username is already existing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupButtton = findViewById(R.id.signinConfirmButton);
        Button datetimePickerButton = findViewById(R.id.dobPickerButton);
        //hashmap key value pair
        etMap.put("emailInputField",(EditText) findViewById(R.id.emailInputField));
        etMap.put("passwordInputField",(EditText) findViewById(R.id.passwordInputField));
        etMap.put("firstnameInputField",(EditText) findViewById(R.id.firstnameInputField));
        etMap.put("surnameInputField",(EditText) findViewById(R.id.surnameInputField));
        etMap.put("addressInputField",(EditText) findViewById(R.id.addressInputField));
        etMap.put("postcodeInputField",(EditText) findViewById(R.id.postcodeInputField));
        Calendar calendar = Calendar.getInstance();// get a calendar using the current time zone and locale of the system. For example today is 10/05/2020, it will get 10,4, 2020
        final int calendarYear = calendar.get(Calendar.YEAR);
        final int calendarMonth = calendar.get(Calendar.MONTH);// month from calendar starts from 0 end in 11. it needs to + 1 to represent current month
        final int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        //Formatting date. add 0 at front for month or day which is too short to store into database. to store into database, we need to change 2020-1-1 to 2020-01-01
        signupDate = dateFormatting(calendarYear,calendarMonth,calendarDay);
        final TextView datePickerResultTextView = findViewById(R.id.datepickerResultTextView);
        final Spinner stateSpinnerTextView = findViewById(R.id.stateSpinner);
        //get all the user name into a list to check if user input the existing username
        new AsyncGetUsername().execute();

        //radio group
        RadioGroup radioGroup = findViewById(R.id.genderRaidoGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton.getText().equals("Male")){
                    gender = "Male";
                }
                else {
                    gender = "Female";
                }
            }
        });

        //spinner
        List<String> list = new ArrayList<>(Arrays.asList("VIC","SA","TAS","WA","NT","QLD","NSW","ACT"));
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        stateSpinnerTextView.setAdapter(spinnerAdapter);
        stateSpinnerTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateInput = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //date time picker
        datetimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Day of birth
                DatePickerDialog datePickerDialog = new DatePickerDialog(Signup.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        DOB = dateFormatting(year,month,day);
                        datePickerResultTextView.setText(DOB);
                    }
                },calendarYear,calendarMonth,calendarDay); //set current day, month, year into datepicker

                DatePicker dp = datePickerDialog.getDatePicker();//initialize datepicker to set max date, get the datetime that user picked
                dp.setMaxDate(System.currentTimeMillis()); //dob is no more than current date
                datePickerDialog.show();
            }

        });

        //validation
        //check if username exist
        signupButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inputValidation = true;
                String userInputUserName = etMap.get("emailInputField").getText().toString();
                String userInputFirstName = etMap.get("firstnameInputField").getText().toString();
                String userInputSurname = etMap.get("surnameInputField").getText().toString();
                String userInputAddress = etMap.get("addressInputField").getText().toString();
                String userInputPostcode = etMap.get("postcodeInputField").getText().toString();

                //check if input is empty
                if (userInputUserName.isEmpty()){
                    etMap.get("emailInputField").setError("The username is empty");
                    inputValidation = false;
                }
                if (userInputFirstName.isEmpty()){
                    etMap.get("firstnameInputField").setError("The first name is empty");
                    inputValidation = false;
                }
                if (userInputSurname.isEmpty()){
                    etMap.get("surnameInputField").setError("The surname is empty");
                    inputValidation = false;
                }
                if (userInputAddress.isEmpty()){
                    etMap.get("addressInputField").setError("The address is empty");
                    inputValidation = false;
                }
                if (userInputPostcode.isEmpty()){
                    etMap.get("postcodeInputField").setError("The postcode is empty");
                    inputValidation = false;
                }

                //check if there is any username in the list is same as user's input
                for (int i = 0; i < usernameList.size(); i++) {
                    if (userInputUserName.equals(usernameList.get(i))) { //check redundant username
                        etMap.get("emailInputField").setError("The username exists");
                        inputValidation = false;
                    }
                }
                
                if (inputValidation){
                    Usertable usertable = new Usertable();
                    usertable.setUserid(userCount+1);//the new userid should be current total number plus 1
                    usertable.setName(userInputFirstName);
                    usertable.setSurname(userInputSurname);
                    usertable.setAddress(userInputAddress);
                    usertable.setPostcode(userInputPostcode);
                    usertable.setDob(DOB);
                    usertable.setGender(gender);
                    usertable.setState(stateInput);
                    new AsyncPostUsertable().execute(usertable);//post the new usertable data into database
                    Intent intent = new Intent(Signup.this, Signin.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Signup.this,"Please check your input",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    //formatting date
    public String dateFormatting(int year, int month, int day){
        String result ="";
        int currentmonth = month + 1; //month from datepicker is current month -1, therefore, we need to add 1 back
        String monthFormatting = String.valueOf(currentmonth);
        String dayFormatting = String.valueOf(day);
        if (monthFormatting.length() == 1){
            monthFormatting = "0" + monthFormatting;//append the month at the end of stringBuffer which is after 0
        }
        if (dayFormatting.length() == 1){
            dayFormatting = 0 + dayFormatting;
        }
        result = year + "-" + monthFormatting + '-' + dayFormatting + "T00:00:00+00:00";
        return result;
    }


    //get current user id by counting the number of existing id
    //use AsyncTask to avoid time consuming when user is waiting to have multiple thread
    private class AsyncCalculateCurrentUserid extends android.os.AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void...voids)  {
            final String methodPath = "fit5046assignment1.usertable/count/";
            //initialise
            URL url = null;
            HttpURLConnection conn = null;
            String result = "";
            //Making HTTP request
            try {
                url = new URL(RestClient.BASE_URL + methodPath);
                //open the connection
                conn = (HttpURLConnection) url.openConnection();
                //set the timeout
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                //set the connection method to GET
                conn.setRequestMethod("GET");
                //add http headers to set your response type to text
                conn.setRequestProperty("Content-Type", "text/plain");
                conn.setRequestProperty("Accept", "text/plain");
                //Read the response
                Scanner inStream = new Scanner(conn.getInputStream());
                //read the input stream and store it as string
                while (inStream.hasNextLine()) {
                    result += inStream.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result){
            userCount = Integer.parseInt(result);//get current counting number of userid
        }
    }

    //get all the username to check if user enter existing username. record all username into list
    private class AsyncGetUsername extends android.os.AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void...voids)  {
            String result = RestClient.findAllUsername();
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i< jsonArray.length();i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    usernameList.add(jsonObject.getString("username"));
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }

        //to set asynctask order, put next asynctask in the post execute
        @Override
        protected void onPostExecute(Void nothing){
            AsyncCalculateCurrentUserid asyncCalculateCurrentUserid = new AsyncCalculateCurrentUserid();
            asyncCalculateCurrentUserid.execute();
        }
    }

    //post new user data into database
    private class AsyncPostUsertable extends android.os.AsyncTask<Usertable,Void,Usertable> {
        @Override
        protected Usertable doInBackground(Usertable...usertables)  {
            RestClient.postUsertable(usertables[0]);
            return usertables[0];
        }

        //to set asynctask order, put next asynctask in the post execute
        @Override
        protected void onPostExecute(Usertable usertable) {
            Credentialstable credentialstable = new Credentialstable();
            credentialstable.setCredentialsid(usertable.getUserid());//set credentials id = user id for convenience
            credentialstable.setUserid(usertable);
            credentialstable.setUsername(etMap.get("emailInputField").getText().toString());
            credentialstable.setPasswordhash(Signin.md5(etMap.get("passwordInputField").getText().toString()));
            credentialstable.setSignupdate(signupDate);
            new AsyncPostCredentialstable().execute(credentialstable);
        }
    }

    //post new credentials data into database
    private class AsyncPostCredentialstable extends android.os.AsyncTask<Credentialstable,Void,Void> {
        @Override
        protected Void doInBackground(Credentialstable...credentialstables)  {
            RestClient.postcredentialstable(credentialstables[0]);
            return null;
        }
    }
    }

