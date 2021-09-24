package com.example.a3rdeye.subscriber.paymentgetway;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.AreaDetails;
import com.example.a3rdeye.subscriber.signin.MobileNo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardDetails extends Fragment {

    EditText edit_Month, edit_Year, edit_CardNumber, edit_OwnerName, edit_CvvNumber;
    Button btn_Payment;
    String fnamePattern = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
    String CardNumberPattern = "^[0-9]{12}$";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.carddetails, container, false);

        btn_Payment = view.findViewById(R.id.payment);
        edit_CardNumber = view.findViewById(R.id.cardnumber);
        edit_CvvNumber = view.findViewById(R.id.cvv);
        edit_OwnerName = view.findViewById(R.id.cardownername);
        edit_Month = view.findViewById(R.id.month);
        edit_Year = view.findViewById(R.id.year);

        btn_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Pattern pattern = Pattern.compile(fnamePattern);
                Matcher matcher = pattern.matcher(edit_OwnerName.getText().toString().trim());
                boolean valid = matcher.matches();

                Pattern pattern1 = Pattern.compile(CardNumberPattern);
                Matcher matcher1 = pattern1.matcher(edit_CardNumber.getText().toString().trim());
                boolean valid1 = matcher1.matches();

                if (TextUtils.isEmpty(edit_OwnerName.getText())) {
                    edit_OwnerName.setError("Owner Nmae is not empty");
                } else if (TextUtils.isEmpty(edit_CardNumber.getText())) {
                    edit_CardNumber.setError("card Number is not empty");
                } else if (TextUtils.isEmpty(edit_Month.getText())) {
                    edit_Month.setError("Month number is not empty");
                } else if (TextUtils.isEmpty(edit_Year.getText())) {
                    edit_Year.setError("Year number is not empty");
                } else if (TextUtils.isEmpty(edit_CvvNumber.getText())) {
                    edit_CvvNumber.setError("cvv number is not empty");
                } else if (!valid) {
                    edit_OwnerName.setError("Enter The Valide Name");
                } else if (!valid1) {
                    edit_CardNumber.setError("Enter The Valide Card Number");
                } else {
                    Toast.makeText(getActivity(), "Validation Complite", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;

    }
}
