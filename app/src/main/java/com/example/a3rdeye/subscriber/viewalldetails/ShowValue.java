package com.example.a3rdeye.subscriber.viewalldetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.packagedetails.Package_ModelClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ShowValue extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ValueAddsServicesAdapter valueAddsServicesAdapter;
    PackagesDetails packagesDetails;
    ArrayList<Package_ModelClass> value;
    ArrayList<Package_ModelClass> vas = new ArrayList<> (  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_value);

        Intent intent = getIntent ();

        String valueservices = intent.getStringExtra ("packageid");

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list", null);
            Type type = new TypeToken<ArrayList<Package_ModelClass>> () {}.getType();
            value = gson.fromJson(json, type);

            if (value == null) {
                value = new ArrayList<>();
            }

        packagesDetails=new PackagesDetails ();
       // value = packagesDetails.getvas ();
        recyclerView = findViewById (R.id.recycler);
        linearLayoutManager = new LinearLayoutManager (ShowValue.this);
        recyclerView.setLayoutManager (linearLayoutManager);
        recyclerView.setHasFixedSize (true);


        for (int i = 0 ; i < value.size () ; i++){

            if (valueservices.equals (value.get (i).packagesid)){

                Package_ModelClass packages = new Package_ModelClass ( value.get (i).name,value.get (i).desc,value.get (i).price );
                vas.add (packages);
                valueAddsServicesAdapter = new ValueAddsServicesAdapter (vas,ShowValue.this);
                recyclerView.setAdapter (valueAddsServicesAdapter);
            }
        }







    }
}