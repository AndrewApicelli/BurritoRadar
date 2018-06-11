package com.example.andrewapicelli.burritoradar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends FragmentActivity implements SearchResultClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_custom);
    }

    /**
     * Launches MapActivity with provided result
     * @param result
     */
    public void sendToMapActivity(SearchResult result){
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("name", result.getName());
        intent.putExtra("rating", result.getRating());
        intent.putExtra("latitude", result.getLatitude());
        intent.putExtra("longitude", result.getLongitude());
        startActivity(intent);
    }

    @Override
    public void resultClicked(SearchResult result) {
        sendToMapActivity(result);
    }

}
