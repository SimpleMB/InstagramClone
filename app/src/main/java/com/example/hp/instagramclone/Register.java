package com.example.hp.instagramclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.ParseException;
import com.parse.ParseUser;



public class Register extends AppCompatActivity {

    private EditText userName, userPass;
    private TextView status;
    private Button btnRegister, btnLogin, btnLogout, btnAddValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        userName =      findViewById(R.id.userName);
        userPass =      findViewById(R.id.userPass);
        status =        findViewById(R.id.status);
        btnRegister =   findViewById(R.id.btnRegister);
        btnLogin =      findViewById(R.id.btnLogin);
        btnLogout =     findViewById(R.id.btnLogout);
        btnAddValue=    findViewById(R.id.btnAddValue);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameReg = userName.getText().toString();
                String userPassReg = userPass.getText().toString();
                ParseUser user = new ParseUser();
// Set the user's username and password, which can be obtained by a forms
                user.setUsername(userNameReg);
                user.setPassword(userPassReg);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            status.setText("Registration successful");
                            userName.setText("");
                            userPass.setText("");
                        } else {
                            ParseUser.logOut();
                            status.setText(e.getMessage());
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userNameLogin = userName.getText().toString();
                String userPassLogin = userPass.getText().toString();
                ParseUser.logInInBackground(userNameLogin,userPassLogin, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null && e == null) {
                            status.setText("Welcome back " +userNameLogin);
                            userName.setText("");
                            userPass.setText("");
                        } else {
                            ParseUser.logOut();
                            status.setText(e.getMessage());
                        }
                    }
                });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            status.setText("Logout successful");
                            userName.setText("");
                            userPass.setText("");
                        } else {
                            ParseUser.logOut();
                            status.setText(e.getMessage());
                        }
                    }
                });

            }
        });

        btnAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseObject value = new ParseObject("Value");
                value.put("UserName",userName.getText().toString());
                value.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null) {
                            Toast.makeText(Register.this, "New User: "+value.get("UserName")+ "\n" + "Saved successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


}
