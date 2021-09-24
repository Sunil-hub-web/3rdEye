package com.example.a3rdeye.enabler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.a3rdeye.ProfilePage_Subscriber;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.About_Us;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage_Enabler extends AppCompatActivity {

    Button button1, button2, button3, button4;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page__enabler);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        button1 = findViewById(R.id.button1);
        button4 = findViewById(R.id.button4);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile:

                        startActivity(new Intent(getApplicationContext(), ProfilePage_Enabler.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.about:

                        startActivity(new Intent(getApplicationContext(), AboutUs.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Enabler.this,ProfilePage_Enabler.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Enabler.this,AboutUs.class);
                startActivity(intent);

            }
        });


    }
}