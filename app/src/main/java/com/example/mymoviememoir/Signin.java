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

public class Signin extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private String nameOfUser;
    private int userid;
    public static final String BASE_URL = "http://192.168.1.103:8080/FIT5046Assignment1/webresources/";
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
                new AsyncSignin().execute(userName.getText().toString(),password.getText().toString());
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
    private class AsyncSignin extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String username = strings[0];
            String password = strings[1];
            return RestClient.findByUsernameAndPassword(username,password);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("[]") || result.equals("")){
                Toast.makeText(Signin.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();

            }
            else {
                try {
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

                Intent intent = new Intent(Signin.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameOfUser", nameOfUser);
                bundle.putInt("userid",userid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}
