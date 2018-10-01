package com.example.mostafa.ronixtechtask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class RegisterActivity extends AppCompatActivity {

    //The view objects
    EditText first_name, last_name, email, phone_number, password, confirm_password;

    //Check validation object
    private boolean check;

    //DataBase object
    DataBase_Users helper = new DataBase_Users(this);

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //initializing awesomevalidation object
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //initializing view objects
        first_name = findViewById(R.id.first_name);

        last_name = findViewById(R.id.last_name);

        email = findViewById(R.id.register_email);

        phone_number = findViewById(R.id.phone);

        password = findViewById(R.id.register_password);

        confirm_password = findViewById(R.id.register_confirm_password);

        //adding validation to edittexts
        awesomeValidation.addValidation(this,
                R.id.first_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",
                R.string.err_name);

        awesomeValidation.addValidation(this,
                R.id.last_name,
                "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",
                R.string.err_name);

        awesomeValidation.addValidation(this,
                R.id.register_email,
                Patterns.EMAIL_ADDRESS,
                R.string.err_email);

        awesomeValidation.addValidation(this,
                R.id.phone,
                "^[0-9]{2}[0-9]{8}$",
                R.string.err_tel);

        awesomeValidation.addValidation(this,
                R.id.register_password,
                "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",
                R.string.err_password);

        awesomeValidation.addValidation(this,
                R.id.register_confirm_password,
                R.id.register_password,
                R.string.err_password_confirmation);

    }

    private boolean submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successful
        if (awesomeValidation.validate()) {

            helper.insert(first_name.getText().toString(),
                    last_name.getText().toString(),
                    email.getText().toString(),
                    phone_number.getText().toString(),
                    password.getText().toString(),
                    confirm_password.getText().toString());

            Toast.makeText(this, "Registration Successfully", Toast.LENGTH_LONG).show();

            check = true;

        } else {

            check = false;

            Toast.makeText(this, "Registration UnSuccessfully", Toast.LENGTH_LONG).show();

        }

        return check;
    }

    public void openLogin(View view) {

        Intent login =new Intent (this , MainActivity.class);
        startActivity(login);

    }

    public void Register(View view) {

        boolean check = submitForm();

        if (check) {

            Intent login =new Intent (this , MainActivity.class);
            startActivity(login);

        }

    }

}
