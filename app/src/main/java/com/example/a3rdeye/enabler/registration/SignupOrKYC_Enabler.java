package com.example.a3rdeye.enabler.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.BikeDetails_Enabler;

public class SignupOrKYC_Enabler extends AppCompatActivity {

    EditText edit_aadharpassport, edit_dl;
    Button btn_UploadDlPhoto, btn_submit;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_orkyc);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_aadharpassport = findViewById(R.id.passportaadhar);
        edit_dl = findViewById(R.id.dl);
        btn_UploadDlPhoto = findViewById(R.id.uploaddl);
        btn_submit = findViewById(R.id.submit);

        awesomeValidation.addValidation(SignupOrKYC_Enabler.this, R.id.dl, "^[A-Za-z0-9\\s]{1,}[\\.]{0,1}[A-Za-z0-9\\s]{0,}$", R.string.passportdlerror);
        awesomeValidation.addValidation(SignupOrKYC_Enabler.this, R.id.passportaadhar, "^[0-9]{2}[0-9]{10}$", R.string.aadharCarderror);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    Intent intent = new Intent(SignupOrKYC_Enabler.this, BikeDetails_Enabler.class);
                    startActivity(intent);

                    Toast.makeText(SignupOrKYC_Enabler.this, "Validation SuccessFully", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(SignupOrKYC_Enabler.this, "Please Validate Properly", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}