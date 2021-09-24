 package com.example.a3rdeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.paymentgetway.PaymentGateway;
import com.example.a3rdeye.subscriber.signin.MobileNo;

public class DisclaimerForm extends AppCompatActivity {

    Button btn_Accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer_form);

        btn_Accept = findViewById(R.id.accept);

        btn_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DisclaimerForm.this, MobileNo.class);
                startActivity(intent);
            }
        });
    }
}