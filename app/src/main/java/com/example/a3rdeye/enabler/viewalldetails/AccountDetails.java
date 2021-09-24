package com.example.a3rdeye.enabler.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a3rdeye.R;
import com.example.a3rdeye.enabler.ProfilePage_Enabler;

public class AccountDetails extends AppCompatActivity {

    Button btn_Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        btn_Edit = findViewById(R.id.edit);

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountDetails.this, Accountdetails_Edit.class);
                startActivity(intent);
            }
        });
    }
}