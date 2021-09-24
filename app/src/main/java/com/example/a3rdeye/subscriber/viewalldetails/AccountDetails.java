package com.example.a3rdeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountDetails extends AppCompatActivity {

    TextView first_name, last_name, mobile_No,edit_state,edit_city, edit_aadharCard,
            edit_passportDl, edit_pinCode, edit_Area,txt_ImageText1,txt_ImageText2;

    String id;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/getuserbyid";
    String fname,lname,mobile,aadhar,passport,state,city,area,pincode,idproof_Image,image,profile_Image,image2;
    ImageView imageView1,imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details2);

        first_name = findViewById(R.id.fname);
        last_name = findViewById(R.id.lname);
        mobile_No = findViewById(R.id.mobileno);
        edit_aadharCard = findViewById(R.id.aadharcard);
        edit_state = findViewById(R.id.state);
        edit_city = findViewById(R.id.city);
        edit_passportDl = findViewById(R.id.passportdl);
        edit_pinCode = findViewById(R.id.pincode);
        edit_Area = findViewById(R.id.area);
        imageView1 = findViewById(R.id.imgPreview);
        imageView2 = findViewById(R.id.imgPreview1);
        txt_ImageText1 = findViewById(R.id.imagetext);
        txt_ImageText2 = findViewById(R.id.imagetext1);

        id = SharedPrefManager.getInstance (AccountDetails.this).getUser ().getId ();


        ProgressDialog dialog = new ProgressDialog (AccountDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                            dialog.dismiss ();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String Status = jsonObject.getString("Information");
                    JSONObject jsonObject1 = new JSONObject(Status);

                    fname = jsonObject1.getString("first_name");
                    lname = jsonObject1.getString("last_name");
                    mobile = jsonObject1.getString("mobile_number");
                    area = jsonObject1.getString("area");
                    state = jsonObject1.getString("state");
                    city = jsonObject1.getString("city");
                    pincode = jsonObject1.getString("pincode");
                    aadhar = jsonObject1.getString("aadhar_number");
                    passport = jsonObject1.getString("passport_number");
                    image = jsonObject1.getString("idproof_image");
                    image2 = jsonObject1.getString("profile_image");

                    idproof_Image = image.split(",")[1];

                    byte[] decodedString = Base64.decode(idproof_Image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    profile_Image = image2.split(",")[1];

                    byte[] decodedString1 = Base64.decode(profile_Image, Base64.DEFAULT);
                    Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString.length);

                    /*image = image.replace("data:image/png;base64,","");
                    byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);*/

                    first_name.setText(fname);
                    last_name.setText(lname);
                    mobile_No.setText(mobile);
                    edit_Area.setText(area);
                    edit_city.setText(city);
                    edit_pinCode.setText(pincode);
                    edit_aadharCard.setText(aadhar);
                    edit_passportDl.setText(passport);
                    edit_state.setText(state);
                    txt_ImageText1.setVisibility(View.GONE);

                    imageView1.setImageBitmap(decodedByte);
                    imageView2.setImageBitmap(decodedByte1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                Toast.makeText(AccountDetails.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",id);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AccountDetails.this);
        requestQueue.add(request);



    }
}