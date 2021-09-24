package com.example.a3rdeye.subscriber.registration;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.a3rdeye.CountryData;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.geofence.ProcessToLocation;
import com.example.a3rdeye.subscriber.geofence.SerachAreaDetails;
import com.example.a3rdeye.subscriber.signin.MobileNo;
import com.example.a3rdeye.subscriber.signin.SignIn_OTPVerification;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    TextView text_Signin;
    Button btn_next;
    CheckBox checkBox;
    EditText first_name, last_name, mobile_No,EMail_id;
    Spinner spinner;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    String url = "https://rentopool.com/Thirdeye/api/auth/sendOTP";

    ProgressDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        btn_next = findViewById(R.id.next);

        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        mobile_No = findViewById(R.id.mobileno);
        EMail_id = findViewById(R.id.email);

        Intent intent = getIntent();
        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");
        String mobile = intent.getStringExtra("mobile");

        first_name.setText(fname);
        last_name.setText(lname);
        mobile_No.setText(mobile);

        //initializing awesomevalidation object

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


        awesomeValidation.addValidation(CreateAccount.this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.fnameerror);
        awesomeValidation.addValidation(CreateAccount.this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(CreateAccount.this, R.id.mobileno, "^[+1-9]{0,3}[0-9]{0,15}$", R.string.mobilenoerror);
        awesomeValidation.addValidation(CreateAccount.this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


        text_Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateAccount.this, MobileNo.class);
                startActivity(intent);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {
                    // Here, we are sure that form is successfully validated. So, do your stuffs now...
                    if (mobile_No.getText().toString().length() == 10) {

                        if (!TextUtils.isEmpty(EMail_id.getText())) {

                            if (checkBox.isChecked()) {

                            dialog = new ProgressDialog(CreateAccount.this);
                            dialog.setMessage("Send Otp In Your Mobile No");
                            dialog.setCancelable(false);
                            dialog.show();

                                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                                String number = mobile_No.getText().toString().trim();
                                String phonenumber = "+" + code + "  " + number;

                                String fname = first_name.getText().toString().trim();
                                String lname = last_name.getText().toString().trim();
                                String mobileno = mobile_No.getText().toString().trim();
                                String email = EMail_id.getText().toString().trim();

                                String add = "+91";
                                String mobile_Number = add + "" + mobileno;

                                SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("fname", fname);
                                editor.putString("lname", lname);
                                editor.putString("mobileno", mobile_Number);
                                editor.putString("email",email);

                                editor.commit();

                                /*Intent intent = new Intent(CreateAccount.this, CreateAccount_OtpVerification.class);
                                intent.putExtra("phonenumber", phonenumber);
                                intent.putExtra("mobileno", mobile_Number);
                                startActivity(intent);*/

                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    dialog.dismiss();

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String Status = jsonObject.getString("success");

                                        if (Status.equals("OTP has been sent")) {

                                            Toast.makeText(CreateAccount.this, "Otp Send SuccessFully", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(CreateAccount.this, CreateAccount_OtpVerification.class);
                                            intent.putExtra("phonenumber", phonenumber);
                                            intent.putExtra("mobileno", mobile_Number);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(CreateAccount.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    dialog.dismiss();

                                    Toast.makeText(CreateAccount.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> params = new HashMap<>();

                                    params.put("mobile_number", mobile_Number);

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(CreateAccount.this);
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
                                Toast.makeText(CreateAccount.this, "CheckBox Must Be Checked", Toast.LENGTH_LONG).show();

                            }
                        }else{

                            EMail_id.setError("Enter Email id");

                        }
                    } else {
                        mobile_No.setError("valid mobile number");
                    }
                } else {
                    Toast.makeText(CreateAccount.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}