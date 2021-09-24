package com.example.a3rdeye.enabler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.MainActivity;
import com.example.a3rdeye.R;

public class AccountDetails_Enabler extends AppCompatActivity {

    EditText edit_BankName, edit_IFCCode, edit_AccountHolderName, edit_AcountNumber, edit_ConformAcountNumber, edit_MobileNo;
    Button btn_submit;
    boolean isEmailValid, isPasswordValid, isPasswordVisible;

    private AwesomeValidation awesomeValidation;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details__enabler);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_BankName = findViewById(R.id.bankname);
        edit_AccountHolderName = findViewById(R.id.accountholdername);
        edit_AcountNumber = findViewById(R.id.accountno);
        edit_ConformAcountNumber = findViewById(R.id.conformaccountno);
        edit_IFCCode = findViewById(R.id.ifccode);
        edit_MobileNo = findViewById(R.id.mobileno);

        btn_submit = findViewById(R.id.submit);

        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.bankname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.bankname);
        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.accountholdername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.holdername);
        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.accountno, "^[0-9]{1,20}$", R.string.accountno);
        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.conformaccountno, "^[0-9]{0,20}$", R.string.confaccNumber);
        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.ifccode, "^[A-za-z]{4}[A-za-z0-9]{1,10}$", R.string.ifccode);
        awesomeValidation.addValidation(AccountDetails_Enabler.this, R.id.mobileno, "^[0-9]{1,15}$", R.string.Mobile);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    if (!edit_AcountNumber.getText().toString().equals(edit_ConformAcountNumber.getText().toString())) {
                        edit_ConformAcountNumber.setError("AccountNo Not matched");
                        edit_ConformAcountNumber.requestFocus();
                    } else {
                        Toast.makeText(AccountDetails_Enabler.this, "Account Number Is Correct", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(AccountDetails_Enabler.this, "Your Validation Complite", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AccountDetails_Enabler.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AccountDetails_Enabler.this, "Please fill details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}