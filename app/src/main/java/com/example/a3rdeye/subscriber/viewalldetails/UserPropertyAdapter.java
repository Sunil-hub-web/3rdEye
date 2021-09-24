package com.example.a3rdeye.subscriber.viewalldetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class UserPropertyAdapter extends RecyclerView.Adapter<UserPropertyAdapter.Viewholder> {

    Context context;
    ArrayList<UserProperty_ModelClass> userPropertyArrayList ;

    public UserPropertyAdapter(Context context, ArrayList<UserProperty_ModelClass> userPropertyArrayList) {
        this.context = context;
        this.userPropertyArrayList = userPropertyArrayList;
    }

    @NonNull
    @NotNull
    @Override
    public UserPropertyAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.userproperty_details,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserPropertyAdapter.Viewholder holder, int position) {

        UserProperty_ModelClass userProperty = userPropertyArrayList.get (position);

        String address = userProperty.getUserAddress ();
        address = address.replace ("Address :","");
        holder.userId.setText (userProperty.getUserId ());
        holder.propertyid.setText (userProperty.getPropertyId ());
        holder.address.setText (address);
        holder.coordinetes.setText (userProperty.getCoordinetes ());

    }

    @Override
    public int getItemCount() {
        return userPropertyArrayList.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView userId,propertyid,address,coordinetes;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            userId = itemView.findViewById (R.id.userid);
            propertyid = itemView.findViewById (R.id.propertyid);
            address = itemView.findViewById (R.id.address);
            coordinetes = itemView.findViewById (R.id.coordinetes);
        }
    }
}
