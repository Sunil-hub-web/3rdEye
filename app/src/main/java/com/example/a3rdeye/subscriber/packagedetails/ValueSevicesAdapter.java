package com.example.a3rdeye.subscriber.packagedetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

public class ValueSevicesAdapter extends RecyclerView.Adapter<ValueSevicesAdapter.Viewholder> {

    Context context;
    ArrayList<ValueAddedServices_ModelClass> services;
    final ArrayList<Package_ModelClass> value_Servicer = new ArrayList<> (  );
    SharedPreferences sp1;
    SharedPreferences.Editor editor;

    public ValueSevicesAdapter(Context context, ArrayList<ValueAddedServices_ModelClass> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @NotNull
    @Override
    public ValueSevicesAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.activity_valueaddes__services,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ValueSevicesAdapter.Viewholder holder, int position) {

        ValueAddedServices_ModelClass valuservices = services.get (position);

        String id = valuservices.getId ();
        String desc = valuservices.getDesc ();

        String[] arr = desc.split(",");

        holder.name.setText (valuservices.getName ());
        holder.price.setText (valuservices.getPrice ());
        holder.desc1.setText (arr[0]);
        holder.desc2.setText (arr[1]);

        if( id.equals ("5")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard2);
        } else if(id.equals ("6")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard3);
        } else if(id.equals ("7")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard4);
        } else if(id.equals ("8")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard5);
        } else if(id.equals ("9")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard6);
        } else if(id.equals ("10")){
            holder.linearLayout.setBackgroundResource(R.drawable.valuecard7);
        }

        holder.button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Package_ModelClass packages = new Package_ModelClass ( valuservices.getName (),valuservices.getDesc (),valuservices.getPrice ());
                value_Servicer.add (packages);

                HashSet hash =new HashSet (  );
                hash.addAll (value_Servicer);
                value_Servicer.clear ();
                value_Servicer.addAll (hash);

                Toast.makeText (context, value_Servicer.toString (), Toast.LENGTH_SHORT).show ( );

            }
        });


    }

    public ArrayList<Package_ModelClass>getValuAddedServices(){

        return value_Servicer;
    }

    @Override
    public int getItemCount() {
        return services.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name,price,desc1,desc2;
        CardView cardView;
        LinearLayout linearLayout;
        Button button;
        String json;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            name = itemView.findViewById (R.id.text);
            price = itemView.findViewById (R.id.price);
            desc1 = itemView.findViewById (R.id.desc);
            desc2 = itemView.findViewById (R.id.desc1);
            button = itemView.findViewById (R.id.select);
            cardView = itemView.findViewById (R.id.card_view);
            linearLayout = itemView.findViewById (R.id.linearLayout);

        }

    }



}
