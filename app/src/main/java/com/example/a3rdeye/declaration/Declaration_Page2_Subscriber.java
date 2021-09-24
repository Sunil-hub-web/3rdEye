package com.example.a3rdeye.declaration;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.geofence.ProcessToLocation;
import com.example.a3rdeye.subscriber.geofence.SerachAreaDetails;
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;
import com.example.a3rdeye.subscriber.signin.SignIn_OTPVerification;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Declaration_Page2_Subscriber extends AppCompatActivity {

    CheckBox checkBox1, checkBox2, checkBox3;
    Button btn_yes, btn_no;
    String check, check1, check2, check3, check4;
    String fname, lname, mobileno, city, state, area, aadhar, dl, passport, pincode,eMail;
    Uri image_uri, image_uri1;
    Bitmap selectedImage, selectedImage1;
    private static final int PICK_IMAGE = 1000;
    String encodedImage, encodedImage1;

    String url = "https://rentopool.com/Thirdeye/api/auth/register";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration__page2);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        btn_yes = findViewById(R.id.yes);
        btn_no = findViewById(R.id.no);

        check = getIntent().getStringExtra("phonenumber");

        getAllData();

       /* btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Declaration_Page2_Subscriber.this, SerachAreaDetails.class);
                startActivity(intent);
            }
        });*/

        image_uri = Uri.parse(getIntent().getExtras().getString("imageuri"));
        image_uri1 = Uri.parse(getIntent().getExtras().getString("imageuri1"));

        Toast.makeText(this, "" + image_uri + "" + image_uri1, Toast.LENGTH_SHORT).show();

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBox1.isChecked()) {

                    check1 = "Plot has 3G Network";
                    Toast.makeText(Declaration_Page2_Subscriber.this, "" + check1, Toast.LENGTH_SHORT).show();

                    if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {

                        btn_yes.setBackgroundResource(R.drawable.button_shap);
                        btn_yes.setTextColor(Color.BLACK);
                        btn_no.setBackgroundResource(R.drawable.button_back);

                    }

                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (checkBox2.isChecked()) {

                                check2 = "Plot doesnot have any litigation";
                                Toast.makeText(Declaration_Page2_Subscriber.this, "" + check2, Toast.LENGTH_SHORT).show();

                                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {

                                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                                    btn_yes.setTextColor(Color.BLACK);
                                    btn_no.setBackgroundResource(R.drawable.button_back);
                                }

                                checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (checkBox3.isChecked()) {

                                            check3 = "I authorize 3rd eye to take videos/Photos, and go inside the property";

                                            btn_yes.setBackgroundResource(R.drawable.button_shap);
                                            btn_yes.setTextColor(Color.BLACK);
                                            btn_no.setBackgroundResource(R.drawable.button_back);

                                            check4 = check1 + "," + check2 + "," + check3;
                                            Toast.makeText(Declaration_Page2_Subscriber.this, "" + check4, Toast.LENGTH_SHORT).show();
                                        } else {

                                            btn_yes.setBackgroundResource(R.drawable.button_back1);
                                            btn_no.setBackgroundResource(R.drawable.button_back);
                                            btn_yes.setTextColor(Color.WHITE);

                                        }
                                    }
                                });

                            } else {

                                Toast.makeText(Declaration_Page2_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                                btn_yes.setBackgroundResource(R.drawable.button_back1);
                                btn_no.setBackgroundResource(R.drawable.button_back);
                                btn_yes.setTextColor(Color.WHITE);
                            }

                        }
                    });

                } else {
                    Toast.makeText(Declaration_Page2_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                    btn_yes.setBackgroundResource(R.drawable.button_back1);
                    btn_no.setBackgroundResource(R.drawable.button_back);
                    btn_yes.setTextColor(Color.WHITE);
                }

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {

                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                    btn_yes.setTextColor(Color.BLACK);
                    btn_no.setBackgroundResource(R.drawable.button_back);

                } else {
                    Toast.makeText(Declaration_Page2_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });

        try {


            final InputStream imageStream = Declaration_Page2_Subscriber.this.getContentResolver().openInputStream(image_uri);
            selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage = Bitmap.createScaledBitmap(selectedImage, 500, 750, true);
            selectedImage.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
            byte[] img = baos.toByteArray();

            encodedImage = Base64.encodeToString(img, Base64.DEFAULT);

            final InputStream imageStream1 = Declaration_Page2_Subscriber.this.getContentResolver().openInputStream(image_uri1);
            selectedImage1 = BitmapFactory.decodeStream(imageStream1);
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            selectedImage1 = Bitmap.createScaledBitmap(selectedImage1, 500, 750, true);

            selectedImage1.compress(Bitmap.CompressFormat.PNG, 80, baos1); //bm is the bitmap object
            byte[] img1 = baos1.toByteArray();

            encodedImage1 = Base64.encodeToString(img1, Base64.DEFAULT);


        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked()) {

                    dialog = new ProgressDialog(Declaration_Page2_Subscriber.this);
                    dialog.setMessage("UpLoading.....");
                    dialog.setCancelable(false);
                    dialog.show();


                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            dialog.dismiss();

                            Toast.makeText(Declaration_Page2_Subscriber.this, "" + response, Toast.LENGTH_SHORT).show();

                            try {
                                JSONObject object = new JSONObject(response);

                                String Status = object.getString("message");

                                if (Status.equals("User successfully registered")) {
                                    Toast.makeText(Declaration_Page2_Subscriber.this, "Registered Successfully", Toast.LENGTH_LONG).show();

                                    String jsonObject = object.getString("user");
                                    JSONObject user = new JSONObject(jsonObject);
                                    String id = user.getString("id");


                                    Intent intent = new Intent(Declaration_Page2_Subscriber.this, ProcessToLocation.class);
                                    intent.putExtra("id",id);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(Declaration_Page2_Subscriber.this, "Try Again", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Declaration_Page2_Subscriber.this, ""+error, Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<>();

                            params.put("first_name", fname);
                            params.put("last_name", lname);
                            params.put("mobile_number", mobileno);
                            params.put("email", eMail);
                            params.put("city", city);
                            params.put("state", state);
                            params.put("area", area);
                            params.put("aadhar_number", aadhar);
                            params.put("dl_number", dl);
                            params.put("passport_number", passport);
                            params.put("pincode", pincode);
                            params.put("first_declaration", check);
                            params.put("second_declaration", check4);
                            params.put("profile_image", "data:image/jpeg;base64," + encodedImage1);
                            params.put("idproof_image", "data:image/jpeg;base64," + encodedImage);
                            params.put("role", "Subscriber");

                            return params;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue = Volley.newRequestQueue(Declaration_Page2_Subscriber.this);
                    requestQueue.add(request);

                } else {
                    Toast.makeText(Declaration_Page2_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void getAllData() {

        SharedPreferences sp1 = getSharedPreferences("details", MODE_PRIVATE);
        fname = sp1.getString("fname", null);
        lname = sp1.getString("lname", null);
        mobileno = sp1.getString("mobileno", null);
        eMail = sp1.getString("email", null);
        city = sp1.getString("city", null);
        state = sp1.getString("state", null);
        area = sp1.getString("area", null);
        aadhar = sp1.getString("aadhar", null);
        dl = sp1.getString("dl", null);
        passport = sp1.getString("passport_number", null);
        pincode = sp1.getString("pincode", null);

         Toast.makeText(this, "All Data : "+fname+" "+lname+" "+mobileno+" "+city+" "+state+" "+area+" "+aadhar+" "+dl+" "+pincode+" "+check, Toast.LENGTH_LONG).show();

    }
}