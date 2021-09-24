package com.example.a3rdeye.subscriber.viewalldetails;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;
import com.example.a3rdeye.SharedPrefManager;
import com.example.a3rdeye.subscriber.packagedetails.Package_ModelClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PackagesDetails extends AppCompatActivity {

    Button btn_Edit;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/getpackagebyuser";
    ArrayList<PackageDetails_ModelClass>list_package;
    ArrayList<Package_ModelClass>list_vas;
    RecyclerView recyclerView;
    PackagesAdapter packageAdapter;
    ValueAddsServicesAdapter valueAddsServicesAdapter;
    String id;
    Package_ModelClass pckg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_packages);

        recyclerView = findViewById (R.id.recyclerV);

        id = SharedPrefManager.getInstance (PackagesDetails.this).getUser ().getId ();

       // btn_Edit = findViewById(R.id.edit);
        list_package = new ArrayList<> (  );
        list_vas = new ArrayList<> (  );
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager (PackagesDetails.this));

        GetPackageDetails (id);

//        btn_Edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(PackagesDetails.this, PackagesDetails_Edit.class);
//                startActivity(intent);
//            }
//        });
    }

    private void GetPackageDetails(String Userid){

        ProgressDialog dialog = new ProgressDialog (PackagesDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject (response);

                    String information = object.getString ("Information");

                    JSONArray array = new JSONArray ( information );

                    for (int i=0; i<array.length ();i++){

                        JSONObject jsonObject = array.getJSONObject (i);

                        PackageDetails_ModelClass packageDetails_modelClass = new PackageDetails_ModelClass (

                                jsonObject.getString ("id"),
                                jsonObject.getString ("user_id"),
                                jsonObject.getString ("package_type"),
                                jsonObject.getString ("package_price"),
                                jsonObject.getString ("services"),
                                jsonObject.getString ("frequency"),
                                jsonObject.getString ("duration"),
                                jsonObject.getString ("value_added_services")
                        );

                        list_package.add (packageDetails_modelClass);

                        Log.d ("listpckg",list_package.toString () );

                        String value_added_services = jsonObject.getString ("value_added_services");

                        JSONArray jsonArray = new JSONArray ( value_added_services );

                        for (int j = 0 ; j<jsonArray.length () ; j++){

                            JSONObject jsonObject1 = jsonArray.getJSONObject (j);

                            //pckg = new Package_ModelClass (jsonObject1.getString ("id"),jsonObject.getString ("id"));
                            pckg = new Package_ModelClass (
                                    jsonObject1.getString ("name"),
                                    jsonObject1.getString ("desc"),
                                    jsonObject1.getString ("price"),
                                    jsonObject.getString ("id")
                            );

                            list_vas.add (pckg);
                        }

                            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(list_vas);
                            editor.putString("task list", json);
                            editor.apply();

                        Log.d ("pckg",list_vas.toString () );

                        Log.d ("listpckg",list_package.toString () );

                    }

                    packageAdapter = new PackagesAdapter (PackagesDetails.this, list_package);
                    recyclerView.setAdapter(packageAdapter);

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(PackagesDetails.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ();

                params.put("user_id", Userid);


                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(PackagesDetails.this);
        requestQueue.add(request);
    }

    public ArrayList<Package_ModelClass> getvas(){
        return list_vas;
    }
}