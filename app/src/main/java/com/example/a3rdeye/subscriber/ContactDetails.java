package com.example.a3rdeye.subscriber;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.a3rdeye.MainActivity;
import com.example.a3rdeye.R;
import com.example.a3rdeye.declaration.Declaration_Page2_Subscriber;
import com.example.a3rdeye.subscriber.registration.CreateAccount;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactDetails extends AppCompatActivity {

    Button btn_submit;

    EditText first_name, last_name, text_MobileNo;
    String fname,lname,mobile;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/storeuserwithoutpin";
    String message,city,pincode,catagory;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        city = intent.getStringExtra("city");
        pincode = intent.getStringExtra("pincode");
        catagory = intent.getStringExtra("catagory");

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

        awesomeValidation.addValidation(ContactDetails.this, R.id.fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.fnameerror);
        awesomeValidation.addValidation(ContactDetails.this, R.id.lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lnameerror);
        awesomeValidation.addValidation(ContactDetails.this, R.id.mobileno, "^[0-9]{2}[0-9]{8}$", R.string.mobilenoerror);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = first_name.getText().toString().trim();
                lname = last_name.getText().toString().trim();
                mobile = text_MobileNo.getText().toString().trim();

                if (awesomeValidation.validate()) {
                    // Here, we are sure that form is successfully validated. So, do your stuffs now...
                    if (text_MobileNo.getText().toString().length() != 10) {
                        text_MobileNo.setError("valid mobile number");

                    } else {

                        if(message.equals("successfully")){

                            Intent intent = new Intent(ContactDetails.this, CreateAccount.class);
                            intent.putExtra("fname",fname);
                            intent.putExtra("lname",lname);
                            intent.putExtra("mobile",mobile);
                            startActivity(intent);
                        }
                        else{
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);

                                        String statues = jsonObject.getString("message");

                                        if(statues.equals("User stored")){

                                            AlertDialog.Builder builder = new AlertDialog.Builder(ContactDetails.this);
                                            builder.setTitle("Message");
                                            builder.setMessage("Pincode Not Avilable And Data Store Successfully for Forthere Contact");

                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent intent1 = new Intent(ContactDetails.this,MainActivity.class);
                                                    startActivity(intent1);

                                                }
                                            });
                                            builder.show();
                                            builder.setCancelable(false);

                                        }
                                        else{ }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(ContactDetails.this, ""+error, Toast.LENGTH_SHORT).show();

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String,String> params = new HashMap<>();

                                    params.put("city",city);
                                    params.put("pincode",pincode);
                                    params.put("category",catagory);
                                    params.put("first_name",fname);
                                    params.put("last_name",lname);
                                    params.put("mobile_no",mobile);

                                    return params;
                                }
                            };

                            RequestQueue requestQueue = Volley.newRequestQueue(ContactDetails.this);
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

                    }
                } else {
                    Toast.makeText(ContactDetails.this, "Form Validated Not Successfully", Toast.LENGTH_LONG).show();

                }

            }
        });


    }
}