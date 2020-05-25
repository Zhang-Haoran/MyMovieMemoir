package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymoviememoir.networkconnection.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signin extends AppCompatActivity {

    private String userInputUsername;
    private String userInputPassword;
    private EditText userName;
    private EditText password;
    private String nameOfUser;
    private int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Button signinButton = findViewById(R.id.signinButton);
        Button signupButton = findViewById(R.id.signupButton);
        userName = findViewById(R.id.usernameInputField);
        password = findViewById(R.id.passwordInputField);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInputUsername = userName.getText().toString();
                userInputPassword = password.getText().toString();
                //signin button validation
                if (userInputUsername.isEmpty()){
                    userName.setError("username input can not be empty");
                    Toast.makeText(Signin.this,"Please check your username",Toast.LENGTH_SHORT).show();
                }
                else {
                    new signinAsyncTask().execute(userInputUsername,userInputPassword);
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, Signup.class);
                startActivity(intent);
            }
        });

    }
    //to avoid time consuming when user is waiting to have multiple thread
    private class signinAsyncTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = md5(strings[1]);
            return RestClient.findByUsernameAndPassword(username,password);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //if the given username and password cannot find a pair in database return []. therefore, to check them here.
            if (result.equals("[]") || result.equals("")){
                userName.setError("Incorrect login credential");
                password.setError("Incorrect login credential");
                Toast.makeText(Signin.this,"Incorrect login credential",Toast.LENGTH_SHORT).show();

            }
            else {
                try {
                    //get username and userid pass it to home
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i< jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject useridJsonString = jsonObject.getJSONObject("userid");
                        nameOfUser = useridJsonString.getString("name");
                        userid = useridJsonString.getInt("userid");
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                //pass value to home
                Intent intent = new Intent(Signin.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameOfUser", nameOfUser);
                bundle.putInt("userid",userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
    //use md5 to hash the password
    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
