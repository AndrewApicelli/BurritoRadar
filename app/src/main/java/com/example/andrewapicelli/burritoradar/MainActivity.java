package com.example.andrewapicelli.burritoradar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PlaceListFragment.PlaceListSelectionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Launches MapPlaceActivity with provided result
     * @param result
     */
    public void sendToMapActivity(PlaceResult result){
        Intent intent = new Intent(this, MapPlaceActivity.class);
        intent.putExtra(MapPlaceActivity.INTENT_NAME, result.getName());
        intent.putExtra(MapPlaceActivity.INTENT_RATING, result.getRating());
        intent.putExtra(MapPlaceActivity.INTENT_LATITUDE, result.getLatitude());
        intent.putExtra(MapPlaceActivity.INTENT_LONGITUDE, result.getLongitude());
        startActivity(intent);
    }

    @Override
    public void placeSelected(PlaceResult result) {
        sendToMapActivity(result);
    }

}
