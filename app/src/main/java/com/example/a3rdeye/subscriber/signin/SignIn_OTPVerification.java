package com.example.a3rdeye.subscriber.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.MainActivity;
import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;
import com.example.a3rdeye.subscriber.Login_ModelClass;
import com.example.a3rdeye.subscriber.geofence.SerachAreaDetails;
import com.example.a3rdeye.subscriber.geofence.VideoCalling;
import com.example.a3rdeye.subscriber.registration.CreateAccount_OtpVerification;
import com.example.a3rdeye.subscriber.registration.CustomerDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.aabhasjindal.otptextview.OtpTextView;

public class SignIn_OTPVerification extends AppCompatActivity {

    TextView counttime, txt_otp;
    CountDownTimer countDownTimer;
    int remainingTime;
    Button btn_verifay;

    String url = "https://rentopool.com/Thirdeye/api/auth/login";
    String url1 = "https://rentopool.com/Thirdeye/api/auth/sendOTP";


    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    OtpTextView otpTextView;

    String verify_otp, mNumber, mobile_number, Status;

    ProgressDialog dialog;

    String id,fname,lname,mobile,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        btn_verifay = findViewById(R.id.verifay);

       /* btn_verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/



        //initializing awesomevalidation object

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        counttime = findViewById(R.id.timer);
        txt_otp = findViewById(R.id.reseotp);
        btn_verifay = findViewById(R.id.verifay);
        otpTextView = findViewById(R.id.otp_view);

        Intent intent = getIntent();
        mNumber = intent.getStringExtra("mobileno");
        Toast.makeText(this, "" + mNumber, Toast.LENGTH_SHORT).show();

        mobile_number = "+917853074379";


        txt_otp.setEnabled(false);

        timer();

        txt_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txt_otp.setEnabled(false);
                countDownTimer.start();
                timer();

                dialog = new ProgressDialog(SignIn_OTPVerification.this);
                dialog.setMessage("Send Otp");
                dialog.setCancelable(false);
                dialog.show();

                StringRequest request = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Status = jsonObject.getString("success");

                            if (Status.equals("OTP has been sent")) {

                                Toast.makeText(SignIn_OTPVerification.this, "Otp Send SuccessFully", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(SignIn_OTPVerification.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.dismiss();

                        Toast.makeText(SignIn_OTPVerification.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();

                        params.put("mobile_number", mNumber);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(SignIn_OTPVerification.this);
                requestQueue.add(request);

                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 50000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 50000;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });


            }
        });

        btn_verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verify_otp = otpTextView.getOTP();


                Toast.makeText(SignIn_OTPVerification.this, "" + verify_otp, Toast.LENGTH_LONG).show();

                if (!awesomeValidation.validate() || otpTextView.getOTP().length() < 6) {


                    Toast.makeText(SignIn_OTPVerification.this, "Fill All Fields", Toast.LENGTH_LONG).show();
                } else {

                    dialog = new ProgressDialog(SignIn_OTPVerification.this);
                    dialog.setMessage("Verify Otp");
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

                            Toast.makeText(SignIn_OTPVerification.this, "" + response, Toast.LENGTH_LONG).show();

                            try {
                                String Status = response.getString("success");

                                if (Status.equals("OTP has been verifyed")) ;
                                {

                                    Toast.makeText(SignIn_OTPVerification.this, "OTP has been verifyed Success Fully", Toast.LENGTH_SHORT).show();
                                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                           // dialog.dismiss();
                                            //Toast.makeText(SignIn_OTPVerification.this, "Login", Toast.LENGTH_LONG).show();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String Status = jsonObject.getString("user");

                                                if (Status.equals("null")) {

                                                    Toast.makeText(SignIn_OTPVerification.this, "Mobile Number is not Register Please Register", Toast.LENGTH_LONG).show();

                                                    Intent intent1 = new Intent(SignIn_OTPVerification.this, MainActivity.class);
                                                    startActivity(intent1);

                                                } else {

                                                    JSONObject jsonObject1 = new JSONObject(Status);
                                                    id = jsonObject1.getString("id");
                                                    fname = jsonObject1.getString("first_name");
                                                    lname = jsonObject1.getString("last_name");
                                                    mobile = jsonObject1.getString("mobile_number");

                                                    Login_ModelClass login_modelClass = new Login_ModelClass (id,fname,lname,mobile);

                                                    SharedPrefManager.getInstance (SignIn_OTPVerification.this).userLogin (login_modelClass);

                                                    Toast.makeText(SignIn_OTPVerification.this, "Login Success Fully", Toast.LENGTH_LONG).show();



                                                    Intent intent = new Intent(SignIn_OTPVerification.this, HomePage_Subscriber.class);
                                                    startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                                // dialog.dismiss();
                                            Toast.makeText(SignIn_OTPVerification.this, "" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<>();

                                            params.put("mobile_number", mNumber);
                                            params.put("otp_code", verify_otp);

                                            return params;
                                        }
                                    };

                                    RequestQueue requestQueue = Volley.newRequestQueue(SignIn_OTPVerification.this);
                                    requestQueue.add(request);
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
                            Toast.makeText(SignIn_OTPVerification.this, "Error" + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                    jsonRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    Volley.newRequestQueue(SignIn_OTPVerification.this).add(jsonRequest);
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