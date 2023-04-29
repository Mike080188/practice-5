package com.example.practice_5.model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice_5.R;
import com.example.practice_5.activity.MapsActivity;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    /* Adapter class for the Locations RecyclerView */
    private List<Location> mLocations;

    private Context mContext;
    public LocationAdapter(List<Location> locations) {
        mLocations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.location_item, parent, false);
        CardView cardView = (CardView) view.findViewById(R.id.location_card_view);

        cardView.setUseCompatPadding(true); // Optional: adds padding for pre-lollipop devices
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = mLocations.get(position);
        /* Listener for mShowOnMapButton which passes selected location to MapsActivity */
        holder.mShowOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passLocationToMapsActivity(location);
            }
        });

        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public void setLocations(List<Location> locations) {
        mLocations = locations;
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private Button mShowOnMapButton;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.location_text_view);
            mShowOnMapButton= itemView.findViewById(R.id.show_location_on_map);
        }

        public void bind(Location location) {
            mNameTextView.setText(location.getName());
            mShowOnMapButton.setText("Show On Map");
        }
    }

    public void passLocationToMapsActivity(Location location){
        /* Open the maps activity passing Location extra*/
        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("location", location);
        mContext.startActivity(intent);
    }

}
