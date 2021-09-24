package com.example.a3rdeye.enabler.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.PhoneDetails_Enabler;

import in.aabhasjindal.otptextview.OtpTextView;

public class CreateAccount_OtpVerification_Enabler extends AppCompatActivity {

    TextView counttime, txt_otp;
    CountDownTimer countDownTimer;
    int remainingTime;
    Button btn_verifay;
    EditText mobile_no, edit_one, edit_two, edit_three, edit_four;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    OtpTextView otpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_otp_verification__enabler);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        counttime = findViewById(R.id.timer);
        txt_otp = findViewById(R.id.reseotp);
        btn_verifay = findViewById(R.id.verifay);
        mobile_no = findViewById(R.id.mobileno);
        otpTextView = findViewById(R.id.otp_view);

        txt_otp.setEnabled(false);

        String phonenumber = getIntent().getStringExtra("phonenumber");

        mobile_no.setText(phonenumber);

        timer();

        awesomeValidation.addValidation(CreateAccount_OtpVerification_Enabler.this, R.id.mobileno, "^[+][0-9]{0,3}[\\s][\\s][0-9]{0,15}$", R.string.mobilenoerror);

        txt_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_otp.setEnabled(false);
                countDownTimer.start();
                timer();
            }
        });

        btn_verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    if (otpTextView.getOTP().length() < 4) {

                        Toast.makeText(CreateAccount_OtpVerification_Enabler.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(CreateAccount_OtpVerification_Enabler.this, PhoneDetails_Enabler.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(CreateAccount_OtpVerification_Enabler.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void timer() {

        countDownTimer = new CountDownTimer(25000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                remainingTime = (int) (millisUntilFinished / 1000);
                counttime.setText(String.valueOf("00:" + remainingTime));

                //remainingTime++;
            }

            @Override
            public void onFinish() {

                counttime.setText("");
                txt_otp.setEnabled(true);

                Toast.makeText(CreateAccount_OtpVerification_Enabler.this, "Code SuccessFully Send Your Mobile", Toast.LENGTH_LONG).show();

            }
        }.start();

    }

    @Override
    protected void onRestart() {
        super.onRestart();


        txt_otp.setEnabled(false);
        countDownTimer.start();
        timer();

    }
}