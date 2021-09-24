package com.example.a3rdeye.enabler.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.CountryData;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.signin.MobileNo_Enabler;

public class CreateAccount_Enabler extends AppCompatActivity {

    TextView text_Signin;
    Button btn_next;
    CheckBox checkBox;
    EditText first_name, last_name, mobile_No;
    Spinner spinner;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account__enabler);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        text_Signin = findViewById(R.id.signin);
        btn_next = findViewById(R.id.next);
        checkBox = findViewById(R.id.checkBox);
        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        mobile_No = findViewById(R.id.mobileno);

        // txtTextView.setText(Html.fromHtml(descriptionUsingTextView));

        String checkBox_html = "<font color=#808080>By providing my number, I hereby agree and accept</font> <font color=#FFFFFF><b><u>Terms of services</u></b></font> <font color=#808080>and</font>  <font color=#FFFFFF><b><u>Privacy Policy</u></b></font>  <font color=#808080> in use of the C.A.R app.</font>";

        checkBox.setText(Html.fromHtml(checkBox_html));

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_text, CountryData.countryNames));


        awesomeValidation.addValidation(CreateAccount_Enabler.this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.fnameerror);
        awesomeValidation.addValidation(CreateAccount_Enabler.this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(CreateAccount_Enabler.this, R.id.mobileno, "^[+1-9]{0,3}[0-9]{0,15}$", R.string.mobilenoerror);


        text_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount_Enabler.this, MobileNo_Enabler.class);
                startActivity(intent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    // Here, we are sure that form is successfully validated. So, do your stuffs now...
                    if (mobile_No.getText().toString().length() == 10) {

                        if (checkBox.isChecked()) {

                            String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                            String number = mobile_No.getText().toString().trim();
                            String phonenumber = "+" + code + "  " + number;

                            Intent intent = new Intent(CreateAccount_Enabler.this, CreateAccount_OtpVerification_Enabler.class);
                            intent.putExtra("phonenumber", phonenumber);
                            startActivity(intent);


                        } else {
                            Toast.makeText(CreateAccount_Enabler.this, "CheckBox MustBe Checked", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        mobile_No.setError("valid mobile number");
                    }
                } else {
                    Toast.makeText(CreateAccount_Enabler.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}