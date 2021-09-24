package com.example.a3rdeye.subscriber.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.declaration.Declaration_Page1_Subscriber;
import com.example.a3rdeye.subscriber.geofence.SerachAreaDetails;
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;

public class CustomerDetails extends AppCompatActivity {

    Button btn_next;
    EditText edit_state, edit_city, edit_aadharCard, edit_passportDl, edit_pinCode, edit_Area;
    Button upload_aadharCard, upload_Selfie;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    Uri image_uri, image_uri1;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        btn_next = findViewById(R.id.next);

       /* btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CustomerDetails.this, Declaration_Page1_Subscriber.class);
                startActivity(intent);
            }
        });*/

        //initializing awesomevalidation object

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btn_next = findViewById(R.id.next);
        edit_aadharCard = findViewById(R.id.aadharcard);
        edit_state = findViewById(R.id.state);
        edit_city = findViewById(R.id.city);
        edit_passportDl = findViewById(R.id.passportdl);
        edit_pinCode = findViewById(R.id.pincode);
        upload_aadharCard = findViewById(R.id.uploadaadhar);
        upload_Selfie = findViewById(R.id.uploadselfie);
        edit_Area = findViewById(R.id.area);

        awesomeValidation.addValidation(CustomerDetails.this, R.id.city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cityerror);
        awesomeValidation.addValidation(CustomerDetails.this, R.id.state, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.stateerror);
        awesomeValidation.addValidation(CustomerDetails.this, R.id.area, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.stateerror);
        awesomeValidation.addValidation(CustomerDetails.this, R.id.passportdl, "^[A-Za-z0-9\\s]{1,}[\\.]{0,1}[A-Za-z0-9\\s]{0,}$", R.string.passportdlerror);
        awesomeValidation.addValidation(CustomerDetails.this, R.id.aadharcard, "^[0-9]{2}[0-9]{10}$", R.string.aadharCarderror);
        awesomeValidation.addValidation(CustomerDetails.this, R.id.pincode, "^[0-9]{6}$", R.string.pincodeerror);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    String city = edit_city.getText().toString().trim();
                    String state = edit_state.getText().toString().trim();
                    String area = edit_Area.getText().toString().trim();
                    String aadhar = edit_aadharCard.getText().toString().trim();
                    String dl = edit_passportDl.getText().toString().trim();
                    String pincode = edit_pinCode.getText().toString().trim();

                    SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
                    String s1 = sp.getString("fname", null);
                    String s2 = sp.getString("lname", null);
                    String s3 = sp.getString("mobileno", null);
                    String s4 = sp.getString("email", null);

                    SharedPreferences sp1 = getSharedPreferences("details", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp1.edit();
                    editor.putString("fname", s1);
                    editor.putString("lname", s2);
                    editor.putString("mobileno", s3);
                    editor.putString("email", s4);
                    editor.putString("city", city);
                    editor.putString("state", state);
                    editor.putString("area", area);
                    editor.putString("aadhar", aadhar);
                    editor.putString("dl", dl);
                    editor.putString("passport_number", dl);
                    editor.putString("pincode", pincode);
                    editor.commit();

                    Intent intent = new Intent(CustomerDetails.this, Declaration_Page1_Subscriber.class);
                    intent.putExtra("imageuri", image_uri.toString());
                    intent.putExtra("imageuri1", image_uri1.toString());
                    startActivity(intent);


                } else {
                    Toast.makeText(CustomerDetails.this, "Form Validated UnSuccessfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        upload_aadharCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //Permission not enabled request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        // show popup to request permission

                        requestPermissions(permission, PERMISSION_CODE);

                    } else {
                        //permission already granted
                        openCamera();
                        Toast.makeText(CustomerDetails.this, "Photo of aadhar", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    //system os < marshmallow
                    openCamera();
                    Toast.makeText(CustomerDetails.this, "Photo selfie", Toast.LENGTH_SHORT).show();
                }
            }
        });
        upload_Selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //Permission not enabled request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        // show popup to request permission

                        requestPermissions(permission, PERMISSION_CODE);

                    } else {
                        //permission already granted
                        openCamera1();
                        Toast.makeText(CustomerDetails.this, "Photo selfie", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    //system os < marshmallow
                    openCamera1();
                    Toast.makeText(CustomerDetails.this, "Photo selfie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera1() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri1);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

}