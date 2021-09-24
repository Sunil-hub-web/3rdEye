package com.example.a3rdeye.subscriber.viewalldetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdeye.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.Viewholder> {

    private Context context;
    private ArrayList<PackageDetails_ModelClass> list_pckgdetail;
    private ArrayList<Package> list_pkcg;
    ArrayList<String> arrayList;
    PackagesDetails packagesDetails;

    public PackagesAdapter(Context context, ArrayList<PackageDetails_ModelClass> list_pckgdetail) {
        this.context = context;
        this.list_pckgdetail = list_pckgdetail;

    }

    @NonNull
    @NotNull
    @Override
    public PackagesAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ( )).inflate (R.layout.activity_packages_details, parent, false);
        return new Viewholder (view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PackagesAdapter.Viewholder holder, int position) {

        PackageDetails_ModelClass packageDetails_modelClass = list_pckgdetail.get (position);
        //Package_ModelClass pkcg = list_pkcg.get (position);

        String serv = packageDetails_modelClass.getServices ( );

        String[] arr = serv.split (",");

       /* String value  = packageDetails_modelClass.getValue ();

        value = value.replace ("[", "");
        value = value.replace ("]", "");

        String str[] = value.split (",");
        list_pkcg = new ArrayList<> (Arrays.asList (str));

        ArrayAdapter arrayAdapter = new ArrayAdapter (context,R.layout.list_view, list_pkcg);
        holder.recyclerView.setAdapter (arrayAdapter);*/

        packagesDetails = new PackagesDetails ();

       /* list_pkcg = packagesDetails.getvas ();

        Log.d ("vas",list_pkcg.toString ());*/

        holder.name.setText (packageDetails_modelClass.getPackage_type ( ));


        holder.money.setText (packageDetails_modelClass.getPackage_price ( ));

        if (packageDetails_modelClass.getServices ( ).equals ("PHOTO")) {

            arrayList = new ArrayList<> (  );

           arrayList.add ("   "+arr[0]);
           arrayList.add ("   "+packageDetails_modelClass.getFrequency ( ));
           arrayList.add ("   "+packageDetails_modelClass.getDuration ( ));

            ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_view,arrayList);
            holder.listView.setAdapter (adapter);

        }
        else if(packageDetails_modelClass.getServices ( ).equals ("PHOTO,VIDEO")){

            arrayList = new ArrayList<> (  );

            arrayList.add ("   "+arr[0]);
            arrayList.add ("   "+arr[1]);
            arrayList.add ("   "+packageDetails_modelClass.getFrequency ( ));
            arrayList.add ("   "+packageDetails_modelClass.getDuration ( ));

            ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_view,arrayList);
            holder.listView.setAdapter (adapter);

        }
        else if (packageDetails_modelClass.getServices ( ).equals ("PHOTO,VIDEO,LIVE VIDEO CALLING")){

            arrayList = new ArrayList<> (  );

            arrayList.add ("   "+arr[0]);
            arrayList.add ("   "+arr[1]);
            arrayList.add ("   "+arr[2]);
            arrayList.add ("   "+packageDetails_modelClass.getFrequency ( ));
            arrayList.add ("   "+packageDetails_modelClass.getDuration ( ));

            ArrayAdapter adapter = new ArrayAdapter(context, R.layout.list_view,arrayList);
            holder.listView.setAdapter (adapter);
        }

        holder.value.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( context,ShowValue.class );
                intent.putExtra ("packageid",packageDetails_modelClass.getId ());
                context.startActivity (intent);

            }
        });

        holder.button.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( context,ShowValue.class );
                intent.putExtra ("packageid",packageDetails_modelClass.getId ());
                context.startActivity (intent);
            }
        });




    }

    @Override
    public long getItemId(int position) {
        return super.getItemId (position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType (position);
    }

    @Override
    public int getItemCount() {
        return list_pckgdetail.size ( );
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name,money,value;
        ListView listView;
        Button button;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            name = itemView.findViewById (R.id.name);
            money = itemView.findViewById (R.id.money);

            listView = itemView.findViewById (R.id.list);
            value = itemView.findViewById (R.id.value);
            button = itemView.findViewById (R.id.show);

        }
    }

    /*public ArrayList<String> getVAs(){
        return list_pkcg;
    }*/
}
