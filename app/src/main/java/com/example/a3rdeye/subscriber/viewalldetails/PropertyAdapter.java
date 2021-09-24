package com.example.a3rdeye.subscriber.viewalldetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.geofence.DirectionMapForGeofence;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.Viewholder> {

  private Context context;
  private ArrayList<Property_ModelClass> listProperty;

    public PropertyAdapter(Context context, ArrayList<Property_ModelClass> listProperty) {
        this.context = context;
        this.listProperty = listProperty;
    }

    @NonNull
    @NotNull
    @Override
    public PropertyAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.property_details,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PropertyAdapter.Viewholder holder, int position) {

        Property_ModelClass prop = listProperty.get (position);

        holder.userid.setText ("user Id :\n"+prop.getUser_id ());
        holder.propertyid.setText ("property id :\n"+prop.getProperty_Id ());
        holder.latitude.setText (prop.getLatitude ());
        holder.longitude.setText (prop.getLongitude ());
        holder.country_name.setText (prop.getCountary_Nmae ());
        holder.locality.setText (prop.getLocality ());
        holder.postalcode.setText (prop.getPostal_Code ());
        holder.address.setText (prop.getAddress ());

        holder.cardView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( context, DirectionMapForGeofence.class);
                intent.putExtra ("userid",prop.getUser_id ());
                intent.putExtra ("propertyid",prop.getProperty_Id ());
                intent.putExtra ("latitude",prop.getLatitude ());
                intent.putExtra ("longitude",prop.getLongitude ());
                intent.putExtra ("country",prop.getCountary_Nmae ());
                intent.putExtra ("locality",prop.getLocality ());
                intent.putExtra ("postalcode",prop.getPostal_Code ());
                intent.putExtra ("address",prop.getAddress ());
                context.startActivity (intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listProperty.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userid,propertyid,latitude,longitude,country_name,locality,postalcode,address;
        CardView cardView;
        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            userid = itemView.findViewById (R.id.userid);
            propertyid = itemView.findViewById (R.id.propertyid);
            latitude = itemView.findViewById (R.id.text_view1);
            longitude = itemView.findViewById (R.id.text_view2);
            country_name = itemView.findViewById (R.id.text_view3);
            locality = itemView.findViewById (R.id.text_view4);
            postalcode = itemView.findViewById (R.id.text_view5);
            address = itemView.findViewById (R.id.text_view6);
            cardView = itemView.findViewById (R.id.card);
        }
    }
}
