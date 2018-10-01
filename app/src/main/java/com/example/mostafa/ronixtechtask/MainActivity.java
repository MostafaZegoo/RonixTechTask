package com.example.mostafa.ronixtechtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //The view objects
    EditText email, password;

    //The string objects
    String email_data, password_data;

    //Check login object
    private boolean check;

    //DataBase object
    DataBase_Users helper = new DataBase_Users(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        email = findViewById(R.id.login_email);

        password = findViewById(R.id.login_password);

    }

    private boolean submitLogin(String email, String password) {
        //first validate the form then move ahead
        //if this becomes true that means validation is successful

        if (helper.checkUser(email,password)) {

            Toast.makeText(this, "Log in Successfully", Toast.LENGTH_LONG).show();

            check = true;

        } else {

            check = false;

            Toast.makeText(this, "Log in UnSuccessfully", Toast.LENGTH_LONG).show();

        }

        return check;
    }

    public void openRegister(View view) {

        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);

    }

    public void Login(View view) {

        email_data = email.getText().toString();

        password_data = password.getText().toString();

        if (submitLogin(email_data, password_data)) {

            Intent register = new Intent(this, MQTTMessageActivity.class);
            startActivity(register);

        }

    }
}

