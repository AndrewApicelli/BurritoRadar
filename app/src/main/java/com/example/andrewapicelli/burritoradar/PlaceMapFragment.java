package com.example.andrewapicelli.burritoradar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class PlaceMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String name;
    private String rating;
    private double latitude;
    private double longitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null) {
            // Inflate the layout for this fragment
            name = getArguments().getString("name");
            rating = getArguments().getString("rating");
            latitude = getArguments().getDouble("latitude");
            longitude = getArguments().getDouble("longitude");
        }

        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView tvName = view.findViewById(R.id.map_result_name);
        TextView tvRating = view.findViewById(R.id.map_result_rating);

        tvName.setText(name);
        tvRating.setText(rating + getString(R.string.ascii_star));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapsInitializer.initialize(getActivity());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng latLng = new LatLng(latitude, longitude);

        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_map_pin)));

        CameraPosition camResult = CameraPosition.builder().target(latLng)
                .zoom(16).bearing(0).tilt(45).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camResult));
    }

}
