package com.example.a3rdeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;
import com.example.a3rdeye.subscriber.geofence.ProcessToLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPropertyDetails extends AppCompatActivity {

    String url = "https://www.rentopool.com/Thirdeye/api/auth/getpropertybyuser";

    RecyclerView recyclerView;
    ArrayList<Property_ModelClass> propertyList;
    RecyclerView.LayoutManager manager;
    PropertyAdapter propertyAdapter;
    String id;
    Button btn_AddProperty;

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_property_details);

        id = SharedPrefManager.getInstance (ViewPropertyDetails.this).getUser ().getId ();

        btn_AddProperty = findViewById (R.id.addproperty);

        Intent intent = getIntent();
        message = intent.getStringExtra("message");


        btn_AddProperty.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( ViewPropertyDetails.this, ProcessToLocation.class);
                intent.putExtra ("userid",id);
                startActivity (intent);
            }
        });

        recyclerView = findViewById (R.id.recycler);

        propertyList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager (ViewPropertyDetails.this));

        if(message != null){
            btn_AddProperty.setVisibility (View.GONE);
            getPropertyDetails();
        }else{
            getPropertyDetails();
        }

    }

    public void getPropertyDetails(){

        ProgressDialog dialog = new ProgressDialog (ViewPropertyDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {
                dialog.dismiss ();
                try {
                    JSONObject object = new JSONObject ( response );

                    String information = object.getString ("Information");
                    JSONArray array = new JSONArray ( information );

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object1 = array.getJSONObject(i);

                        Property_ModelClass property = new Property_ModelClass (
                                object1.getString ("user_id"),
                                object1.getString ("id"),
                                object1.getString ("latitude"),
                                object1.getString ("longitude"),
                                object1.getString ("country_name"),
                                object1.getString ("locality"),
                                object1.getString ("postal_code"),
                                object1.getString ("address")
                        );

                        propertyList.add (property);

                    }
                    propertyAdapter = new PropertyAdapter (ViewPropertyDetails.this,propertyList);
                    recyclerView.setAdapter (propertyAdapter);


                } catch (JSONException e) {
                    e.printStackTrace ( );
                }


            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                Toast.makeText (ViewPropertyDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("user_id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (ViewPropertyDetails.this);
        requestQueue.add (request);
    }
}