package com.example.a3rdeye.enabler.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.registration.CreateAccount_Enabler;

public class MobileNo_Enabler extends AppCompatActivity {

    Button btn_verifay;
    TextView text_Register;
    CheckBox checkBox;
    EditText edit_mobile;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobileno__enabler);


        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btn_verifay = findViewById(R.id.verifay);
        text_Register = findViewById(R.id.register);
        checkBox = findViewById(R.id.checkBox);
        edit_mobile = findViewById(R.id.mobile);

        String checkBox_html = "<font color=#808080>By providing my number, I hereby agree and accept</font> <font color=#FFFFFF><b><u>Terms of services</u></b></font> <font color=#808080>and</font>  <font color=#FFFFFF><b><u>Privacy Policy</u></b></font>  <font color=#808080> in use of the C.A.R app.</font>";

        checkBox.setText(Html.fromHtml(checkBox_html));

        awesomeValidation.addValidation(MobileNo_Enabler.this, R.id.mobile, "^[0-9]{0,15}$", R.string.mobilenoerror);

        btn_verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    if (edit_mobile.getText().toString().length() == 10) {

                        Toast.makeText(MobileNo_Enabler.this, "Validation SuccessFully", Toast.LENGTH_SHORT).show();
                        if (checkBox.isChecked()) {

                            Intent intent = new Intent(MobileNo_Enabler.this, SignIn_OTPVerification_Enabler.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MobileNo_Enabler.this, "CheckBox MustBe Checked", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edit_mobile.setError("valid mobile number");
                    }
                } else {
                    Toast.makeText(MobileNo_Enabler.this, "Validation Not SuccessFully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        text_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MobileNo_Enabler.this, CreateAccount_Enabler.class);
                startActivity(intent);

            }
        });
    }
}