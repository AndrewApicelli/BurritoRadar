package com.example.andrewapicelli.burritoradar;

import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_custom);

        String name = getIntent().getStringExtra("name");
        String rating = getIntent().getStringExtra("rating");
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("rating", rating);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        PlaceMapFragment myObj = new PlaceMapFragment();
        myObj.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_map_fragment, myObj);
        transaction.commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }
}
