package com.example.a3rdeye.enabler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUs extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.about);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.click :
                        //startActivity(new Intent(getApplicationContext(),DashBaord.class));
                        //overridePendingTransition(0,0);
                        //return true;

                    case R.id.home :
                        startActivity(new Intent(getApplicationContext(), HomePage_Enabler.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.about :
                        return true;
                }
                return false;
            }
        });
    }
}