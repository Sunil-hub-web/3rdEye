package com.example.a3rdeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.a3rdeye.subscriber.viewalldetails.PackagesDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification_Subscriber extends AppCompatActivity {

    String url = "https://rentopool.com/Thirdeye/api/auth/getnotification?user_id=";
    String id;
    ArrayList<Notification_ModelClass> notification = new ArrayList<> (  );

    LinearLayoutManager linearLayoutManager;
    NotificationAdapter notificationAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification_subscriber);


        recyclerView = findViewById (R.id.recyclernotification);

        linearLayoutManager = new LinearLayoutManager (Notification_Subscriber.this);

        //id = SharedPrefManager.getInstance (PackagesDetails.this).getUser ().getId ();
        getMessage("59");

    }

    public void getMessage(String userId){

        ProgressDialog dialog = new ProgressDialog (Notification_Subscriber.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();
        dialog.setCancelable (false);

        StringRequest request = new StringRequest (Request.Method.POST, url+userId, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String information = jsonObject.getString ("Information");
                    JSONArray jsonArray = new JSONArray ( information );

                    for(int i=0;i<jsonArray.length ();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject (i);

                       Notification_ModelClass notification_modelClass = new Notification_ModelClass (
                               jsonObject1.getString ("date"),
                               jsonObject1.getString ("time"),
                               jsonObject1.getString ("notification_text")
                       );

                       notification.add (notification_modelClass);

                    }
                    notificationAdapter = new NotificationAdapter (Notification_Subscriber.this,notification);
                    recyclerView.setHasFixedSize (true);
                    recyclerView.setLayoutManager (linearLayoutManager);
                    recyclerView.setAdapter (notificationAdapter);

                    Log.d ("notifaction",notification.toString () );


                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (Notification_Subscriber.this, "Error : "+error, Toast.LENGTH_SHORT).show ( );

            }
        });/*{

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("user_id",userId);
                return super.getParams ( );
            }
        };*/

        RequestQueue requestQueue = Volley.newRequestQueue (Notification_Subscriber.this);
        requestQueue.add (request);
    }
}