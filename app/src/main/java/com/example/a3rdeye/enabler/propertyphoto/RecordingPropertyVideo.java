package com.example.a3rdeye.enabler.propertyphoto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.a3rdeye.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RecordingPropertyVideo extends AppCompatActivity {

    VideoView videoView, videoView1;
    Uri videoUri, videoUri1;
    public static int VIDEO_REQUEST = 101;
    public static int VIDEO_REQUEST1 = 1001;
    Button play_Video, play_Video1;
    MediaController mediaController;
    RadioButton radioButton, radioButton1;
    TextView textView1,radioText,radioText1,condition,condition1,txtDateTime, txtLatLngs, txtAddress, txtPostal;
    String selectedPlace,checkCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_property_video);

        videoView = findViewById(R.id.videoPreview);
        videoView1 = findViewById(R.id.videoPreview1);
        play_Video = findViewById(R.id.play);
        play_Video1 = findViewById(R.id.play1);

        radioText = findViewById(R.id.radiotext);
        radioText1 = findViewById(R.id.radiotext1);

        condition = findViewById(R.id.condition);
        condition1 = findViewById(R.id.condition1);

        txtDateTime = findViewById(R.id.txt_desc_datatime);
        txtLatLngs = findViewById(R.id.txt_desc_latlngs);
        txtAddress = findViewById(R.id.txt_desc_address);
        txtPostal = findViewById(R.id.txt_desc_postal);

        panaromaicVideo();



    }

    public void captureVideo() {


        Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (videointent.resolveActivity(getPackageManager()) != null) {

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
            videoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // set video quality
            videointent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            videointent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
            videointent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri); // set the image file
            startActivityForResult(videointent, VIDEO_REQUEST1);
        }
    }

    public void captureVideo1() {

        Intent videointent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (videointent.resolveActivity(getPackageManager()) != null) {

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From The Camera");
            videoUri1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // set video quality
            videointent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            videointent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
            videointent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri1); // set the image file
            startActivityForResult(videointent, VIDEO_REQUEST1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());

            txtDateTime.setText("Date&Time :" + date);

            mediaController = new MediaController(RecordingPropertyVideo.this);
            videoView.setMediaController(mediaController);
            videoView1.setMediaController(mediaController);
            mediaController.show();

            //videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();

            //videoUri1 = data.getData();
            videoView1.setVideoURI(videoUri1);
            videoView1.requestFocus();
            videoView1.start();

            alertCondition2();


        }
    }


    public void panaromaicVideo() {
        //Show Your Another AlertDialog
        final Dialog dialog1 = new Dialog(RecordingPropertyVideo.this);
        dialog1.setContentView(R.layout.panaromic);
        dialog1.setCancelable(false);

        radioButton = dialog1.findViewById(R.id.panaromicvideo1);
        radioButton1 = dialog1.findViewById(R.id.panaromicvideo2);
        Button button = dialog1.findViewById(R.id.panaromicbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!radioButton.isChecked() && !radioButton1.isChecked()) {

                    Toast.makeText(RecordingPropertyVideo.this, "Please Select AnyOne Option", Toast.LENGTH_SHORT).show();
                } else {

                    if (radioButton.isChecked()) {

                        selectedPlace = radioButton.getText().toString().trim();
                        radioText.setText(selectedPlace);
                        captureVideo();
                        dialog1.dismiss();


                    } else {
                        if (radioButton1.isChecked()) {

                            selectedPlace = radioButton1.getText().toString().trim();
                            radioText1.setText(selectedPlace);
                            captureVideo1();
                            dialog1.dismiss();
                        }
                    }
                }
            }
        });
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void alertCondition2() {
        //Show Your Another AlertDialog
        final Dialog dialog1 = new Dialog(RecordingPropertyVideo.this);
        dialog1.setContentView(R.layout.condition2);
        dialog1.setCancelable(false);
        Button button1 = dialog1.findViewById(R.id.button);
        Button button2 = dialog1.findViewById(R.id.button1);
        textView1 = dialog1.findViewById(R.id.editText);

        button2.setVisibility(View.INVISIBLE);

        if(radioButton1.isChecked()){
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textView1.getText())) {
                    textView1.setError("Fill the TextField");
                    Toast.makeText(RecordingPropertyVideo.this, "please fill text view", Toast.LENGTH_SHORT).show();
                } else {
                    conditionCheck();
                    dialog1.dismiss();
                    panaromaicVideo();
                }
                //finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(textView1.getText())) {
                    textView1.setError("Fill the TextField");
                    Toast.makeText(RecordingPropertyVideo.this, "please fill text view", Toast.LENGTH_SHORT).show();
                } else {
                    conditionCheck();
                    dialog1.dismiss();
                }


                //showAlert ( );
            }
        });
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void conditionCheck(){

        if(radioButton.isChecked()){
            checkCondition = textView1.getText().toString();
            condition.setText(checkCondition);
        }
        else if(radioButton1.isChecked()){
            checkCondition = textView1.getText().toString();
            condition1.setText(checkCondition);
        }
        else{}

    }
}