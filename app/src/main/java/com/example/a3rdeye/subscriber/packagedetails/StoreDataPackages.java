package com.example.a3rdeye.subscriber.packagedetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class StoreDataPackages {

    Context context;

    public void StorePackages(){

        SharedPreferences sp = context.getSharedPreferences("details", context.MODE_PRIVATE);

        String name1 = sp.getString("name", null);
        String service = sp.getString("services", null);
        String frequency = sp.getString("frequency", null);
        String duration = sp.getString("duration", null);
        String price1 = sp.getString("price", null);
        String values = sp.getString ("data",null);

        Toast.makeText (context,""+values,Toast.LENGTH_LONG).show ();
    }
}
