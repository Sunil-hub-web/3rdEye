package com.example.a3rdeye.subscriber.geofence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
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
import com.example.a3rdeye.subscriber.packagedetails.Packages_Details;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SerachAreaDetails extends AppCompatActivity implements OnMapReadyCallback {

    Button btn_select;
    TextView text_CurrentLocation;

    TextView textView1, textView2, textView3, textView4, textView5, textView6, text_MapView;
    FusedLocationProviderClient fusedLocationProviderClient;

    String str_Latitude, str_Longitude, str_Countryname, str_Locality, str_PostalCode, str_Address,id,userid;

    private GoogleMap mMap;
    Bitmap smallMarker;

    private Geocoder geocoder;

    SearchView searchView;

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;

    String message;
    ProgressDialog dialog;

    String url = "https://rentopool.com/Thirdeye/api/auth/insertpropertydetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_area_details);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Intent intent1 = getIntent();
        userid = intent1.getStringExtra("userid");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.geofence_map);
        mapFragment.getMapAsync(this);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);

        btn_select = findViewById(R.id.verifay);
        text_CurrentLocation = findViewById(R.id.currentLocation);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView6 = findViewById(R.id.text_view6);
        text_MapView = findViewById(R.id.mapView);

        searchView = findViewById(R.id.sv_location);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //text_MapView.setText("SATELLITE");

        text_MapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = text_MapView.getText().toString().trim();

                if (value.equals("TERRAIN")) {

                    text_MapView.setText("HYBRID");
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else {

                    text_MapView.setText("TERRAIN");
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }

            }
        });


        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    str_Latitude = textView1.getText().toString().trim();
                    str_Longitude = textView2.getText().toString().trim();
                    str_Countryname = textView3.getText().toString().trim();
                    str_Locality = textView4.getText().toString().trim();
                    str_PostalCode = textView5.getText().toString().trim();
                    str_Address = textView6.getText().toString().trim();

                dialog = new ProgressDialog(SerachAreaDetails.this);
                dialog.setMessage("Store Data");
                dialog.setCancelable(false);
                dialog.show();

                if(userid != null){

                    storeUserProperty ( userid );

                }else{

                    storeUserProperty ();
                }

                //Toast.makeText(Geofence.this, ""+str_Latitude+""+str_Longitude+""+str_Countryname+""+str_Locality+""+str_PostalCode+""+str_Address+""+latLngList1, Toast.LENGTH_SHORT).show();

            }
        });

        text_CurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check gps is enable or not
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();
                } else {
                    //GPS is already On then
                    getLocation();
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();
                } else {
                    //GPS is already On then
                    String location = searchView.getQuery().toString();

                    List<Address> addressList = null;

                    if (location != null || !location.equals("")) {

                        Geocoder geocoder = new Geocoder(SerachAreaDetails.this);

                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                        //view_address.setText(address.getAddressLine(0));

                        //set Latitude On Text View
                        textView1.setText(Html.fromHtml("<font color='#606060'><b>Latitude :</b><br></font>" + address.getLatitude()));

                        //set Longitude On Text View
                        textView2.setText(Html.fromHtml("<font color='#606060'><b>Longitude :</b><br></font>" + address.getLongitude()));

                        textView3.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + address.getCountryName()));

                        //set Locality On Text View
                        textView4.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + address.getLocality()));

                        //set Locality On Text View
                        textView5.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + address.getPostalCode()));

                        //set address On Text View
                        textView6.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + address.getAddressLine(0)));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        enableUserLocation();

        getTopLocation(googleMap);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(20.3490, 85.8077);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Dlf").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera (CameraUpdateFactory.newLatLngZoom (sydney, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));

        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //Showing Current Location
        googleMap.setMyLocationEnabled(true); // false to disable

    }

    private void getTopLocation(GoogleMap googleMap) {

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                //Check gps is enable or not
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();
                } else {
                    //GPS is already On then
                    LatLng topLocation = googleMap.getCameraPosition().target;

                    geocoder = new Geocoder(SerachAreaDetails.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(topLocation.latitude, topLocation.longitude, 1);
                        if (addresses.size() > 0) {

                            Address address = addresses.get(0);
                            String streetAddress = address.getCountryName();
                            String streetAddress1 = address.getLocality();
                            String streetAddress2 = address.getPostalCode();
                            String streetAddress3 = address.getAddressLine(0);

                            textView3.setText(streetAddress);
                            textView4.setText(streetAddress1);
                            textView5.setText(streetAddress2);
                            textView6.setText(streetAddress3);

                            //set Latitude On Text View
                            textView1.setText(Html.fromHtml("<font color='#606060'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));

                            //set Longitude On Text View
                            textView2.setText(Html.fromHtml("<font color='#606060'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));

                            //set countary name On Text View
                            textView3.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + streetAddress));

                            //set Locality On Text View
                            textView4.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + streetAddress1));

                            //set Locality On Text View
                            textView5.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + streetAddress2));

                            //set address On Text View
                            textView6.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + streetAddress3));

                        /*mMap.addMarker(new MarkerOptions()
                                .position(topLocation)
                                .title(streetAddress)
                                .draggable(true)
                        );*/

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialize location
                Location location = task.getResult();

                if (location != null) {

                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(SerachAreaDetails.this, Locale.getDefault());

                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //set Latitude On Text View
                        textView1.setText(Html.fromHtml("<font color='#606060'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));

                        //set Longitude On Text View
                        textView2.setText(Html.fromHtml("<font color='#606060'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));

                        //set countary name On Text View
                        textView3.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + addresses.get(0).getCountryName()));

                        //set Locality On Text View
                        textView4.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + addresses.get(0).getLocality()));

                        //set Locality On Text View
                        textView5.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + addresses.get(0).getPostalCode()));

                        //set address On Text View
                        textView6.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

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
    }

    public void locationPermission() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void storeUserProperty(){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String Status = jsonObject.getString("message");

                    if (Status.equals("Property stored")) {

                        if(userid != null){

                            Toast.makeText(SerachAreaDetails.this, "Stored Data SuccessFully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SerachAreaDetails.this, Packages_Details.class);
                            intent.putExtra("userid",userid);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SerachAreaDetails.this, "Stored Data SuccessFully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SerachAreaDetails.this, Packages_Details.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(SerachAreaDetails.this, "Stored Data Un SuccessFully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(SerachAreaDetails.this, "Error" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

                params.put("user_id",id);
                params.put("latitude", str_Latitude);
                params.put("longitude", str_Longitude);
                params.put("country_name", str_Countryname);
                params.put("locality", str_Locality);
                params.put("postal_code", str_PostalCode);
                params.put("address", str_Address);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SerachAreaDetails.this);
        requestQueue.add(request);

    }

    public void storeUserProperty(String userid){

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String Status = jsonObject.getString("message");

                    if (Status.equals("Property stored")) {

                        if(userid != null){

                            Toast.makeText(SerachAreaDetails.this, "Stored Data SuccessFully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SerachAreaDetails.this, Packages_Details.class);
                            intent.putExtra("userid",userid);
                            startActivity(intent);

                        }
                        else{

                            Toast.makeText(SerachAreaDetails.this, "Stored Data SuccessFully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SerachAreaDetails.this, Packages_Details.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(SerachAreaDetails.this, "Stored Data Un SuccessFully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(SerachAreaDetails.this, "Error" + error, Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();

                params.put("user_id",userid);
                params.put("latitude", str_Latitude);
                params.put("longitude", str_Longitude);
                params.put("country_name", str_Countryname);
                params.put("locality", str_Locality);
                params.put("postal_code", str_PostalCode);
                params.put("address", str_Address);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SerachAreaDetails.this);
        requestQueue.add(request);

    }
}