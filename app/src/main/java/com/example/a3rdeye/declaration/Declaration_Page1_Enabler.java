package com.example.a3rdeye.declaration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.a3rdeye.R;

public class Declaration_Page1_Enabler extends AppCompatActivity {


    CheckBox checkBox1, checkBox2;
    Button btn_yes, btn_no;
    String checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration__page1__enabler);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        btn_yes = findViewById(R.id.yes);
        btn_no = findViewById(R.id.no);

        //btn_yes.setEnabled(false);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBox1.isChecked()) {

                    if (checkBox2.isChecked() && checkBox1.isChecked()) {

                        btn_yes.setBackgroundResource(R.drawable.button_shap);
                        btn_yes.setTextColor(Color.BLACK);
                        btn_no.setBackgroundResource(R.drawable.button_back);

                    }

                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (checkBox2.isChecked() && checkBox1.isChecked()) {

                                btn_yes.setBackgroundResource(R.drawable.button_shap);
                                btn_yes.setTextColor(Color.BLACK);
                                btn_no.setBackgroundResource(R.drawable.button_back);

                                //btn_yes.setEnabled(true);
                                //btn_no.setEnabled(false);
                            } else {
                                btn_yes.setBackgroundResource(R.drawable.button_back1);
                                btn_no.setBackgroundResource(R.drawable.button_back);
                                btn_yes.setTextColor(Color.WHITE);
                                //btn_yes.setEnabled(false);
                                //btn_no.setEnabled(true);
                            }

                        }
                    });

                } else {
                    Toast.makeText(Declaration_Page1_Enabler.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

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

                    //btn_yes.setBackgroundColor(getResources().getColor(R.color.button1));
                    //btn_no.setBackgroundColor(getResources().getColor(R.color.button2));
                    //btn_yes.setEnabled(true);

                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                    btn_yes.setTextColor(Color.BLACK);
                    btn_no.setBackgroundResource(R.drawable.button_back);


                } else {
                    Toast.makeText(Declaration_Page1_Enabler.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked() && checkBox2.isChecked()) {

                    Intent intent = new Intent(Declaration_Page1_Enabler.this, Declaration_Page2_Enabler.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Declaration_Page1_Enabler.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}