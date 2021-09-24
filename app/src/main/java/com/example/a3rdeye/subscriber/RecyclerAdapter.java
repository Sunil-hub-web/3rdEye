package com.example.a3rdeye.subscriber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {

    Context context;
    private List<Model> itemview;

    public RecyclerAdapter(AreaDetails areaDetails, ArrayList<Model> categories) {


        this.context = areaDetails;
        this.itemview = categories;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewholder holder, int position) {

        Model model = itemview.get(position);

        //holder.text_id.setText(model.getId());

        holder.text_catagory.setText(model.getCatagory());

    }

    @Override
    public int getItemCount() {
        return itemview.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView text_id, text_catagory;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            //text_id = itemView.findViewById(R.id.catagoryid);
            text_catagory = itemView.findViewById(R.id.catagoryname);
        }
    }
}
