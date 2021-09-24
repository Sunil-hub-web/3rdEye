package com.example.a3rdeye.enabler.propertyphoto;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3rdeye.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CapturePropertyPhoto extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSION_CODE = 1000;
    private static final int PICK_IMAGE_REQUEST = 1;
    public int PIC_CODE = 0;
    private static final int CAMERA_REQUEST = 10001;

    TextView txtDateTime, txtLatLngs, txtAddress, txtPostal, radioText, radioText1, radioText2, radioText3, radioText4, radioText5, radioText6, radioText7, radioText8, radioText9, radioText10, radioText11,
            condition, condition1, condition2, condition3, condition4, condition5, condition6, condition7, condition8, condition9, condition10, condition11;
    ImageView imgPreview, imgPreview1, imgPreview2, imgPreview3, imgPreview4, imgPreview5, imgPreview6, imgPreview7, imgPreview8, imgPreview9, imgPreview10, imgPreview11;

    Uri image_uri, image_uri1, image_uri2, image_uri3, image_uri4, image_uri5, image_uri6, image_uri7, image_uri8, image_uri9, image_uri10, image_uri11;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    TextView textView, textView1;
    RadioButton radioButton;
    RadioGroup radioGroup;

    RadioButton radioGate, radioApproachroad, radioFrontage, radioFrontlhs, radioFrontrhs,
            radioLeftBoundary, radioRightBoundary, radioPictureFromBackSide, radioLocality1, radioLocality2,
            radioAnyOther1, radioAnyOther2;

    String selectedPlace, selectedPlace1, selectedPlace2, selectedPlace3, selectedPlace4, selectedPlace5, selectedPlace6, selectedPlace7, selectedPlace8, selectedPlace9, selectedPlace10, selectedPlace11,
            checkCondition, checkCondition1, checkCondition2, checkCondition3, checkCondition4, checkCondition5, checkCondition6, checkCondition7, checkCondition8, checkCondition9, checkCondition10, checkCondition11;
    String txt_DataTime, txt_LatLngs, txt_Postal, txt_Address, txt_Radio, txt_Condition;
    Bitmap bitmap, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9, bitmap10, bitmap11,
            scaledbMap, scaledbMap1, scaledbMap2, scaledbMap3, scaledbMap4, scaledbMap5, scaledbMap6, scaledbMap7, scaledbMap8, scaledbMap9, scaledbMap10, scaledbMap11;

    Button btn_printPdf, button2, btn_saveImage;

    ProgressDialog pd;
    File file;

    String textView_Data, textView_Data1, textView_Data2, textView_Data3, textView_Data4, textView_Data5, textView_Data6, textView_Data7, textView_Data8, textView_Data9, textView_Data10, textView_Data11;
    String completed = "COMPLETED";
    TextView text_Comp, text_Comp1, text_Comp2, text_Comp3, text_Comp4, text_Comp5, text_Comp6, text_Comp7, text_Comp8, text_Comp9, text_Comp10, text_Comp11;
    String subLocality;

    Bitmap scaledbMapicone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_property_photo);

        imgPreview = findViewById(R.id.imgPreview);
        imgPreview1 = findViewById(R.id.imgPreview1);
        imgPreview2 = findViewById(R.id.imgPreview2);
        imgPreview3 = findViewById(R.id.imgPreview3);
        imgPreview4 = findViewById(R.id.imgPreview4);
        imgPreview5 = findViewById(R.id.imgPreview5);
        imgPreview6 = findViewById(R.id.imgPreview6);
        imgPreview7 = findViewById(R.id.imgPreview7);
        imgPreview8 = findViewById(R.id.imgPreview8);
        imgPreview9 = findViewById(R.id.imgPreview9);
        imgPreview10 = findViewById(R.id.imgPreview10);
        imgPreview11 = findViewById(R.id.imgPreview11);

        radioText = findViewById(R.id.radiotext);
        radioText1 = findViewById(R.id.radiotext1);
        radioText2 = findViewById(R.id.radiotext2);
        radioText3 = findViewById(R.id.radiotext3);
        radioText4 = findViewById(R.id.radiotext4);
        radioText5 = findViewById(R.id.radiotext5);
        radioText6 = findViewById(R.id.radiotext6);
        radioText7 = findViewById(R.id.radiotext7);
        radioText8 = findViewById(R.id.radiotext8);
        radioText9 = findViewById(R.id.radiotext9);
        radioText10 = findViewById(R.id.radiotext10);
        radioText11 = findViewById(R.id.radiotext11);

        condition = findViewById(R.id.condition);
        condition1 = findViewById(R.id.condition1);
        condition2 = findViewById(R.id.condition2);
        condition3 = findViewById(R.id.condition3);
        condition4 = findViewById(R.id.condition4);
        condition5 = findViewById(R.id.condition5);
        condition6 = findViewById(R.id.condition6);
        condition7 = findViewById(R.id.condition7);
        condition8 = findViewById(R.id.condition8);
        condition9 = findViewById(R.id.condition9);
        condition10 = findViewById(R.id.condition10);
        condition11 = findViewById(R.id.condition11);

        btn_printPdf = findViewById(R.id.printPdf);
        btn_saveImage = findViewById(R.id.saveImage);

        txtDateTime = findViewById(R.id.txt_desc_datatime);
        txtLatLngs = findViewById(R.id.txt_desc_latlngs);
        txtAddress = findViewById(R.id.txt_desc_address);
        txtPostal = findViewById(R.id.txt_desc_postal);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CapturePropertyPhoto.this);

        // cameraOpertions();
        showAlert();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


    }

    public void cameraOpertions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                //Permission not enabled request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                // show popup to request permission

                requestPermissions(permission, PERMISSION_CODE);

            } else {

                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
        }

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

    private void openCamera2() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri2 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri2);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera3() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri3 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri3);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera4() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri4 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri4);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera5() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri5 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri5);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera6() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri6 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri6);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera7() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri7 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri7);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera8() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri8 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri8);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera9() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri9 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri9);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera10() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri10 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri10);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    private void openCamera11() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
        image_uri11 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri11);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //called when image was captured from camera


        if (resultCode == RESULT_OK) {

            // image_uri = data.getData ();

            radioText.setText(selectedPlace);
            radioText1.setText(selectedPlace1);
            radioText2.setText(selectedPlace2);
            radioText3.setText(selectedPlace3);
            radioText4.setText(selectedPlace4);
            radioText5.setText(selectedPlace5);
            radioText6.setText(selectedPlace6);
            radioText7.setText(selectedPlace7);
            radioText8.setText(selectedPlace8);
            radioText9.setText(selectedPlace9);
            radioText10.setText(selectedPlace10);
            radioText11.setText(selectedPlace11);

            textView_Data = radioText.getText().toString().trim();
            textView_Data1 = radioText1.getText().toString().trim();
            textView_Data2 = radioText2.getText().toString().trim();
            textView_Data3 = radioText3.getText().toString().trim();
            textView_Data4 = radioText4.getText().toString().trim();
            textView_Data5 = radioText5.getText().toString().trim();
            textView_Data6 = radioText6.getText().toString().trim();
            textView_Data7 = radioText7.getText().toString().trim();
            textView_Data8 = radioText8.getText().toString().trim();
            textView_Data9 = radioText9.getText().toString().trim();
            textView_Data10 = radioText10.getText().toString().trim();
            textView_Data11 = radioText11.getText().toString().trim();

            //condition.setText (checkCondition);

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
            String latitude1 = sp.getString("Latitude", null);
            String longitude1 = sp.getString("Longitude", null);
            String address1 = sp.getString("Address", null);
            String postal1 = sp.getString("PostalCode", null);
            subLocality = sp.getString("subLocality", null);


            txtDateTime.setText("Date&Time :" + date);
            txtLatLngs.setText("la :" + latitude1 + " ,lo :" + longitude1);
            txtPostal.setText("Pin_Code :" + postal1);
            txtAddress.setText(address1);

            imgPreview.setImageURI(image_uri);
            imgPreview1.setImageURI(image_uri1);
            imgPreview2.setImageURI(image_uri2);
            imgPreview3.setImageURI(image_uri3);
            imgPreview4.setImageURI(image_uri4);
            imgPreview5.setImageURI(image_uri5);
            imgPreview6.setImageURI(image_uri6);
            imgPreview7.setImageURI(image_uri7);
            imgPreview8.setImageURI(image_uri8);
            imgPreview9.setImageURI(image_uri9);
            imgPreview10.setImageURI(image_uri10);
            imgPreview11.setImageURI(image_uri11);

            txt_DataTime = txtDateTime.getText().toString().trim();
            txt_LatLngs = txtLatLngs.getText().toString().trim();
            txt_Postal = txtPostal.getText().toString().trim();
            txt_Address = txtAddress.getText().toString().trim();

            conditionFormat();
            conditionFormat1();
            // openCamera1 ();

            if (radioGate.isChecked()) {
                radioGate.setChecked(true);
            }

        }
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Open Camera</font>"));
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>What Do You Want Capture Photo Or Record Video</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#FFFFFF'>Capture Photo</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        cameraOpertions();

                        if (textView_Data == null) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
                            builder.setTitle(Html.fromHtml("<font color='#FFFFFF' >Open Camera</font>"));
                            LayoutInflater inflater = getLayoutInflater();
                            View dialogLayout = inflater.inflate(R.layout.list_item, null);

                            //radioGroup = dialogLayout.findViewById (R.id.radioGroup);
                            radioGate = dialogLayout.findViewById(R.id.gate);
                            radioApproachroad = dialogLayout.findViewById(R.id.approachroad);
                            radioFrontage = dialogLayout.findViewById(R.id.frontage);
                            radioFrontlhs = dialogLayout.findViewById(R.id.frontlhs);
                            radioFrontrhs = dialogLayout.findViewById(R.id.frontrhs);
                            radioLeftBoundary = dialogLayout.findViewById(R.id.leftboundary);
                            radioRightBoundary = dialogLayout.findViewById(R.id.rightboundary);
                            radioPictureFromBackSide = dialogLayout.findViewById(R.id.picturefrombackside);
                            radioLocality1 = dialogLayout.findViewById(R.id.locality1);
                            radioLocality2 = dialogLayout.findViewById(R.id.locality2);
                            radioAnyOther1 = dialogLayout.findViewById(R.id.anyother1);
                            radioAnyOther2 = dialogLayout.findViewById(R.id.anyother2);

                            text_Comp = dialogLayout.findViewById(R.id.comp);
                            text_Comp1 = dialogLayout.findViewById(R.id.comp1);
                            text_Comp2 = dialogLayout.findViewById(R.id.comp2);
                            text_Comp3 = dialogLayout.findViewById(R.id.comp3);
                            text_Comp4 = dialogLayout.findViewById(R.id.comp4);
                            text_Comp5 = dialogLayout.findViewById(R.id.comp5);
                            text_Comp6 = dialogLayout.findViewById(R.id.comp6);
                            text_Comp7 = dialogLayout.findViewById(R.id.comp7);
                            text_Comp8 = dialogLayout.findViewById(R.id.comp8);
                            text_Comp9 = dialogLayout.findViewById(R.id.comp9);
                            text_Comp10 = dialogLayout.findViewById(R.id.comp10);
                            text_Comp11 = dialogLayout.findViewById(R.id.comp11);

                            radioApproachroad.setEnabled(false);
                            radioFrontage.setEnabled(false);
                            radioFrontlhs.setEnabled(false);
                            radioFrontrhs.setEnabled(false);
                            radioLeftBoundary.setEnabled(false);
                            radioRightBoundary.setEnabled(false);
                            radioPictureFromBackSide.setEnabled(false);
                            radioLocality1.setEnabled(false);
                            radioLocality2.setEnabled(false);
                            radioAnyOther1.setEnabled(false);
                            radioAnyOther2.setEnabled(false);

                            builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Ok</font>"),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            radioList();
                                        }
                                    });
                            builder.setView(dialogLayout);
                            builder.setCancelable(false);
                            AlertDialog dialog1 = builder.create();
                            dialog1.show();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                            Button nbutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                           //Set negative button background
                           nbutton.setBackgroundResource(R.drawable.button_shap);
                         //Set negative button text color
                           nbutton.setTextColor(Color.BLACK);

                        } else {

                            //Show Your Another AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
                            builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Open Camera</font>"));
                            LayoutInflater inflater = getLayoutInflater();
                            View dialogLayout = inflater.inflate(R.layout.list_item, null);

                            //radioGroup = dialogLayout.findViewById(R.id.radioGroup);
                            radioGate = dialogLayout.findViewById(R.id.gate);
                            radioApproachroad = dialogLayout.findViewById(R.id.approachroad);
                            radioFrontage = dialogLayout.findViewById(R.id.frontage);
                            radioFrontlhs = dialogLayout.findViewById(R.id.frontlhs);
                            radioFrontrhs = dialogLayout.findViewById(R.id.frontrhs);
                            radioLeftBoundary = dialogLayout.findViewById(R.id.leftboundary);
                            radioRightBoundary = dialogLayout.findViewById(R.id.rightboundary);
                            radioPictureFromBackSide = dialogLayout.findViewById(R.id.picturefrombackside);
                            radioLocality1 = dialogLayout.findViewById(R.id.locality1);
                            radioLocality2 = dialogLayout.findViewById(R.id.locality2);
                            radioAnyOther1 = dialogLayout.findViewById(R.id.anyother1);
                            radioAnyOther2 = dialogLayout.findViewById(R.id.anyother2);

                            text_Comp = dialogLayout.findViewById(R.id.comp);
                            text_Comp1 = dialogLayout.findViewById(R.id.comp1);
                            text_Comp2 = dialogLayout.findViewById(R.id.comp2);
                            text_Comp3 = dialogLayout.findViewById(R.id.comp3);
                            text_Comp4 = dialogLayout.findViewById(R.id.comp4);
                            text_Comp5 = dialogLayout.findViewById(R.id.comp5);
                            text_Comp6 = dialogLayout.findViewById(R.id.comp6);
                            text_Comp7 = dialogLayout.findViewById(R.id.comp7);
                            text_Comp8 = dialogLayout.findViewById(R.id.comp8);
                            text_Comp9 = dialogLayout.findViewById(R.id.comp9);
                            text_Comp10 = dialogLayout.findViewById(R.id.comp10);
                            text_Comp11 = dialogLayout.findViewById(R.id.comp11);

                            builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Ok</font>"),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            radioList();
                                        }
                                    });
                            builder.setView(dialogLayout);
                            builder.setCancelable(false);
                            AlertDialog dialog1 = builder.create();
                            dialog1.show();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                            Button nbutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                            //Set negative button background
                            nbutton.setBackgroundResource(R.drawable.button_shap);
                            //Set negative button text color
                            nbutton.setTextColor(Color.BLACK);
                            radioComplitedOption();


                        }
                    }
                });
        builder.setNeutralButton(Html.fromHtml("<font color='#FFFFFF'>Record Video</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        cameraOpertions();
                        Intent intent = new Intent(CapturePropertyPhoto.this, RecordingPropertyVideo.class);
                        startActivity(intent);

                    }
                });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Change the alert dialog background color
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

       /* Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        //Set negative button background
        nbutton.setBackgroundResource(R.drawable.button_shap);
        //Set negative button text color
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button background
        pbutton.setBackgroundResource(R.drawable.button_shap);
        //Set positive button text color
        pbutton.setTextColor(Color.BLACK);*/
    }

    public void radioList() {
        if (radioGate.isChecked()) {
            selectedPlace = radioGate.getText().toString().trim();
            openCamera();
        } else if (radioApproachroad.isChecked()) {
            selectedPlace1 = radioApproachroad.getText().toString().trim();
            openCamera1();
        } else if (radioFrontage.isChecked()) {
            selectedPlace2 = radioFrontage.getText().toString().trim();
            openCamera2();
        } else if (radioFrontlhs.isChecked()) {
            selectedPlace3 = radioFrontlhs.getText().toString().trim();
            openCamera3();
        } else if (radioFrontrhs.isChecked()) {
            selectedPlace4 = radioFrontrhs.getText().toString().trim();
            openCamera4();
        } else if (radioLeftBoundary.isChecked()) {
            selectedPlace5 = radioLeftBoundary.getText().toString().trim();
            openCamera5();
        } else if (radioRightBoundary.isChecked()) {
            selectedPlace6 = radioRightBoundary.getText().toString().trim();
            openCamera6();
        } else if (radioPictureFromBackSide.isChecked()) {
            selectedPlace7 = radioPictureFromBackSide.getText().toString().trim();
            openCamera7();
        } else if (radioLocality1.isChecked()) {
            selectedPlace8 = radioLocality1.getText().toString().trim();
            openCamera8();
        } else if (radioLocality2.isChecked()) {
            selectedPlace9 = radioLocality2.getText().toString().trim();
            openCamera9();
        } else if (radioAnyOther1.isChecked()) {
            selectedPlace10 = radioAnyOther1.getText().toString().trim();
            openCamera10();
        } else if (radioAnyOther2.isChecked()) {
            selectedPlace11 = radioAnyOther2.getText().toString().trim();
            openCamera11();
        } else {
            Toast.makeText(getApplicationContext(), selectedPlace, Toast.LENGTH_LONG).show(); // print the value of selected super star
        }
    }

    public void conditionCheck() {
        if (radioButton.isChecked()) {
            if (radioGate.isChecked()) {
                checkCondition = radioButton.getText().toString();
                condition.setText(checkCondition);
            } else if (radioApproachroad.isChecked()) {
                checkCondition1 = radioButton.getText().toString();
                condition1.setText(checkCondition1);
            } else if (radioFrontage.isChecked()) {
                checkCondition2 = radioButton.getText().toString();
                condition2.setText(checkCondition2);
            } else if (radioFrontlhs.isChecked()) {
                checkCondition3 = radioButton.getText().toString();
                condition3.setText(checkCondition3);
            } else if (radioFrontrhs.isChecked()) {
                checkCondition4 = radioButton.getText().toString();
                condition4.setText(checkCondition4);
            } else if (radioLeftBoundary.isChecked()) {
                checkCondition5 = radioButton.getText().toString();
                condition5.setText(checkCondition5);
            } else if (radioRightBoundary.isChecked()) {
                checkCondition6 = radioButton.getText().toString();
                condition6.setText(checkCondition6);
            } else if (radioPictureFromBackSide.isChecked()) {
                checkCondition7 = radioButton.getText().toString();
                condition7.setText(checkCondition7);
            } else if (radioLocality1.isChecked()) {
                checkCondition8 = radioButton.getText().toString();
                condition8.setText(checkCondition8);
            } else if (radioLocality2.isChecked()) {
                checkCondition9 = radioButton.getText().toString();
                condition9.setText(checkCondition9);
            } else if (radioAnyOther1.isChecked()) {
                checkCondition10 = radioButton.getText().toString();
                condition10.setText(checkCondition10);
            } else if (radioAnyOther2.isChecked()) {
                checkCondition11 = radioButton.getText().toString();
                condition11.setText(checkCondition11);
            }

        } else {
            Toast.makeText(this, "Please select radiobutton", Toast.LENGTH_SHORT).show();
        }
    }

    public void conditionCheck1() {
        if (radioGate.isChecked()) {
            checkCondition = textView.getText().toString();
            condition.setText(checkCondition);
            txt_Condition = condition.getText().toString().trim();
        } else if (radioApproachroad.isChecked()) {
            checkCondition1 = textView.getText().toString();
            condition1.setText(checkCondition1);
            txt_Condition = condition1.getText().toString().trim();
        } else if (radioFrontage.isChecked()) {
            checkCondition2 = textView.getText().toString();
            condition2.setText(checkCondition2);
            txt_Condition = condition2.getText().toString().trim();
        } else if (radioFrontlhs.isChecked()) {
            checkCondition3 = textView.getText().toString();
            condition3.setText(checkCondition3);
            txt_Condition = condition3.getText().toString().trim();
        } else if (radioFrontrhs.isChecked()) {
            checkCondition4 = textView.getText().toString();
            condition4.setText(checkCondition4);
            txt_Condition = condition4.getText().toString().trim();
        } else if (radioLeftBoundary.isChecked()) {
            checkCondition5 = textView.getText().toString();
            condition5.setText(checkCondition5);
            txt_Condition = condition5.getText().toString().trim();
        } else if (radioRightBoundary.isChecked()) {
            checkCondition6 = textView.getText().toString();
            condition6.setText(checkCondition6);
            txt_Condition = condition6.getText().toString().trim();
        } else if (radioPictureFromBackSide.isChecked()) {
            checkCondition7 = textView.getText().toString();
            condition7.setText(checkCondition7);
            txt_Condition = condition7.getText().toString().trim();
        } else if (radioLocality1.isChecked()) {
            checkCondition8 = textView.getText().toString();
            condition8.setText(checkCondition8);
            txt_Condition = condition8.getText().toString().trim();
        } else if (radioLocality2.isChecked()) {
            checkCondition9 = textView.getText().toString();
            condition9.setText(checkCondition9);
            txt_Condition = condition9.getText().toString().trim();
        } else if (radioAnyOther1.isChecked()) {
            checkCondition10 = textView.getText().toString();
            condition10.setText(checkCondition10);
            txt_Condition = condition10.getText().toString().trim();
        } else if (radioAnyOther2.isChecked()) {
            checkCondition11 = textView.getText().toString();
            condition11.setText(checkCondition11);
            txt_Condition = condition11.getText().toString().trim();
        }
    }

    public void conditionCheck2() {
        if (radioLocality1.isChecked()) {
            checkCondition8 = textView1.getText().toString();
            condition8.setText(checkCondition8);
            txt_Condition = condition8.getText().toString().trim();
        } else if (radioLocality2.isChecked()) {
            checkCondition9 = textView1.getText().toString();
            condition9.setText(checkCondition9);
            txt_Condition = condition9.getText().toString().trim();
        } else if (radioAnyOther1.isChecked()) {
            checkCondition10 = textView1.getText().toString();
            condition10.setText(checkCondition10);
            txt_Condition = condition10.getText().toString().trim();
        } else if (radioAnyOther2.isChecked()) {
            checkCondition11 = textView1.getText().toString();
            condition11.setText(checkCondition11);
            txt_Condition = condition11.getText().toString().trim();
        } else {
            Toast.makeText(getApplicationContext(), selectedPlace, Toast.LENGTH_LONG).show(); // print the value of selected super star
        }

    }

    public void conditionFormat() {

        if (radioGate.isChecked() || radioApproachroad.isChecked() || radioFrontage.isChecked() || radioFrontlhs.isChecked() ||
                radioFrontrhs.isChecked() || radioLeftBoundary.isChecked() || radioRightBoundary.isChecked() ||
                radioPictureFromBackSide.isChecked()) {

            alertCondition1();
        }
    }

    public void conditionFormat1() {

        if (radioLocality1.isChecked() || radioLocality2.isChecked() || radioAnyOther1.isChecked() || radioAnyOther2.isChecked()) {

            alertCondition2();

        }

    }

    public void alertCondition1() {
        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(CapturePropertyPhoto.this);
        dialog.setContentView(R.layout.condition1);
        dialog.setCancelable(false);
        Button button = dialog.findViewById(R.id.button);
        textView = dialog.findViewById(R.id.editText);
        radioButton = dialog.findViewById(R.id.ok);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButton.setChecked(false);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!radioButton.isChecked()) {

                    if (TextUtils.isEmpty(textView.getText())) {
                        textView.setError("Name is not empty");
                        Toast.makeText(CapturePropertyPhoto.this, "Please Select Radio Button", Toast.LENGTH_SHORT).show();
                    } else {
                        conditionCheck1();
                        dialog.dismiss();
                        showAlert();
                    }
                } else {
                    conditionCheck();
                    dialog.dismiss(); //finish();
                    showAlert();
                }
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void alertCondition2() {
        //Show Your Another AlertDialog
        final Dialog dialog1 = new Dialog(CapturePropertyPhoto.this);
        dialog1.setContentView(R.layout.condition2);
        dialog1.setCancelable(false);
        Button button1 = dialog1.findViewById(R.id.button);
        button2 = dialog1.findViewById(R.id.button1);
        textView1 = dialog1.findViewById(R.id.editText);
        button2.setVisibility(View.INVISIBLE);

        if (radioAnyOther2.isChecked()) {
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textView1.getText())) {
                    textView1.setError("Fill the TextField");
                    Toast.makeText(CapturePropertyPhoto.this, "please fill text view", Toast.LENGTH_SHORT).show();
                } else {
                    conditionCheck2();
                    dialog1.dismiss();
                    showAlert();

                }
                //finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conditionCheck2();
                dialog1.dismiss();
            }
        });

        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void radioComplitedOption() {

        if (textView_Data != null) {
            if (textView_Data.equals("Gate")) {
                text_Comp.setText(completed);

                radioGate.setEnabled(false);

                if (textView_Data1.equals("Approach Road")) {

                    text_Comp.setText(completed);
                    text_Comp1.setText(completed);

                    radioGate.setEnabled(false);
                    radioApproachroad.setEnabled(false);

               /*     radioRightBoundary.setEnabled(false);
                    radioPictureFromBackSide.setEnabled(false);
                    radioLocality1.setEnabled(false);
                    radioLocality2.setEnabled(false);
                    radioAnyOther1.setEnabled(false);
                    radioAnyOther2.setEnabled(false);
*/
                    if (textView_Data2.equals("Frontage")) {

                        text_Comp.setText(completed);
                        text_Comp1.setText(completed);
                        text_Comp2.setText(completed);

                        radioGate.setEnabled(false);
                        radioApproachroad.setEnabled(false);
                        radioFrontage.setEnabled(false);

                        if (textView_Data3.equals("Front LHS")) {

                            text_Comp.setText(completed);
                            text_Comp1.setText(completed);
                            text_Comp2.setText(completed);
                            text_Comp3.setText(completed);

                            radioGate.setEnabled(false);
                            radioApproachroad.setEnabled(false);
                            radioFrontage.setEnabled(false);
                            radioFrontlhs.setEnabled(false);

                            if (textView_Data4.equals("Front RHS")) {

                                text_Comp.setText(completed);
                                text_Comp1.setText(completed);
                                text_Comp2.setText(completed);
                                text_Comp3.setText(completed);
                                text_Comp4.setText(completed);

                                radioGate.setEnabled(false);
                                radioApproachroad.setEnabled(false);
                                radioFrontage.setEnabled(false);
                                radioFrontlhs.setEnabled(false);
                                radioFrontrhs.setEnabled(false);

                                if (textView_Data5.equals("Left Boundary")) {

                                    text_Comp.setText(completed);
                                    text_Comp1.setText(completed);
                                    text_Comp2.setText(completed);
                                    text_Comp3.setText(completed);
                                    text_Comp4.setText(completed);
                                    text_Comp5.setText(completed);

                                    radioGate.setEnabled(false);
                                    radioApproachroad.setEnabled(false);
                                    radioFrontage.setEnabled(false);
                                    radioFrontlhs.setEnabled(false);
                                    radioFrontrhs.setEnabled(false);
                                    radioLeftBoundary.setEnabled(false);

                                    if (textView_Data6.equals("Right Boundary")) {

                                        text_Comp.setText(completed);
                                        text_Comp1.setText(completed);
                                        text_Comp2.setText(completed);
                                        text_Comp3.setText(completed);
                                        text_Comp4.setText(completed);
                                        text_Comp5.setText(completed);
                                        text_Comp6.setText(completed);

                                        radioGate.setEnabled(false);
                                        radioApproachroad.setEnabled(false);
                                        radioFrontage.setEnabled(false);
                                        radioFrontlhs.setEnabled(false);
                                        radioFrontrhs.setEnabled(false);
                                        radioLeftBoundary.setEnabled(false);
                                        radioRightBoundary.setEnabled(false);

                                        if (textView_Data7.equals("Picture From Back Side")) {

                                            text_Comp.setText(completed);
                                            text_Comp1.setText(completed);
                                            text_Comp2.setText(completed);
                                            text_Comp3.setText(completed);
                                            text_Comp4.setText(completed);
                                            text_Comp5.setText(completed);
                                            text_Comp6.setText(completed);
                                            text_Comp7.setText(completed);

                                            radioGate.setEnabled(false);
                                            radioApproachroad.setEnabled(false);
                                            radioFrontage.setEnabled(false);
                                            radioFrontlhs.setEnabled(false);
                                            radioFrontrhs.setEnabled(false);
                                            radioLeftBoundary.setEnabled(false);
                                            radioRightBoundary.setEnabled(false);
                                            radioPictureFromBackSide.setEnabled(false);

                                            if (textView_Data8.equals("Locality1")) {

                                                text_Comp.setText(completed);
                                                text_Comp1.setText(completed);
                                                text_Comp2.setText(completed);
                                                text_Comp3.setText(completed);
                                                text_Comp4.setText(completed);
                                                text_Comp5.setText(completed);
                                                text_Comp6.setText(completed);
                                                text_Comp7.setText(completed);
                                                text_Comp8.setText(completed);

                                                radioGate.setEnabled(false);
                                                radioApproachroad.setEnabled(false);
                                                radioFrontage.setEnabled(false);
                                                radioFrontlhs.setEnabled(false);
                                                radioFrontrhs.setEnabled(false);
                                                radioLeftBoundary.setEnabled(false);
                                                radioRightBoundary.setEnabled(false);
                                                radioPictureFromBackSide.setEnabled(false);
                                                radioLocality1.setEnabled(false);

                                                if (textView_Data9.equals("Locality2")) {

                                                    text_Comp.setText(completed);
                                                    text_Comp1.setText(completed);
                                                    text_Comp2.setText(completed);
                                                    text_Comp3.setText(completed);
                                                    text_Comp4.setText(completed);
                                                    text_Comp5.setText(completed);
                                                    text_Comp6.setText(completed);
                                                    text_Comp7.setText(completed);
                                                    text_Comp8.setText(completed);
                                                    text_Comp9.setText(completed);

                                                    radioGate.setEnabled(false);
                                                    radioApproachroad.setEnabled(false);
                                                    radioFrontage.setEnabled(false);
                                                    radioFrontlhs.setEnabled(false);
                                                    radioFrontrhs.setEnabled(false);
                                                    radioLeftBoundary.setEnabled(false);
                                                    radioRightBoundary.setEnabled(false);
                                                    radioPictureFromBackSide.setEnabled(false);
                                                    radioLocality1.setEnabled(false);
                                                    radioLocality2.setEnabled(false);

                                                    if (textView_Data10.equals("Any Other1")) {

                                                        text_Comp.setText(completed);
                                                        text_Comp1.setText(completed);
                                                        text_Comp2.setText(completed);
                                                        text_Comp3.setText(completed);
                                                        text_Comp4.setText(completed);
                                                        text_Comp5.setText(completed);
                                                        text_Comp6.setText(completed);
                                                        text_Comp7.setText(completed);
                                                        text_Comp8.setText(completed);
                                                        text_Comp9.setText(completed);
                                                        text_Comp10.setText(completed);

                                                        radioGate.setEnabled(false);
                                                        radioApproachroad.setEnabled(false);
                                                        radioFrontage.setEnabled(false);
                                                        radioFrontlhs.setEnabled(false);
                                                        radioFrontrhs.setEnabled(false);
                                                        radioLeftBoundary.setEnabled(false);
                                                        radioRightBoundary.setEnabled(false);
                                                        radioPictureFromBackSide.setEnabled(false);
                                                        radioLocality1.setEnabled(false);
                                                        radioLocality2.setEnabled(false);
                                                        radioAnyOther1.setEnabled(false);

                                                        if (textView_Data11.equals("Any Other2")) {

                                                            text_Comp.setText(completed);
                                                            text_Comp1.setText(completed);
                                                            text_Comp2.setText(completed);
                                                            text_Comp3.setText(completed);
                                                            text_Comp4.setText(completed);
                                                            text_Comp5.setText(completed);
                                                            text_Comp6.setText(completed);
                                                            text_Comp7.setText(completed);
                                                            text_Comp8.setText(completed);
                                                            text_Comp9.setText(completed);
                                                            text_Comp10.setText(completed);
                                                            text_Comp11.setText(completed);

                                                            radioGate.setEnabled(false);
                                                            radioApproachroad.setEnabled(false);
                                                            radioFrontage.setEnabled(false);
                                                            radioFrontlhs.setEnabled(false);
                                                            radioFrontrhs.setEnabled(false);
                                                            radioLeftBoundary.setEnabled(false);
                                                            radioRightBoundary.setEnabled(false);
                                                            radioPictureFromBackSide.setEnabled(false);
                                                            radioLocality1.setEnabled(false);
                                                            radioLocality2.setEnabled(false);
                                                            radioAnyOther1.setEnabled(false);
                                                            radioAnyOther2.setEnabled(false);

                                                        }

                                                    }

                                                } else {
                                                    Toast.makeText(this, "selected Plase 9 is null", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(this, "selected Plase 8 is null", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(this, "selected Plase 7 is null", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(this, "selected Plase 6 is null", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(this, "selected Plase 5 is null", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(this, "selected Plase 4 is null", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(this, "selected Plase 3 is null", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "selected Plase 2 is null", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "selected Plase 1 is null", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "selected Plase is null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "selected Plase is null", Toast.LENGTH_LONG).show();
        }
    }

//CREATE PDF FILE IN APPLICATION

    public void showPdfDocument() {

        try {

            txt_DataTime = txtDateTime.getText().toString().trim();
            txt_LatLngs = txtLatLngs.getText().toString().trim();
            txt_Postal = txtPostal.getText().toString().trim();
            txt_Address = txtAddress.getText().toString().trim();

            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri);
            bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri1);
            bitmap2 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri2);
            bitmap3 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri3);
            bitmap4 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri4);
            bitmap5 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri5);
            bitmap6 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri6);
            bitmap7 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri7);
            bitmap8 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri8);
            bitmap9 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri9);
            bitmap10 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri10);
            bitmap11 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_uri11);

            scaledbMap = Bitmap.createScaledBitmap(bitmap, 680, 830, false);
            scaledbMap1 = Bitmap.createScaledBitmap(bitmap1, 680, 830, false);
            scaledbMap2 = Bitmap.createScaledBitmap(bitmap2, 680, 830, false);
            scaledbMap3 = Bitmap.createScaledBitmap(bitmap3, 680, 830, false);
            scaledbMap4 = Bitmap.createScaledBitmap(bitmap4, 680, 830, false);
            scaledbMap5 = Bitmap.createScaledBitmap(bitmap5, 680, 830, false);
            scaledbMap6 = Bitmap.createScaledBitmap(bitmap6, 680, 830, false);
            scaledbMap7 = Bitmap.createScaledBitmap(bitmap7, 680, 830, false);
            scaledbMap8 = Bitmap.createScaledBitmap(bitmap4, 680, 830, false);
            scaledbMap9 = Bitmap.createScaledBitmap(bitmap5, 680, 830, false);
            scaledbMap10 = Bitmap.createScaledBitmap(bitmap6, 680, 830, false);
            scaledbMap11 = Bitmap.createScaledBitmap(bitmap7, 680, 830, false);

            //scaledbMap1 = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void printPdf() {

        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(700, 950, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        paint.setTextSize(18f);
        paint.setColor(Color.rgb(0, 0, 0));

        canvas.drawText(txt_DataTime, 20, 25, paint);
        canvas.drawBitmap(scaledbMapicone, 60, 25, paint);
        canvas.drawText(txt_LatLngs, 20, 45, paint);
        canvas.drawText(txt_Address, 20, 65, paint);
        canvas.drawText(txt_Postal, 20, 85, paint);

        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));

        canvas.drawLine(10, 100, 550, 100, forLinePaint);

        paint.setTextSize(25f);
        canvas.drawText(radioText.getText().toString().trim(), 20, 120, paint);
        paint.setTextSize(25f);
        canvas.drawText(condition.getText().toString().trim(), 20, 150, paint);
        canvas.drawBitmap(scaledbMap, 5, 160, paint);
        pdfDocument.finishPage(page);

        //Start 2Nd Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas1 = page.getCanvas();
        paint.setTextSize(25f);
        canvas1.drawText(radioText1.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas1.drawText(condition1.getText().toString().trim(), 20, 80, paint);
        canvas1.drawBitmap(scaledbMap1, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 3rd Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas2 = page.getCanvas();
        paint.setTextSize(25f);
        canvas2.drawText(radioText2.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas2.drawText(condition2.getText().toString().trim(), 20, 80, paint);
        canvas2.drawBitmap(scaledbMap2, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 4th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas3 = page.getCanvas();
        paint.setTextSize(25f);
        canvas3.drawText(radioText3.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas3.drawText(condition3.getText().toString().trim(), 20, 80, paint);
        canvas3.drawBitmap(scaledbMap3, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 5th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas4 = page.getCanvas();
        paint.setTextSize(25f);
        canvas4.drawText(radioText4.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas4.drawText(condition4.getText().toString().trim(), 20, 80, paint);
        canvas4.drawBitmap(scaledbMap4, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 6th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas5 = page.getCanvas();
        paint.setTextSize(25f);
        canvas5.drawText(radioText5.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas5.drawText(condition5.getText().toString().trim(), 20, 80, paint);
        canvas5.drawBitmap(scaledbMap5, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 7th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas6 = page.getCanvas();
        paint.setTextSize(25f);
        canvas6.drawText(radioText6.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas6.drawText(condition6.getText().toString().trim(), 20, 80, paint);
        canvas6.drawBitmap(scaledbMap6, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 8th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas7 = page.getCanvas();
        paint.setTextSize(25f);
        canvas7.drawText(radioText7.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas7.drawText(condition7.getText().toString().trim(), 20, 80, paint);
        canvas7.drawBitmap(scaledbMap7, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 9th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas8 = page.getCanvas();
        paint.setTextSize(25f);
        canvas8.drawText(radioText8.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas8.drawText(condition8.getText().toString().trim(), 20, 80, paint);
        canvas8.drawBitmap(scaledbMap8, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 10th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas9 = page.getCanvas();
        paint.setTextSize(25f);
        canvas9.drawText(radioText9.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas9.drawText(condition9.getText().toString().trim(), 20, 80, paint);
        canvas9.drawBitmap(scaledbMap9, 5, 100, paint);

        pdfDocument.finishPage(page);


        //Start 11th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas10 = page.getCanvas();
        paint.setTextSize(25f);
        canvas10.drawText(radioText10.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas10.drawText(condition10.getText().toString().trim(), 20, 80, paint);
        canvas10.drawBitmap(scaledbMap10, 5, 100, paint);

        pdfDocument.finishPage(page);

        //Start 11th Page PdfDocument

        page = pdfDocument.startPage(pageInfo);
        Canvas canvas11 = page.getCanvas();
        paint.setTextSize(25f);
        canvas11.drawText(radioText11.getText().toString().trim(), 20, 50, paint);
        paint.setTextSize(25f);
        canvas11.drawText(condition11.getText().toString().trim(), 20, 80, paint);
        canvas11.drawBitmap(scaledbMap11, 5, 100, paint);

        pdfDocument.finishPage(page);
        //Start 12th Page PdfDocument

        String gate = "Gate";                   String RightBoundary = "Right Boundary";
        String ApproachRoad = "Approach Road";  String Picturefrombackside = "Picture from back side";
        String Frontage = "Frontage";           String Locality1 = "Locality1";
        String FrontLHS = "Front LHS";          String Locality2 = "Locality2";
        String FrontRHS = "Front RHS";          String AnyOther1 = "AnyOther1";
        String LeftBoundary = "Left Boundary";  String AnyOther2 = "AnyOther2";


        page = pdfDocument.startPage(pageInfo);
        Canvas canvas12 = page.getCanvas();

        paint.setTextSize(30f);
        canvas12.drawText("Description !", 20, 40, paint);

        canvas12.drawText(gate, 20, 100, paint);
        canvas12.drawText("desc :"+condition.getText().toString().trim(), 20, 130, paint);

        canvas12.drawText(ApproachRoad, 20, 160, paint);
        canvas12.drawText("desc :"+condition1.getText().toString().trim(), 20, 190, paint);

        canvas12.drawText(Frontage, 20, 220, paint);
        canvas12.drawText("desc :"+condition2.getText().toString().trim(), 20, 250, paint);

        canvas12.drawText(FrontLHS, 20, 280, paint);
        canvas12.drawText("desc :"+condition3.getText().toString().trim(), 20, 310, paint);


        canvas12.drawText(FrontRHS, 20, 340, paint);
        canvas12.drawText("desc :"+condition4.getText().toString().trim(), 20, 370, paint);

        canvas12.drawText(LeftBoundary, 20, 400, paint);
        canvas12.drawText("desc :"+condition5.getText().toString().trim(), 20, 430, paint);

        canvas12.drawText(RightBoundary, 20, 460, paint);
        canvas12.drawText("desc : "+condition6.getText().toString().trim(), 20, 490, paint);

        canvas12.drawText(Picturefrombackside, 20, 520, paint);
        canvas12.drawText("desc : "+condition7.getText().toString().trim(), 20, 550, paint);

        canvas12.drawText(Locality1, 20, 580, paint);
        canvas12.drawText("desc : "+condition8.getText().toString().trim(), 20, 610, paint);

        canvas12.drawText(Locality2, 20, 640, paint);
        canvas12.drawText("desc : "+condition9.getText().toString().trim(), 20, 670, paint);

        canvas12.drawText(AnyOther1, 20, 700, paint);
        canvas12.drawText("desc : "+condition10.getText().toString().trim(), 20, 730, paint);

        canvas12.drawText(AnyOther2, 20, 760, paint);
        canvas12.drawText("desc : "+condition11.getText().toString().trim(), 20, 790, paint);


        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30f);
        canvas12.drawText("Thank You!", canvas12.getWidth() / 2, 870, paint);

        pdfDocument.finishPage(page);

        File path = new File(Environment.getExternalStorageDirectory() + "/" + "Third Eye Pdfdocument");
        path.mkdir();

       // File path1 = new File(Environment.getExternalStorageDirectory() + "/" + "Third Eye Pdfdocument" + "/" + subLocality+".pdf");

        try {
            pdfDocument.writeTo(new FileOutputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();
        Toast.makeText(this, "Pdf Successfully Created", Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(ShowImage.this,SavePdfandShow.class);
//        startActivity(intent);
    }

}