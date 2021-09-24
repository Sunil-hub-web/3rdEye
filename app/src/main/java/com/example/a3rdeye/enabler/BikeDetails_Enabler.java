package com.example.a3rdeye.enabler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.registration.CustomerDetails;

public class BikeDetails_Enabler extends AppCompatActivity {

    Button btn_submit;
    EditText edit_CompanyName, edit_VehiclesNumber, edit_ModelName, edit_RcNumber;
    Button upload_RC;

    private AwesomeValidation awesomeValidation;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    Uri image_uri;
    private static final int IMAGE_CAPTURE_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_bike_details__enabler);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);


        btn_submit = findViewById (R.id.submit);
        edit_CompanyName = findViewById (R.id.companyname);
        edit_VehiclesNumber = findViewById (R.id.vehiclenumber);
        edit_ModelName = findViewById (R.id.modelname);
        edit_RcNumber = findViewById (R.id.rcnumber);
        upload_RC = findViewById (R.id.uploadrc);

        awesomeValidation.addValidation (BikeDetails_Enabler.this, R.id.companyname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Enabler.this, R.id.vehiclenumber, "^[A-Za-z0-9]{1,10}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Enabler.this, R.id.modelname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Enabler.this, R.id.rcnumber, "^[A-za-z0-9]{1,10}$", R.string.RcNumber);

        btn_submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate ( )) {

                    Toast.makeText (BikeDetails_Enabler.this, "Validation Success Fully", Toast.LENGTH_SHORT).show ( );

                    Intent intent = new Intent (BikeDetails_Enabler.this, AccountDetails_Enabler.class);
                    startActivity (intent);
                } else {
                    Toast.makeText (BikeDetails_Enabler.this, "Please fill details properly", Toast.LENGTH_SHORT).show ( );
                }

            }
        });

        upload_RC.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission (Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission (Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //Permission not enabled request it
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        // show popup to request permission

                        requestPermissions (permission, MY_PERMISSIONS_REQUEST_CAMERA);

                    } else {
                        //permission already granted
                        openCamera ( );
                        Toast.makeText (BikeDetails_Enabler.this, "Photo Rc", Toast.LENGTH_SHORT).show ( );
                    }
                } else {

                    //system os < marshmallow
                    openCamera ( );
                    Toast.makeText (BikeDetails_Enabler.this, "Photo Rc", Toast.LENGTH_SHORT).show ( );
                }

            }
        });


    }

    private void openCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
        }
    }

}
