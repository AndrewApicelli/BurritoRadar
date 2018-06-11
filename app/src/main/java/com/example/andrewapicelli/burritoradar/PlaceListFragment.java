package com.example.andrewapicelli.burritoradar;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Fragment containing contents list of location based search into Google Places api
 */
public class PlaceListFragment extends Fragment implements SearchResultClickListener {

    private final int REQUEST_FINE_LOCATION = 0;

    private RecyclerView recyclerView;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private SearchResultAdapter adapter;

    private SearchResultClickListener mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new SearchResultAdapter(getActivity(),this);

        recyclerView.setAdapter(adapter);

        tryGetNearbyResults();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SearchResultClickListener){
            this.mCallback = (SearchResultClickListener) context;
        }
    }

    /**
     * Checks and confirms permissions before attempting to get location for place search
     */
    private void tryGetNearbyResults(){
        if(ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            getDeviceLocation();
        }
        else {
            toast("Location permissions needed to find a burrito near you");
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_FINE_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getDeviceLocation();
            } else {
                toast("Permission not granted");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    /**
     * Establish users location with location services. Runs Places search upon successfully
     * retrieving location.
     */
    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Log.d("loc", "onComplete: " + task.getResult());
                        Location loc = (Location) task.getResult();
                        queryPlaces(loc);
                    } else {
                        Log.d("loc", "onComplete: Error retrieving location...");
                        toast("Error retrieving location...");
                    }
                }
            });
        } catch (SecurityException e) {
            Log.e("loc", "getDeviceLocation: Security Exception: " + e.getMessage());
            toast("Error retrieving location...");
        }

    }

    /**
     * Executes HTTP request with provided location to Google Places api and hands results to RecycleView
     * adapter for rendering.
     * @param location
     */
    private void queryPlaces(Location location){
        String query = "burrito restaurant";
        String radius = "1500";
        String type = "restaurant";
        String key = getString(R.string.google_maps_key);
        String loc = location.getLatitude() + ", " + location.getLongitude();

        String myUrl = Uri.parse("https://maps.googleapis.com/maps/api/place/textsearch/json?")
                .buildUpon()
                .appendQueryParameter("query", query)
                .appendQueryParameter("radius", radius)
                .appendQueryParameter("type", type)
                .appendQueryParameter("location", loc)
                .appendQueryParameter("key", key).build().toString();
        Log.i("URL", "onCreate: " + myUrl);

        OkHttpClient httpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(myUrl)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                toast("Failed to connect...");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String strResp = response.body().string();
                    Log.i("RESULT FROM QUERY", "onResponse: " + strResp);

                    try {
                        JSONObject jsonObject = new JSONObject(strResp);
                        final List<SearchResult> searchResults = new SearchResultBuilder().build(jsonObject);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.addAll(searchResults);
                            }
                        });

                    } catch (JSONException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toast("Failed to connect...");
                            }
                        });
                    }

                } else {
                    toast("Failed to connect...");
                }
            }
        });
    }

    public void toast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void resultClicked(SearchResult result) {
        if(mCallback != null){
            mCallback.resultClicked(result);
        }
    }

}
