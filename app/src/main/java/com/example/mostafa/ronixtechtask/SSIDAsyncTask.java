package com.example.mostafa.ronixtechtask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SSIDAsyncTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

    private ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();

    private static final String TAG_SSID_ID = "SSID";

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(String... strings) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String ssid_key;

        try {
            // Construct the URL for the SSID query
            URL url = new URL("http://ronixtech.com/ronix_services/task/srv.php");

            Log.v("SSID_Test",url.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                ssid_key = null;
            }
            assert inputStream != null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                ssid_key = null;
            }
            ssid_key = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            ssid_key = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        try {

            JSONObject mainObject = new JSONObject(ssid_key);
            JSONArray mainArray = mainObject.getJSONArray("results");

            for (int i=0;i<mainArray.length();i++){

                JSONObject c = mainArray.getJSONObject(i);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("SSID_id",c.getString(TAG_SSID_ID));
                arrayList.add(hashMap);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
