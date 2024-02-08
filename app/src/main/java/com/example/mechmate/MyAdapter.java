package com.example.mechmate;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;




    public MyAdapter(Context context, ArrayList<Customers> customersArrayList, double meclat, double meclon) {
        this.context = context;
        this.customersArrayList = customersArrayList;
        this.meclat = meclat;
        this.meclon = meclon;
    }
    public void setMechanicLocation(double meclat, double meclon) {
        this.meclat = meclat;
        this.meclon = meclon;
        notifyDataSetChanged(); // Notify adapter about the updated location
    }

    // Interface for handling item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void removeItem(int position) {
        customersArrayList.remove(position);
        notifyItemRemoved(position);
    }
    Context context;
    ArrayList<Customers> customersArrayList;

    double meclat;
    double meclon;

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.customeratmech,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

//        Customers customers = customersArrayList.get(position);
//
//        holder.cusname.setText(customers.customernames);
//        double cuslat = customers.customerLatitude;
//        double cuslon = customers.customerLongitude;
//
//        double distance = calculateDistance(meclat, meclon, cuslat, cuslon);
//        // Display the distance in your TextView
//        holder.cusdist.setText(String.format(Locale.getDefault(), "Approx. %.2f km", distance));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(listener != null){
//                    int position = holder.getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listener.onItemClick(position);
//                    }
//                }
//
//            }
//        });

        Customers customers = customersArrayList.get(position);

        double cuslat = customers.customerLatitude;
        double cuslon = customers.customerLongitude;

        double distance = calculateDistance(meclat, meclon, cuslat, cuslon);

        // Check if the customer is within 10 km radius
        if (distance <= 10.0) {
            holder.cusname.setText(customers.customernames);
            holder.cusdist.setText(String.format(Locale.getDefault(), "Approx. %.2f km", distance));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = holder.getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        } else {
            // If the customer is outside the 10 km radius, you can choose to hide the item or show a message
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            // Alternatively, you can set some text to indicate that the customer is outside the radius
//             holder.cusname.setText("Customer outside 10 km radius");
        }
    }

    @Override
    public int getItemCount() {
        return customersArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cusname, cusdist;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cusname = itemView.findViewById(R.id.cusname);
            cusdist = itemView.findViewById(R.id.cusdist);

        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
//        final int R = 6371; // Radius of the Earth in kilometers
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return R * c; // Distance in kilometers

        float[] results = new  float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        double distance = results[0];
        return  distance/1000;

    }
}
