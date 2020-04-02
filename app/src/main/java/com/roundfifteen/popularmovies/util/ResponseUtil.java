package com.roundfifteen.popularmovies.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.roundfifteen.popularmovies.util.Constants.*;

public class ResponseUtil {

    public static String extractTrailerKeyFromResponse(String response) throws JSONException {
        JSONObject jsonResToObj = new JSONObject(response);
        JSONArray resultArray = new JSONArray(jsonResToObj.getString(RESULTS));

        if (resultArray.length() == 0) {
            return null;
        } else {
            JSONObject firstElement = resultArray.getJSONObject(0);
            return firstElement.getString(KEY);
        }
    }

    public static List<String> extractReviewsFromResponse(String response) throws JSONException {
        JSONObject jsonResToObj = new JSONObject(response);
        JSONArray resultArray = new JSONArray(jsonResToObj.getString(RESULTS));
        if (resultArray.length() == 0) {
            return null;
        } else {
            List<String> reviews = new ArrayList<>();
            System.out.println(resultArray.length());
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject currElement = resultArray.getJSONObject(i);
                reviews.add(currElement.getString("content"));
            }

            return reviews;
        }
    }

}
