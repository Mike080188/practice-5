package com.example.practice_5.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practice_5.R;
import com.example.practice_5.activity.MapsActivity;
import com.google.android.gms.maps.model.LatLng;

public class PickCoordinatesFragment extends Fragment {

    private Button mShowOnMap;

    private Context mContext;

    private EditText mLongitudeEditText;
    private EditText mLatitudeEditText;

    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_coordinates, container, false);
        mContext = getContext();
        mShowOnMap = root.findViewById(R.id.frag_show_location_on_map_button);
        mLongitudeEditText = root.findViewById(R.id.edit_text_longitude);
        mLatitudeEditText = root.findViewById(R.id.edit_text_latitude);

        mShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passCoordinatesToMapsActivity();
            }
        });

        return root;
    }

    public void passCoordinatesToMapsActivity(){
        /* Validate coordinates then pass to MapsActivity as extra */
        String longText = mLongitudeEditText.getText().toString();
        String latText = mLatitudeEditText.getText().toString();

        // Validate the provided longitude and latitude
        if (longText.isEmpty() || latText.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(),"Please provide longitude and latitude",Toast.LENGTH_LONG).show();
            return;
        }

        Double longDouble = Double.parseDouble(longText);
        Double latDouble = Double.parseDouble(latText);

        if (longDouble <  -180|| longDouble > 180) {
            Toast.makeText(getActivity().getApplicationContext(),"Longitude must be between -180 and 180",Toast.LENGTH_LONG).show();
            return;
        }

        if (latDouble <  -90|| latDouble > 90) {
            Toast.makeText(getActivity().getApplicationContext(),"Latitude must be between -90 and 90",Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(getActivity().getApplicationContext(),"Latitude: " + latText + " Longitude: " + longText,Toast.LENGTH_LONG).show();

        // Pass coordinates to MApsActivity as extra
        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("coordinates", new LatLng(latDouble, longDouble));
        mContext.startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
