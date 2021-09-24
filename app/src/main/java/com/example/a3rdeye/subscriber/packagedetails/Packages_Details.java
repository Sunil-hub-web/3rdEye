package com.example.a3rdeye.subscriber.packagedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3rdeye.R;

public class Packages_Details extends AppCompatActivity {

    ViewPager viewPager;
    Button btn_Next;
    TextView text;

    String id,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages__details);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Intent intent1 = getIntent();
        userid = intent1.getStringExtra("userid");


        viewPager = findViewById(R.id.viewPager);
        text = findViewById(R.id.text);

        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);

        viewPager.setAdapter(new SliderAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Toast.makeText(Packages_Details.this, "" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_Next = findViewById(R.id.next);

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(viewPager.getCurrentItem());

                //Toast.makeText(Packages_Details.this, ""+viewPager.getCurrentItem(), Toast.LENGTH_SHORT).show();

                SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
                String s1 = sp.getString("type", null);

                if (s1 != null) {

                    Toast.makeText(Packages_Details.this, "Selected Packages : " + s1, Toast.LENGTH_LONG).show();

                    if(userid != null){

                        Intent intent  = new Intent(Packages_Details.this, ValueAddes_Services.class);
                        intent.putExtra("userid",userid);
                        startActivity(intent);

                    }else{

                        Intent intent  = new Intent(Packages_Details.this, ValueAddes_Services.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(Packages_Details.this, "Select Your Packages Details", Toast.LENGTH_LONG).show();

                }

                /* if(viewPager.getCurrentItem() == 0){

                     Toast.makeText(Packages_Details.this, "Sliver", Toast.LENGTH_SHORT).show();

                 }else if(viewPager.getCurrentItem() == 1){

                     Toast.makeText(Packages_Details.this, "Gold", Toast.LENGTH_SHORT).show();

                 }else if(viewPager.getCurrentItem() == 2){

                     Toast.makeText(Packages_Details.this, "Platinum", Toast.LENGTH_SHORT).show();
                 }*/

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}