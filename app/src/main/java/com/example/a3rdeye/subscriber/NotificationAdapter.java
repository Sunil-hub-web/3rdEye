package com.example.a3rdeye.subscriber;

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

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {

    Context context;
    ArrayList<Notification_ModelClass> notifi;

    public NotificationAdapter(Context context, ArrayList<Notification_ModelClass> notifi) {
        this.context = context;
        this.notifi = notifi;
    }

    @NonNull
    @NotNull
    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.notification,parent,false);
        return new Viewholder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationAdapter.Viewholder holder, int position) {

        Notification_ModelClass noti = notifi.get (position);

        String date = noti.getDate ();
        String time = noti.getTime ();

        holder.text_DateTime.setText (date+" : "+time);
        holder.text_Message.setText (noti.getNotification_Message ());

    }

    @Override
    public int getItemCount() {
        return notifi.size ();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView text_DateTime,text_Message;

        public Viewholder(@NonNull @NotNull View itemView) {
            super (itemView);

            text_DateTime = itemView.findViewById (R.id.datetime);
            text_Message = itemView.findViewById (R.id.message);
        }
    }
}
