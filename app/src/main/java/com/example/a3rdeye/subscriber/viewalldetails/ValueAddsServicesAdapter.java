package com.example.a3rdeye.subscriber.viewalldetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;
import com.example.a3rdeye.subscriber.packagedetails.Package_ModelClass;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ValueAddsServicesAdapter extends RecyclerView.Adapter<ValueAddsServicesAdapter.Viewholder> {

    private ArrayList<Package_ModelClass> list_pkcg;
    private ArrayList<PackageDetails_ModelClass> list_package;
    private Context context;
    PackagesAdapter adapter = new PackagesAdapter (context,list_package);
    public ValueAddsServicesAdapter(ArrayList<Package_ModelClass> list_pkcg, Context context) {
        this.list_pkcg = list_pkcg;
        this.context = context;
    }



    @NonNull
    @NotNull
    @Override


    public ValueAddsServicesAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.view_valueaddservices,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ValueAddsServicesAdapter.Viewholder holder, int position) {


            Package_ModelClass pkcg = list_pkcg.get (position);
           // PackageDetails_ModelClass packageDetails_modelClass = list_package.get (position);


                    String desc = pkcg.getDesc ();

                    String[] arr = desc.split(",");


                     //list_pkcg.clear ();
                    holder.vname.setText (pkcg.getName ());

                    holder.desc1.setText (arr[0]);
                    holder.desc2.setText (arr[1]);

                    holder.price.setText (pkcg.getPrice ());




        }


    @Override
    public int getItemCount() {
        return list_pkcg.size ();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType (position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId (position);
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView vname,price,desc1,desc2;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            vname = itemView.findViewById (R.id.vname);
            desc1 = itemView.findViewById (R.id.vdesc);
            desc2 = itemView.findViewById (R.id.vdesc1);
            price = itemView.findViewById (R.id.price);
        }
    }
}
