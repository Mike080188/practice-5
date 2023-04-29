package com.example.practice_5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.practice_5.R;
import com.example.practice_5.model.Location;
import com.example.practice_5.model.LocationAdapter;
import java.util.ArrayList;
import java.util.List;

public class LocationsFragment extends Fragment{

    private LocationAdapter mLocationAdapter;
    private RecyclerView mRecyclerView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_locations, container, false);

            /** Instantiate the RecyclerView */
            mRecyclerView = view.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            mLocationAdapter = new LocationAdapter(getLocations());
            mRecyclerView.setAdapter(mLocationAdapter);

            return view;
        }
    private List<Location> getLocations() {
        /** Return list of Locations */
        List<Location> locations = new ArrayList<Location>();

        Location location = new Location();
        location.setName("Penn State Great Valley");
        location.setAddress("30 East Swedesford Rd, Malvern, PA 19355");
        locations.add(location);

        location = new Location();
        location.setName("My House");
        location.setAddress("698 2nd Ave, Folsom, PA 19033");
        locations.add(location);

        location = new Location();
        location.setName("The White House");
        location.setAddress("1600 Pennsylvania Avenue NW, Washington, DC 20500");
        locations.add(location);

        location = new Location();
        location.setName("The Taj Mahal");
        location.setAddress("Dharmapuri, Forest Colony, Tajganj, Agra, Uttar Pradesh 282001, India");
        locations.add(location);

        location = new Location();
        location.setName("Birj Kalifa");
        location.setAddress("1 Sheikh Mohammed bin Rashid Blvd - Downtown Dubai - Dubai - United Arab Emirates");
        locations.add(location);

        location = new Location();
        location.setName("The Eiffel Tower");
        location.setAddress("Champ de Mars, 5 Av. Anatole France, 75007 Paris, France");
        locations.add(location);

        return locations;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
