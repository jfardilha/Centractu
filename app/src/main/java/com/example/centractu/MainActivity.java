package com.example.centractu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_source = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
        final ArrayList<String> source_list = new ArrayList<>();
        final ArrayList<String> source_names = new ArrayList<>();
        final TextView txt = findViewById(R.id.text);
        String url_source1;
        JsonObjectRequest requete = new JsonObjectRequest(Request.Method.GET, url_source, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray sources = response.getJSONArray("sources");
                            for (int i = 0; i<sources.length(); i++){
                                JSONObject source1 = sources.getJSONObject(i);
                                source_list.add(source1.get("id").toString());
                                source_names.add(source1.get("name").toString());
                                Log.d("json_test", source1.get("id").toString());
//                              A enlever après
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("json_test", "tout ne s'est pas passé comme prévu");
                txt.setText("Something went wrong !");
            }
        });
        queue.add(requete);

        url_source1 = "https://newsapi.org/v2/everything?" +
                "apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=lequipe";
//        pas opti : à changer
        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, url_source1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent main_page = new Intent(MainActivity.this, Main2Activity.class);
                        main_page.putExtra("source_list",source_list);
                        main_page.putExtra("source_names", source_names);
                        main_page.putExtra("jsonobject", response.toString());
                        startActivity(main_page);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("La source ne peut être atteinte");
            }
        });
        queue.add(request2);

    }
}
