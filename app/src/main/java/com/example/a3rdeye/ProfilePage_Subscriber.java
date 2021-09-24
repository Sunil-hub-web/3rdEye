package com.example.a3rdeye;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.subscriber.About_Us;
import com.example.a3rdeye.subscriber.Notification_Subscriber;
import com.example.a3rdeye.subscriber.SettingActivity;
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;
import com.example.a3rdeye.subscriber.viewalldetails.AccountDetails;
import com.example.a3rdeye.subscriber.viewalldetails.PackagesDetails;
import com.example.a3rdeye.subscriber.viewalldetails.ViewUserPropertyDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.system.ProcessKt;

public class ProfilePage_Subscriber extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CircleImageView circleImageView;
    public static final int IMAGE_CODE = 1;
    Uri imageUri;
    ImageView imageView;
    TextView text_UserDetails, text_PackagesDetails, text_UserName, text_MobileNumber, text_PropertyDetails,text_Logout,text_Setting,text_Notifaction;
    String fname, lname, mobile, image, profile_Image, id;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/getuserbyid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile_page__subscriber);

        bottomNavigationView = findViewById (R.id.bottomNavigation);
        circleImageView = findViewById (R.id.profile_image);
        imageView = findViewById (R.id.imageview);
        text_UserDetails = findViewById (R.id.accountdetails);
        text_PackagesDetails = findViewById (R.id.packages);
        text_UserName = findViewById (R.id.username);
        text_MobileNumber = findViewById (R.id.mobilenumber);
        text_PropertyDetails = findViewById (R.id.property);
        text_Logout = findViewById (R.id.logout);
        text_Setting = findViewById (R.id.setting);
        text_Notifaction = findViewById (R.id.notifaction);

        id = SharedPrefManager.getInstance (ProfilePage_Subscriber.this).getUser ().getId ();

        getUserData();

        bottomNavigationView.setSelectedItemId (R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId ( )) {

                    case R.id.profile:

                        //startActivity(new Intent(getApplicationContext(),DashBaord.class));
                        // overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity (new Intent (getApplicationContext ( ), HomePage_Subscriber.class));
                        overridePendingTransition (0, 0);
                        return true;

                    case R.id.about:

                        startActivity (new Intent (getApplicationContext ( ), About_Us.class));
                        overridePendingTransition (0, 0);
                        return true;
                }
                return false;
            }
        });

        circleImageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        text_UserDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, AccountDetails.class);
                startActivity (intent);
            }
        });

        text_PackagesDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, PackagesDetails.class);
                startActivity (intent);
            }
        });

        text_PropertyDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, ViewUserPropertyDetails.class);
                startActivity (intent);

            }
        });

        text_Logout.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance (ProfilePage_Subscriber.this).logout ();
            }
        });

        text_Setting.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( ProfilePage_Subscriber.this, SettingActivity.class);
                startActivity (intent);
            }
        });
        text_Notifaction.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( ProfilePage_Subscriber.this, Notification_Subscriber.class);
                startActivity (intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData ( ) != null) {

            imageUri = data.getData ( );
            circleImageView.setImageURI (imageUri);
        }
    }

    public void getUserData(){

        ProgressDialog dialog = new ProgressDialog (ProfilePage_Subscriber.this);
        dialog.setMessage ("Retriving...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();

                dialog.dismiss ( );

                try {

                    JSONObject jsonObject = new JSONObject (response);
                    String Status = jsonObject.getString ("Information");

                    JSONObject jsonObject1 = new JSONObject (Status);

                    fname = jsonObject1.getString ("first_name");
                    lname = jsonObject1.getString ("last_name");
                    mobile = jsonObject1.getString ("mobile_number");
                    image = jsonObject1.getString ("profile_image");

                    profile_Image = image.split (",")[1];

                    byte[] decodedString1 = Base64.decode (profile_Image, Base64.DEFAULT);
                    Bitmap decodedByte1 = BitmapFactory.decodeByteArray (decodedString1, 0, decodedString1.length);

                    text_UserName.setText (fname + " " + lname);
                    text_MobileNumber.setText (mobile);

                    circleImageView.setImageBitmap (decodedByte1);


                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ( );
                Toast.makeText (ProfilePage_Subscriber.this, "" + error, Toast.LENGTH_SHORT).show ( );

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );
                params.put ("id",id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue (ProfilePage_Subscriber.this);
        requestQueue.add (request);

    }

    @Override
    public void onBackPressed() {

    }
}