package com.example.a3rdeye.enabler.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.AccountDetails_Enabler;
import com.example.a3rdeye.enabler.BikeDetails_Enabler;

public class BikeDetails_Edit extends AppCompatActivity {

    Button btn_Cancel,btn_Update;
    EditText edit_CompanyName, edit_VehiclesNumber, edit_ModelName, edit_RcNumber;
    Button upload_RC;

    private AwesomeValidation awesomeValidation;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    Uri image_uri;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_details__edit);

        //initializing awesomevalidation object
        /*
         * The library provides 3 types of validation
         * BASIC
         * COLORATION
         * UNDERLABEL
         * */
        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);


        btn_Cancel = findViewById (R.id.cancel);
        btn_Update = findViewById (R.id.update);
        edit_CompanyName = findViewById (R.id.companyname);
        edit_VehiclesNumber = findViewById (R.id.vehiclenumber);
        edit_ModelName = findViewById (R.id.modelname);
        edit_RcNumber = findViewById (R.id.rcnumber);
        upload_RC = findViewById (R.id.uploadrc);

        awesomeValidation.addValidation (BikeDetails_Edit.this, R.id.companyname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Edit.this, R.id.vehiclenumber, "^[A-Za-z0-9]{1,10}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Edit.this, R.id.modelname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.companyName);
        awesomeValidation.addValidation (BikeDetails_Edit.this, R.id.rcnumber, "^[A-za-z0-9]{1,10}$", R.string.RcNumber);

        btn_Update.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate ( )) {

                    Toast.makeText (BikeDetails_Edit.this, "Validation Success Fully", Toast.LENGTH_SHORT).show ( );

                    Intent intent = new Intent (BikeDetails_Edit.this, AccountDetails_Enabler.class);
                    startActivity (intent);
                } else {
                    Toast.makeText (BikeDetails_Edit.this, "Please fill details properly", Toast.LENGTH_SHORT).show ( );
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
                        Toast.makeText (BikeDetails_Edit.this, "Photo Rc", Toast.LENGTH_SHORT).show ( );
                    }
                } else {

                    //system os < marshmallow
                    openCamera ( );
                    Toast.makeText (BikeDetails_Edit.this, "Photo Rc", Toast.LENGTH_SHORT).show ( );
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