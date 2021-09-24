package com.example.a3rdeye.subscriber.geofence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.HomePage_Subscriber;
import com.example.a3rdeye.R;
import com.example.a3rdeye.declaration.Declaration_Page2_Subscriber;
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;
import com.example.a3rdeye.subscriber.viewalldetails.PackagesDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Geofence extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView imageView1, imageView2;
    Context context;
    Button btn_GetCoordinates, btn_SaveLocation, btn_DrawPolyline;

    private static final String TAG = "MapsActivity";
    private static final int REQUEST_LOCATION = 1;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    LocationManager locationManager;
    private double latitude;
    private double longitude;
    String latitude1, longitude1, location1;
    Polyline polyline;
    TextView tv_Latitude, tv_Longitude;
    int red = 0, green = 0, blue = 0;
    List<LatLng> latLngList = new ArrayList<>();
    ArrayList<String> latLngList1 = new ArrayList<>();
    LatLng location, latLng;

    String str_Latitude, str_Longitude, str_Countryname, str_Locality, str_PostalCode, str_Address, propertyid, json;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/updatepropertydetails";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.geofence_map);
        mapFragment.getMapAsync(this);

        imageView1 = findViewById(R.id.imageButton);
        imageView2 = findViewById(R.id.imageButton1);
        btn_SaveLocation = findViewById(R.id.savelocation);
        btn_DrawPolyline = findViewById(R.id.drawpolyline);
        btn_GetCoordinates = findViewById(R.id.getcoordinate);
        tv_Latitude = findViewById(R.id.latitude);
        tv_Longitude = findViewById(R.id.longitude);

        // btn_SaveLocation.setEnabled(false);
        //btn_DrawPolyline.setEnabled(false);

        Intent intent = getIntent ();

        str_Latitude = intent.getStringExtra ("latitude");
        str_Longitude = intent.getStringExtra ("longitude");
        str_Countryname = intent.getStringExtra ("country");
        str_Locality = intent.getStringExtra ("locality");
        str_PostalCode = intent.getStringExtra ("postalcode");
        str_Address = intent.getStringExtra ("address");
        propertyid = intent.getStringExtra ("id");


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Geofence.this, HomePage_Subscriber.class);
                startActivity(intent);
            }
        });


        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(Geofence.this, imageView2);

                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

                popup.show();//showing popup menu


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:
                                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                break;
                            case R.id.two:
                                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                break;
                        }
                        return false;
                    }
                });

            }
        });

        btn_GetCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btn_SaveLocation.setEnabled(false);
                //btn_DrawPolyline.setEnabled(true);


                btn_DrawPolyline.setTextColor(Color.BLACK);
                btn_DrawPolyline.setBackgroundResource(R.drawable.button_shap);

                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    showSettingsAlert();

                    Toast.makeText(Geofence.this, "Enable Your Location", Toast.LENGTH_SHORT).show();

                } else {

                    //GPS is already On then
                    getLocation();

                    location = new LatLng(latitude, longitude);

                    MarkerOptions markerOptions = new MarkerOptions().position(location).title("you are here");
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));

                    location1 = "" + (latitude) + " " + (longitude);
                    latLngList.add(location);
                    latLngList1.add(location1);
                    Toast.makeText(Geofence.this, "latLngList : " + latLngList, Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_DrawPolyline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btn_SaveLocation.setEnabled(true);
                //btn_DrawPolyline.setEnabled(true);
                // btn_GetCoordinates.setEnabled(false);

                btn_SaveLocation.setTextColor(Color.BLACK);
                btn_SaveLocation.setBackgroundResource(R.drawable.button_shap);
                btn_DrawPolyline.setBackgroundResource(R.drawable.button_back1);
                btn_GetCoordinates.setBackgroundResource(R.drawable.button_back1);

                if (polyline != null) polyline.remove();
                PolylineOptions polylineOptions = new PolylineOptions().addAll(latLngList).clickable(true);
                polyline = mMap.addPolyline(polylineOptions);
                polyline.setColor(Color.rgb(red, green, blue));
            }
        });

        btn_SaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                json = gson.toJson(latLngList);
                Log.d("extra_js",json);

                ProgressDialog dialog = new ProgressDialog (Geofence.this);
                dialog.setMessage ("Data Update...");
                dialog.show ();

                StringRequest stringRequest = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss ();
                        try {
                            JSONObject jsonObject = new JSONObject ( response );

                            String status = jsonObject.getString ("0");
                            String status1 = jsonObject.getString ("1");

                            if(status.equals("success")){

                                if(status1.equals ("You have successfully updated the details.")){

                                    Toast.makeText (Geofence.this, "Your Data Update Successfully", Toast.LENGTH_SHORT).show ( );

                                    btn_SaveLocation.setBackgroundResource(R.drawable.button_back1);
                                    btn_DrawPolyline.setBackgroundResource(R.drawable.button_back1);
                                    btn_GetCoordinates.setBackgroundResource(R.drawable.button_back1);

                                    Intent intent1 = new Intent ( Geofence.this,HomePage_Subscriber.class );
                                    startActivity (intent1);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }

                    }
                }, new Response.ErrorListener ( ) {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         dialog.dismiss ();
                        Toast.makeText (Geofence.this, ""+error, Toast.LENGTH_SHORT).show ( );

                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params = new HashMap<> (  );

                        params.put ("id",propertyid);
                        params.put("latitude",str_Latitude);
                        params.put("longitude",str_Longitude);
                        params.put("country_name",str_Countryname);
                        params.put("locality",str_Locality);
                        params.put("postal_code",str_PostalCode);
                        params.put("address",str_Address);
                        params.put("co_ordinates",json);

                        return params ;

                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue (Geofence.this);
                requestQueue.add (stringRequest);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(20.3490, 85.8077);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));

        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        enableUserLocation();

    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(Geofence.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Geofence.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps != null) {

                latitude = LocationGps.getLatitude();
                longitude = LocationGps.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

                // showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude1+"\n"+"Longitude= "+longitude1);
                tv_Latitude.setText(latitude1);
                tv_Longitude.setText(longitude1);

            } else if (LocationNetwork != null) {
                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

                // showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                tv_Latitude.setText(latitude1);
                tv_Longitude.setText(longitude1);

            } else if (LocationPassive != null) {

                latitude = LocationNetwork.getLatitude();
                longitude = LocationNetwork.getLongitude();

                latitude1 = String.valueOf(latitude);
                longitude1 = String.valueOf(longitude);

                //showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                tv_Latitude.setText(latitude1);
                tv_Longitude.setText(longitude1);

            } else {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
            //Thats All Run Your App
        }

    }

    // All Permission For Access Location

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Geofence.this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..

            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }
    }

}