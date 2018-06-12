package com.example.andrewapicelli.burritoradar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MapPlaceActivity extends AppCompatActivity {

    public static final String INTENT_NAME = "name";
    public static final String INTENT_RATING = "rating";
    public static final String INTENT_LATITUDE = "latitude";
    public static final String INTENT_LONGITUDE = "longitude";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_place);

        String name = getIntent().getStringExtra(INTENT_NAME);
        String rating = getIntent().getStringExtra(INTENT_RATING);
        double latitude = getIntent().getDoubleExtra(INTENT_LATITUDE, 0);
        double longitude = getIntent().getDoubleExtra(INTENT_LONGITUDE, 0);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);

        PlaceMapFragment placeMapFragment =
                PlaceMapFragment.newInstance(name, rating, latitude, longitude);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.place_map_fragment, placeMapFragment);
        transaction.commit();
    }

    /**
     * Used to make HomeButton function as back button
     * @param item
     * @return
     */
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
