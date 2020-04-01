package com.roundfifteen.popularmovies.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static String extractReviewsFromResponse(String response) throws JSONException {
        JSONObject jsonResToObj = new JSONObject(response);
        JSONArray resultArray = new JSONArray(jsonResToObj.getString(RESULTS));
        if (resultArray.length() == 0) {
            return null;
        } else {
            JSONObject firstElement = resultArray.getJSONObject(0);
            return firstElement.getString("content");
        }
    }

}
