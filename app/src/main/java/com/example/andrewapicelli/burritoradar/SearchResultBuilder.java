package com.example.andrewapicelli.burritoradar;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultBuilder {

    /**
     * Builds results provided a JSONObject representing the root of the
     * TextSearch Places api result
     * @param responseObject
     * @return
     */
    public List<SearchResult> build(JSONObject responseObject){

        List<SearchResult> alSearchResult = new ArrayList<>();
        JSONArray resultArray = null;
        try {
            resultArray = responseObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < resultArray.length(); i++) {
            try {
                JSONObject resultObject = resultArray.getJSONObject(i);
                alSearchResult.add(toResult(resultObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return alSearchResult;

    }

    /**
     * Builds a single result provided the JSONObject representing the 'result'
     * object in the TextSearch places API
     * @param resultObject
     * @return
     */
    private SearchResult toResult(JSONObject resultObject){

        String name = "";
        String address = "";
        String rating = "";
        double longitude = 0.0;
        double latitude = 0.0;

        try {

            name = resultObject.getString("name");
            address = resultObject.getString("formatted_address");
            rating = resultObject.getString("rating");
            latitude = resultObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            longitude = resultObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("pars", "toResult: ERROR PARSING");
        }


        SearchResult result = new SearchResult();
        result.setName(name);
        result.setAddress(address);
        result.setRating(rating);
        result.setLatitude(latitude);
        result.setLongitude(longitude);

        return result;
    }

}
