package com.example.a3rdeye.declaration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.a3rdeye.R;

public class Declaration_Page1_Subscriber extends AppCompatActivity {

    CheckBox checkBox1, checkBox2;
    Button btn_yes, btn_no;
    String checkbox1, checkbox2, checkBox;
    Uri image_uri, image_uri1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration__page1);


        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        btn_yes = findViewById(R.id.yes);
        btn_no = findViewById(R.id.no);

        image_uri = Uri.parse(getIntent().getExtras().getString("imageuri"));
        image_uri1 = Uri.parse(getIntent().getExtras().getString("imageuri1"));

        Toast.makeText(this, "" + image_uri, Toast.LENGTH_SHORT).show();

        //btn_yes.setEnabled(false);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBox1.isChecked()) {

                    checkbox1 = "I have a bike,which I can use regularly";

                    Toast.makeText(Declaration_Page1_Subscriber.this, "" + checkbox1, Toast.LENGTH_SHORT).show();

                    if (checkBox2.isChecked() && checkBox1.isChecked()) {

                        btn_yes.setBackgroundResource(R.drawable.button_shap);
                        btn_yes.setTextColor(Color.BLACK);
                        btn_no.setBackgroundResource(R.drawable.button_back);

                    }

                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            checkbox2 = "Plot has clear Approach";
                            Toast.makeText(Declaration_Page1_Subscriber.this, "" + checkbox2, Toast.LENGTH_SHORT).show();

                            if (checkBox2.isChecked() && checkBox1.isChecked()) {

                                btn_yes.setBackgroundResource(R.drawable.button_shap);
                                btn_yes.setTextColor(Color.BLACK);
                                btn_no.setBackgroundResource(R.drawable.button_back);

                            } else {
                                btn_yes.setBackgroundResource(R.drawable.button_back1);
                                btn_no.setBackgroundResource(R.drawable.button_back);
                                btn_yes.setTextColor(Color.WHITE);

                            }

                        }
                    });

                } else {
                    Toast.makeText(Declaration_Page1_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                    btn_yes.setBackgroundResource(R.drawable.button_back1);
                    btn_no.setBackgroundResource(R.drawable.button_back);
                    btn_yes.setTextColor(Color.WHITE);
                }

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked() && checkBox2.isChecked()) {

                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                    btn_yes.setTextColor(Color.BLACK);
                    btn_no.setBackgroundResource(R.drawable.button_back);


                } else {
                    Toast.makeText(Declaration_Page1_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBox = checkbox1 + "," + checkbox2;

                Toast.makeText(Declaration_Page1_Subscriber.this, "" + checkBox, Toast.LENGTH_LONG).show();

                if (checkBox1.isChecked() && checkBox2.isChecked()) {

                    Intent intent = new Intent(Declaration_Page1_Subscriber.this, Declaration_Page2_Subscriber.class);
                    intent.putExtra("phonenumber", checkBox);
                    intent.putExtra("imageuri", image_uri.toString());
                    intent.putExtra("imageuri1", image_uri1.toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(Declaration_Page1_Subscriber.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}