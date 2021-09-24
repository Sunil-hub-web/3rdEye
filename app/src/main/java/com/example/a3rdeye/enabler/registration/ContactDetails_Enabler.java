package com.example.a3rdeye.enabler.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;

public class ContactDetails_Enabler extends AppCompatActivity {

    Button btn_submit;

    EditText first_name, last_name, text_MobileNo;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_details__enabler);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btn_submit = findViewById(R.id.submit);
        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        text_MobileNo = findViewById(R.id.mobileno);

        awesomeValidation.addValidation(ContactDetails_Enabler.this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.fnameerror);
        awesomeValidation.addValidation(ContactDetails_Enabler.this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(ContactDetails_Enabler.this, R.id.mobileno, "^[1-9]{2}[0-9]{8}$", R.string.mobilenoerror);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    // Here, we are sure that form is successfully validated. So, do your stuffs now...
                    if (text_MobileNo.getText().toString().length() != 10) {

                        text_MobileNo.setError("valid mobile number");
                    } else {

                        Intent intent = new Intent(ContactDetails_Enabler.this, CreateAccount_Enabler.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(ContactDetails_Enabler.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}