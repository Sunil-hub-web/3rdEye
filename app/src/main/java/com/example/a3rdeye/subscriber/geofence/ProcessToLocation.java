package com.example.a3rdeye.subscriber.geofence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ProcessToLocation extends AppCompatActivity {

    CheckBox checkbox;
    TextView text_DateTime;
    Button btn_next, btn_skip;
    String check,id,date,time,userid;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/storeenabler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procress_to_location);

        text_DateTime = findViewById(R.id.datetime);
        checkbox = findViewById(R.id.check);
        btn_next = findViewById(R.id.next);
        btn_skip = findViewById(R.id.skip);

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        date = df.format(Calendar.getInstance().getTime());

        DateFormat df1 = new SimpleDateFormat("HH:mm");
        time = df1.format(Calendar.getInstance().getTime());

        //id = getIntent().getStringExtra("id");

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Intent intent1 = getIntent();
        userid = intent1.getStringExtra("userid");


        text_DateTime.setText(date+","+time);

        btn_next.setEnabled(false);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (checkbox.isChecked()) {

                    check = "Enabler Person Needed";

                    btn_next.setBackgroundResource(R.drawable.button_shap);
                    btn_next.setTextColor(Color.BLACK);
                    btn_skip.setBackgroundResource(R.drawable.button_back1);

                    btn_skip.setEnabled(false);
                    btn_next.setEnabled(true);

                } else {

                    btn_next.setBackgroundResource(R.drawable.button_back1);
                    btn_skip.setBackgroundResource(R.drawable.button_back);
                    btn_next.setTextColor(Color.WHITE);

                    btn_skip.setEnabled(true);
                    btn_next.setEnabled(false);
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userid != null){

                   storeEnabler ( userid );

                }else{

                    storeEnabler ();
                }
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userid != null){

                    Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                    intent.putExtra("id",userid);
                    startActivity(intent);

                }else{

                    Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }



            }
        });


    }

    public void storeEnabler(){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject object = new JSONObject(response);
                    String Status = object.getString("message");

                    if(Status.equals("Enabler stored")){

                        if(userid != null){

                            Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                            intent.putExtra("userid",userid);
                            startActivity(intent);

                        }else{

                            Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProcessToLocation.this, "Error"+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("subscriber_id",id);
                params.put("day",check);
                params.put("date",date);
                params.put("time",time);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProcessToLocation.this);
        requestQueue.add(request);

    }

    public void storeEnabler(String userid){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject object = new JSONObject(response);
                    String Status = object.getString("message");

                    if(Status.equals("Enabler stored")){

                        if(userid != null){

                            Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                            intent.putExtra("userid",userid);
                            startActivity(intent);

                        }else{

                            Intent intent = new Intent(ProcessToLocation.this, SerachAreaDetails.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProcessToLocation.this, "Error"+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();

                params.put("subscriber_id",userid);
                params.put("day",check);
                params.put("date",date);
                params.put("time",time);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProcessToLocation.this);
        requestQueue.add(request);

    }
}