package com.example.a3rdeye.subscriber.geofence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;
import com.example.a3rdeye.subscriber.viewalldetails.AccountDetails;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoCalling extends AppCompatActivity {

    String fname, lname, mobile;

    TextView text_FullName, text_MobileNo, text_Name;
    ImageView image_click, image_wrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_calling);

        text_FullName = findViewById(R.id.fullname);
        text_MobileNo = findViewById(R.id.mobile);
        text_Name = findViewById(R.id.name);
        image_click = findViewById(R.id.click);
        image_wrong = findViewById(R.id.wrong);

        // Initialize default options for Jitsi Meet conferences.
        URL serverURL;
        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            serverURL = new URL("https://meet.jit.si");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                // When using JaaS, set the obtained JWT here
                //.setToken("MyJWT")
                .setWelcomePageEnabled(false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        fname = SharedPrefManager.getInstance (VideoCalling.this).getUser ().getFname ();
        lname = SharedPrefManager.getInstance (VideoCalling.this).getUser ().getLname ();
        mobile = SharedPrefManager.getInstance (VideoCalling.this).getUser ().getMobileno ();

        text_FullName.setText(fname + " " + lname);
        text_MobileNo.setText(mobile);

        char str = fname.charAt(0);
        text_Name.setText(String.valueOf(str));


        image_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = text_MobileNo.getText().toString();

                if (text.length() > 0) {
                    // Build options object for joining the conference. The SDK will merge the default
                    // one we set earlier and this one when joining.
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(text)
                            .build();
                    // Launch the new activity with the given options. The launch() method takes care
                    // of creating the required Intent and passing the options.
                    JitsiMeetActivity.launch(VideoCalling.this, options);
                }
            }

        });

        image_wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(VideoCalling.this, DirectionMapForGeofence.class);
                startActivity(intent);
            }
        });


    }
}