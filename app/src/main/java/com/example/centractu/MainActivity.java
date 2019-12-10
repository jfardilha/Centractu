package com.example.centractu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_source = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET, url_source, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray sources = response.getJSONArray("sources");
                            for (int i = 0; i<sources.length(); i++){
                                JSONObject source1 = sources.getJSONObject(i);
                                Log.d("json_test", source1.get("id").toString());
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json_test", "tout ne s'est pas passé comme prévu");
            }
        });
        queue.add(requete);
    }
}
