package com.example.a3rdeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.a3rdeye.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AreaDetails extends AppCompatActivity {

    ListView listView;
    Button btn_submit;
    ArrayList<String> categories;
    TextView text_select;
    EditText text_city, text_pincode;

    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;

    String url = "https://rentopool.com/Thirdeye/api/auth/checkpincode";
    String url1 = "https://rentopool.com/Thirdeye/api/auth/getcategory";

    String pin_code;
    ProgressDialog dialog;

    //RecyclerView.LayoutManager manager;
    //RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_details);

        btn_submit = findViewById(R.id.submit);

       /* btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AreaDetails.this, ContactDetails.class);
                startActivity(intent);
            }
        });*/

        //initializing awesomevalidation object

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        btn_submit = findViewById(R.id.submit);
        listView = findViewById(R.id.listView);
        text_select = findViewById(R.id.select);
        text_city = findViewById(R.id.city);
        text_pincode = findViewById(R.id.pincode);

        //text_select.setFocusable(false);

        categories = new ArrayList<>();

        //text_select.setText("house");

        //listView.setHasFixedSize(true);
        //listView.setLayoutManager(new LinearLayoutManager(AreaDetails.this));


        listView.setVisibility(View.INVISIBLE);

        //adding validation to edittexts
        awesomeValidation.addValidation(AreaDetails.this, R.id.city, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.cityerror);
        awesomeValidation.addValidation(AreaDetails.this, R.id.pincode, "^[0-9]{6}$", R.string.pincodeerror);

        recyclerViewData();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pin_code = text_pincode.getText().toString().trim();
                Toast.makeText(AreaDetails.this, "" + pin_code, Toast.LENGTH_SHORT).show();

                if (awesomeValidation.validate()) {

                    if (TextUtils.isEmpty(text_select.getText())) {
                        text_select.setError("category is not empty");

                    } else {

                        dialog = new ProgressDialog(AreaDetails.this);
                        dialog.setMessage("Uploading....");
                        dialog.setCancelable(false);
                        dialog.show();

                        String city = text_city.getText().toString().trim();
                        String pincode = text_pincode.getText().toString().trim();
                        String catagory = text_select.getText().toString().trim();

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                dialog.dismiss();

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String Status = jsonObject.getString("pincodeCount");

                                    if (Status.equals("1")) {

                                        //Toast.makeText(AreaDetails.this, "Pin Code Is Available", Toast.LENGTH_SHORT).show();
                                        String message = "successfully";
                                        Intent intent = new Intent(AreaDetails.this, ContactDetails.class);
                                        intent.putExtra("message",message);
                                        intent.putExtra("city",city);
                                        intent.putExtra("pincode",pincode);
                                        intent.putExtra("catagory",catagory);
                                        startActivity(intent);

                                    } else {

                                        String message = "unsuccessfully";
                                        //Toast.makeText(AreaDetails.this, "Pincode is Not Valid", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AreaDetails.this, ContactDetails.class);
                                        intent.putExtra("message",message);
                                        intent.putExtra("city",city);
                                        intent.putExtra("pincode",pincode);
                                        intent.putExtra("catagory",catagory);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                dialog.dismiss();

                                Toast.makeText(AreaDetails.this, "Error : " + error, Toast.LENGTH_SHORT).show();

                            }
                        }
                        ) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                Map<String, String> params = new HashMap<>();

                                params.put("pincode", pin_code);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(AreaDetails.this);
                        requestQueue.add(request);

                    }
                } else {
                    Toast.makeText(AreaDetails.this, "Form Validated UnSuccessfully", Toast.LENGTH_LONG).show();

                }

            }
        });

        text_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listView.getVisibility() == View.VISIBLE) {

                    listView.setVisibility(View.INVISIBLE);

                } else {
                    listView.setVisibility(View.VISIBLE);
                }
            }
        });

        text_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listView.setVisibility(View.INVISIBLE);
            }
        });

        text_pincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listView.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void recyclerViewData() {

        dialog = new ProgressDialog(AreaDetails.this);
        dialog.setMessage("Uploading....");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        //Model model = new Model(
                                //object.getString("category_name"));

                        String model = object.getString("category_name");

                        categories.add(model);

                    }

                    //mAdapter = new RecyclerAdapter(AreaDetails.this, categories);
                    //listView.setAdapter(mAdapter);

                    ArrayAdapter adapter = new ArrayAdapter(AreaDetails.this, R.layout.list_text, categories);
                    listView.setAdapter (adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                Toast.makeText(AreaDetails.this, "Error" + error, Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(AreaDetails.this);
        requestQueue.add(request);

        listView.setOnItemClickListener (new AdapterView.OnItemClickListener ( ) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                text_select.setText (categories.get(position));

                listView.setVisibility(View.INVISIBLE);

            }
        });

    }
}