package com.example.a3rdeye.subscriber.paymentgetway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a3rdeye.R;

public class PaymentGateway extends AppCompatActivity {

    Button btn_CardDetails,btn_NetBanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        btn_CardDetails = findViewById(R.id.carddetails);
        btn_NetBanking = findViewById(R.id.netbanking);

        btn_CardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                CardDetails card = new CardDetails();
                ft.replace(R.id.frame,card);
                ft.commit();
            }
        });





    }
}