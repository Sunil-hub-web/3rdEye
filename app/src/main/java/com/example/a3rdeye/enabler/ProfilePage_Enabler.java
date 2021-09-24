package com.example.a3rdeye.enabler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.viewalldetails.AccountDetails;
import com.example.a3rdeye.enabler.viewalldetails.BikeDetails;
import com.example.a3rdeye.enabler.viewalldetails.UserDetails;
import com.example.a3rdeye.subscriber.About_Us;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePage_Enabler extends AppCompatActivity {

    ImageView imageView;
    public static final int IMAGE_CODE = 1;
    CircleImageView circleImageView;
    Uri imageUri;
    BottomNavigationView bottomNavigationView;
    TextView text_UserDetails,text_BankDetails,text_BikeDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page__enabler);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        imageView = findViewById(R.id.imageview);
        circleImageView = findViewById(R.id.profile_image);
        text_UserDetails = findViewById(R.id.accountDetails);
        text_BankDetails = findViewById(R.id.bankDetails);
        text_BikeDetails = findViewById(R.id.bikeDetails);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMAGE_CODE);
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile :

                        //startActivity(new Intent(getApplicationContext(),DashBaord.class));
                        // overridePendingTransition(0,0);
                        return true;

                    case R.id.home :
                        startActivity(new Intent(getApplicationContext(), HomePage_Enabler.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about :

                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        text_UserDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfilePage_Enabler.this, UserDetails.class);
                startActivity(intent);
            }
        });
        text_BankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfilePage_Enabler.this, AccountDetails.class);
                startActivity(intent);
            }
        });

        text_BikeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfilePage_Enabler.this, BikeDetails.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData() !=null){

            imageUri = data.getData();
            circleImageView.setImageURI(imageUri);
        }
    }
}