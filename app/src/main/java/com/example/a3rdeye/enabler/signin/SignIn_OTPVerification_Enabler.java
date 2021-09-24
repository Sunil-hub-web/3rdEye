package com.example.a3rdeye.enabler.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.HomePage_Enabler;
import com.example.a3rdeye.subscriber.registration.CustomerDetails;

import in.aabhasjindal.otptextview.OtpTextView;

public class SignIn_OTPVerification_Enabler extends AppCompatActivity {

    TextView counttime, txt_otp;
    CountDownTimer countDownTimer;
    int remainingTime;
    Button btn_verifay;
    OtpTextView otpTextView;


    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinotp_verification__enabler);

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
        otpTextView = findViewById(R.id.otp_view);

        txt_otp.setEnabled(false);

        timer();

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

                        Toast.makeText(SignIn_OTPVerification_Enabler.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(SignIn_OTPVerification_Enabler.this, HomePage_Enabler.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(SignIn_OTPVerification_Enabler.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();
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
