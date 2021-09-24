package com.example.a3rdeye.subscriber.packagedetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;
import com.example.a3rdeye.subscriber.DisclaimerForm;
import com.example.a3rdeye.subscriber.viewalldetails.PackagesDetails;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ValueAddes_Services extends AppCompatActivity {

    Button btn_Skip,btn_Save;
    RecyclerView recyclerView;
    ValueSevicesAdapter valueSevicesAdapter;
    LinearLayoutManager linearLayoutManager;

    ArrayList<ValueAddedServices_ModelClass> data = new ArrayList<>();
    ArrayList<Package_ModelClass> packages;

    String url = "https://www.rentopool.com/Thirdeye/api/auth/storepackagebyuser";
    String url1 = "https://rentopool.com/Thirdeye/api/auth/getservice";

    String service, frequency, duration, price, name,id,json,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valueadded_serviese);

        btn_Skip = findViewById(R.id.skip);
        btn_Save = findViewById(R.id.save);
        recyclerView = findViewById (R.id.recyclerValue);

        Intent intent1 = getIntent();
        userid = intent1.getStringExtra("userid");

        if(userid != null){

            id = SharedPrefManager.getInstance (ValueAddes_Services.this).getUser ().getId ();
        }
        else{

            Intent intent = getIntent();
            id = intent.getStringExtra("id");
        }

        linearLayoutManager = new LinearLayoutManager (ValueAddes_Services.this);
        valueSevicesAdapter = new ValueSevicesAdapter (ValueAddes_Services.this,data);
        packages = valueSevicesAdapter.getValuAddedServices ();


        getUserData();

        SharedPreferences sp = getSharedPreferences("details", MODE_PRIVATE);
        String s1 = sp.getString("type", null);

        name = sp.getString("name", null);
        service = sp.getString("services", null);
        frequency = sp.getString("frequency", null);
        duration = sp.getString("duration", null);
        price = sp.getString("price", null);


       /* btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ValueAddes_Services.this, DisclaimerForm.class);
                startActivity(intent);

            }
        });*/

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Gson gson = new Gson();
                 json = gson.toJson(packages);
                 Log.d("extra_js",json);


                ProgressDialog dialog = new ProgressDialog(ValueAddes_Services.this);
                dialog.setMessage("Store Data");
                dialog.setCancelable(false);
                dialog.show();

                final Map<String, String> params = new HashMap();

                        params.put("user_id",id);
                        params.put("package_type",name);
                        params.put("package_price",price);
                        params.put("services",service);
                        params.put("frequency",frequency);
                        params.put("duration",duration);
                        params.put("value_added_services",json);

                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        dialog.dismiss();

                        try {

                            String Status = response.getString("message");

                            if(Status.equals("Property detail stored")){

                                if(userid != null){

                                    json="";
                                    Toast.makeText(ValueAddes_Services.this, "Property detail stored", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ValueAddes_Services.this, HomePage_Subscriber.class);
                                    startActivity(intent);

                                }else{

                                    json="";
                                    Toast.makeText(ValueAddes_Services.this, "Property detail stored", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ValueAddes_Services.this, DisclaimerForm.class);
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
                        error.printStackTrace();
                        dialog.dismiss();

                        Toast.makeText(ValueAddes_Services.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                });


                jsonRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(ValueAddes_Services.this).add(jsonRequest);

            }
        });

        btn_Skip.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                ProgressDialog dialog = new ProgressDialog(ValueAddes_Services.this);
                dialog.setMessage("Store Data");
                dialog.setCancelable(false);
                dialog.show();


                final Map<String, String> params = new HashMap();

                params.put("user_id",id);
                params.put("package_type",name);
                params.put("package_price",price);
                params.put("services",service);
                params.put("frequency",frequency);
                params.put("duration",duration);
                params.put("value_added_services","");

                JSONObject parameters = new JSONObject(params);

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        dialog.dismiss();

                        try {

                            String Status = response.getString("message");

                            if(Status.equals("Property detail stored")){

                                if(userid != null){

                                    Toast.makeText(ValueAddes_Services.this, "Property detail stored", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ValueAddes_Services.this, HomePage_Subscriber.class);
                                    startActivity(intent);

                                }else{

                                    Toast.makeText(ValueAddes_Services.this, "Property detail stored", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ValueAddes_Services.this, DisclaimerForm.class);
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
                        error.printStackTrace();
                        dialog.dismiss();

                        Toast.makeText(ValueAddes_Services.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                    }
                });


                jsonRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                Volley.newRequestQueue(ValueAddes_Services.this).add(jsonRequest);

            }
        });
    }

    public void getUserData(){

        ProgressDialog dialog = new ProgressDialog (ValueAddes_Services.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.GET, url1, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONArray array = new JSONArray ( response );

                    for (int i = 0;i<array.length ();i++){

                        JSONObject jsonObject = array.getJSONObject (i);

                        ValueAddedServices_ModelClass value = new ValueAddedServices_ModelClass (
                                jsonObject.getString ("id"),
                                jsonObject.getString ("name"),
                                jsonObject.getString ("price"),
                                jsonObject.getString ("description")
                        );
                            data.add (value);
                    }


                    Log.d ("mssage",data.toString ());


                    recyclerView.setLayoutManager (linearLayoutManager);
                    recyclerView.setAdapter (valueSevicesAdapter);

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (ValueAddes_Services.this, "Error"+error, Toast.LENGTH_SHORT).show ( );

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue (ValueAddes_Services.this);
        requestQueue.add (request);

    }

    @Override
    protected void onRestart() {
        super.onRestart ( );
        packages.clear ();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ( );
        packages.clear ();
        Intent intent = new Intent ( ValueAddes_Services.this, Packages_Details.class );
        startActivity (intent);
    }
}