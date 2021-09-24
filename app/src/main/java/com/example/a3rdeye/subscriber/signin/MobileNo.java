package com.example.a3rdeye.subscriber.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.MainActivity;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.AreaDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MobileNo extends AppCompatActivity {

    Button btn_verifay;
    TextView text_Register;
    CheckBox checkBox;
    EditText edit_mobile;
    String mobileNo, mobile_Number;
    String Status;

    String url = "https://rentopool.com/Thirdeye/api/auth/sendOTP";

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_no);

        btn_verifay = findViewById(R.id.verifay);


        //initializing awesomevalidation object


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btn_verifay = findViewById(R.id.verifay);
        text_Register = findViewById(R.id.register);
        checkBox = findViewById(R.id.checkBox);
        edit_mobile = findViewById(R.id.mobile);

        String checkBox_html = "<font color=#808080>By providing my number, I hereby agree and accept</font> <font color=#FFFFFF><b><u>Terms of services</u></b></font> <font color=#808080>and</font>  <font color=#FFFFFF><b><u>Privacy Policy</u></b></font>  <font color=#808080> in use of the C.A.R app.</font>";

        checkBox.setText(Html.fromHtml(checkBox_html));

        awesomeValidation.addValidation(MobileNo.this, R.id.mobile, "^[0-9]{0,15}$", R.string.mobilenoerror);

        btn_verifay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNo = edit_mobile.getText().toString().trim();

                String add = "+91";
                mobile_Number = add + "" + mobileNo;

                Toast.makeText(MobileNo.this, "" + mobile_Number, Toast.LENGTH_SHORT).show();
                if (awesomeValidation.validate()) {

                    if (edit_mobile.getText().toString().length() == 10) {

                        //Toast.makeText(MobileNo.this, "Validation SuccessFully", Toast.LENGTH_SHORT).show();
                        if (checkBox.isChecked()) {

                            dialog = new ProgressDialog(MobileNo.this);
                            dialog.setMessage("Send Otp In Your Mobile No");
                            dialog.setCancelable(false);
                            dialog.show();

                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    dialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        Status = jsonObject.getString("success");

                                        if (Status.equals("OTP has been sent")) {

                                            Toast.makeText(MobileNo.this, "Otp Send SuccessFully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(MobileNo.this, SignIn_OTPVerification.class);
                                            intent.putExtra("mobileno", mobile_Number);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(MobileNo.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    dialog.dismiss();

                                    Toast.makeText(MobileNo.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<>();

                                    params.put("mobile_number", mobile_Number);

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(MobileNo.this);
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
                        } else {
                            Toast.makeText(MobileNo.this, "CheckBox MustBe Checked", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        edit_mobile.setError("valid mobile number");
                    }
                } else {
                    Toast.makeText(MobileNo.this, "Validation Not SuccessFully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        text_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MobileNo.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}