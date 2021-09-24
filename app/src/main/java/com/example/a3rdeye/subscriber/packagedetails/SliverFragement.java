package com.example.a3rdeye.subscriber.packagedetails;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.a3rdeye.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SliverFragement extends Fragment {

    Button btn_select;

    String url = "https://rentopool.com/Thirdeye/api/auth/getpackagedetail";
    TextView text_Photo, text_video, text_LiveVideoCalling, text_frequency, text_Duration, text_price,text_Name;

    String sliver="Silver";
    Context context;

    ProgressDialog dialog;
    String services,frequency,duration,price,name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sliver, container, false);

        getDataSliver();



        btn_select = view.findViewById(R.id.btnsliver);
        text_Photo = view.findViewById(R.id.photo);
        text_video = view.findViewById(R.id.video);
        text_LiveVideoCalling = view.findViewById(R.id.livevideo);
        text_frequency = view.findViewById(R.id.frequency);
        text_Duration = view.findViewById(R.id.duration);
        text_price = view.findViewById(R.id.money);
        text_Name = view.findViewById(R.id.sliver);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                services = text_Photo.getText().toString().trim();
                price = text_price.getText().toString().trim();
                duration = text_Duration.getText().toString().trim();
                frequency = text_frequency.getText().toString().trim();
                name = text_Name.getText().toString().trim();

                try {
                    SharedPreferences sp = getActivity().getSharedPreferences("details", context.MODE_PRIVATE);
                    String s1 = sp.getString("type", null);

                    if(s1 != null ){

                        Toast.makeText(getActivity(), "Your Selected Packages : "+s1, Toast.LENGTH_SHORT).show();
                        btn_select.setEnabled(false);
                    }
                    else{
                        //btn_select.setEnabled(true);

                        SharedPreferences sp1 = getActivity().getSharedPreferences("details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("type", sliver);
                        editor.putString("name",name);
                        editor.putString("services",services);
                        editor.putString("frequency",frequency);
                        editor.putString("duration",duration);
                        editor.putString("price",price);
                        editor.commit();

                        Toast.makeText(getActivity(), "Sliver Is Selected", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        return view;

    }

    public void getDataSliver() {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Uploading....");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        String Status = object.getString("package_type");

                        if (Status.equals("Sliver")) {

                            String data_Photo = object.getString("services");

                            String[] str = data_Photo.split(",");

                            text_Photo.setText("    " + str[0].toUpperCase());
                            text_video.setText("    " + str[1].toUpperCase());
                            text_LiveVideoCalling.setText("    " + str[2].toUpperCase());

                            String data_Frequency = object.getString("frequency");
                            text_frequency.setText("    " + "FREQUENCY" + "  " + data_Frequency + "  " + "MONTH");

                            String data_Duration = object.getString("duration");
                            text_Duration.setText("    " + "DURATION" + "  " + data_Duration + "  " + "MONTH");

                            String data_Price = object.getString("package_price");
                            text_price.setText("$" + data_Price);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error : " + error, Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

   /* @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = getActivity().getSharedPreferences("details", context.MODE_PRIVATE);
        String s1 = sp.getString("type", null);

        if(s1 != null){

            btn_select.setVisibility(View.GONE);
        }
        else{
            btn_select.setVisibility(View.VISIBLE);
        }
    }*/
}
