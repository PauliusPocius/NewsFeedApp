package com.example.paulius.newsfeedapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Paulius on 2017-03-07.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() throws JSONException {
    }

    public static List<NewFeed> fetchnewfeeds(String requestURL){


        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){

        }

        List<NewFeed> newFeeds = extractnewfeeds(jsonResponse);

        return newFeeds;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("nu", "Error with creating URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readfromStream(inputStream);
        }catch (IOException e){
            Log.e(LOG_TAG,"response code is not 200",e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readfromStream(InputStream inputStream)throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputstreamreader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferreader = new BufferedReader(inputstreamreader);
            String line = bufferreader.readLine();
            while (line != null){
                output.append(line);
                line = bufferreader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link NewFeed} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<NewFeed> extractnewfeeds(String newfeedjson) {


        if (TextUtils.isEmpty(newfeedjson)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<NewFeed> newFeeds = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {


            JSONObject jsonObject = new JSONObject(newfeedjson);
            JSONObject jsonObject1= jsonObject.getJSONObject("response");
            JSONArray jsonArray= jsonObject1.getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++){

                JSONObject properties = jsonArray.getJSONObject(i);

                String url = properties.getString("webUrl");
                String section = properties.getString("sectionName");
                String name = properties.getString("webTitle");
                newFeeds.add(new NewFeed(section,name,url));

            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return newFeeds;
    }
}
