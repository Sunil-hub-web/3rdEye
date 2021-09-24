package com.example.a3rdeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a3rdeye.ProfilePage_Subscriber;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;
import com.example.a3rdeye.subscriber.packagedetails.SliderAdapter;

public class PackagesDetails_Edit extends AppCompatActivity {

    ViewPager viewPager;
    Button btn_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_details__edit);

        viewPager = findViewById(R.id.viewpager);
        btn_Next = findViewById(R.id.next);


        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(12);

        viewPager.setAdapter(new SliderAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Toast.makeText(PackagesDetails_Edit.this, "" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PackagesDetails_Edit.this, ValueEditServices_Edit.class);
                startActivity(intent);
            }
        });
    }
}