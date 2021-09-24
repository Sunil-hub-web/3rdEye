package com.example.a3rdeye.subscriber.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.geofence.Geofence;
import com.example.a3rdeye.subscriber.geofence.ProcessToLocation;
import com.example.a3rdeye.subscriber.geofence.SerachAreaDetails;
import com.example.a3rdeye.subscriber.signin.SignIn_OTPVerification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.aabhasjindal.otptextview.OtpTextView;

public class CreateAccount_OtpVerification extends AppCompatActivity {

    TextView counttime, txt_otp;
    CountDownTimer countDownTimer;
    int remainingTime;
    Button btn_verifay;
    EditText mobile_no;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    OtpTextView otpTextView;

    String verify_otp, mNumber, mobile_number;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount__otpverification);

        btn_verifay = findViewById(R.id.verifay);

        //initializing awesomevalidation object

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        counttime = findViewById(R.id.timer);
        txt_otp = findViewById(R.id.reseotp);
        btn_verifay = findViewById(R.id.verifay);
        mobile_no = findViewById(R.id.mobileno);
        otpTextView = findViewById(R.id.otp_view);

        txt_otp.setEnabled(false);

        String phonenumber = getIntent().getStringExtra("phonenumber");

        Intent intent = getIntent();
        mNumber = intent.getStringExtra("mobileno");

        mobile_no.setText(phonenumber);

        timer();

        awesomeValidation.addValidation(CreateAccount_OtpVerification.this, R.id.mobileno, "^[+][0-9]{0,3}[\\s][\\s][0-9]{0,15}$", R.string.mobilenoerror);

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

                verify_otp = otpTextView.getOTP();

                if (awesomeValidation.validate()) {

                    if (otpTextView.getOTP().length() < 6) {

                        Toast.makeText(CreateAccount_OtpVerification.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                    } else {

                       /* Intent intent = new Intent(CreateAccount_OtpVerification.this, CustomerDetails.class);
                        startActivity(intent);*/
                        
                        dialog = new ProgressDialog(CreateAccount_OtpVerification.this);
                        dialog.setMessage("Loading......");
                        dialog.setCancelable(false);
                        dialog.show();
                        final Map<String, String> params = new HashMap();

                        params.put("mobile_number", mNumber);
                        params.put("otp_code", verify_otp);

                        JSONObject parameters = new JSONObject(params);

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, "https://rentopool.com/Thirdeye/api/auth/verifyOTP",
                                parameters, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Toast.makeText(CreateAccount_OtpVerification.this, "" + response, Toast.LENGTH_LONG).show();

                                dialog.dismiss();

                                try {
                                    String Status = response.getString("success");

                                    if (Status.equals("OTP has been verifyed")) ;
                                    {
                                        Toast.makeText(CreateAccount_OtpVerification.this, "OTP has been verifyed Success Fully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CreateAccount_OtpVerification.this, CustomerDetails.class);
                                        startActivity(intent);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                dialog.dismiss();
                                error.printStackTrace();
                                Toast.makeText(CreateAccount_OtpVerification.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                        });

                        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        Volley.newRequestQueue(CreateAccount_OtpVerification.this).add(jsonRequest);
                    }

                } else {
                    Toast.makeText(CreateAccount_OtpVerification.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();
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

                Toast.makeText(CreateAccount_OtpVerification.this, "Code SuccessFully Send Your Mobile", Toast.LENGTH_LONG).show();

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