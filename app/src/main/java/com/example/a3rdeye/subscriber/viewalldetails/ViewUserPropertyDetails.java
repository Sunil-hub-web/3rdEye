package com.example.a3rdeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewUserPropertyDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    UserPropertyAdapter userPropertyAdapter;
    ArrayList<UserProperty_ModelClass> userProperties;

    String url = "https://www.rentopool.com/Thirdeye/api/auth/getpropertybyuser";
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_user_property_details);

        recyclerView = findViewById (R.id.recycler);

        id = SharedPrefManager.getInstance (ViewUserPropertyDetails.this).getUser ().getId ();


        userProperties = new ArrayList<> (  );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (ViewUserPropertyDetails.this);
        recyclerView.setLayoutManager (linearLayoutManager);

        ProgressDialog dialog = new ProgressDialog (ViewUserPropertyDetails.this);
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

                    for(int i = 0;i<array.length ();i++){

                        JSONObject object1 = array.getJSONObject (i);

                        UserProperty_ModelClass userProperty = new UserProperty_ModelClass (
                                object1.getString ("user_id"),
                                object1.getString ("id"),
                                object1.getString ("address"),
                                object1.getString ("co_ordinates"));

                        userProperties.add (userProperty);

                    }
                    Log.d ("property",userProperties.toString () );

                    userPropertyAdapter = new UserPropertyAdapter (ViewUserPropertyDetails.this,userProperties);
                    recyclerView.setAdapter (userPropertyAdapter);

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }


            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (ViewUserPropertyDetails.this, "Error"+error, Toast.LENGTH_SHORT).show ( );

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("user_id",id);
                return params                                                                           ;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (ViewUserPropertyDetails.this);
        requestQueue.add (request);


    }
}