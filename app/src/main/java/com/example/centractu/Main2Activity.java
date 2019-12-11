package com.example.centractu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ArrayList<String> source_list = getIntent().getStringArrayListExtra("source_list");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                source_list);
        ListView source = findViewById(R.id.listview);
        source.setAdapter(arrayAdapter);

//        solution temporaire
        try {
            JSONObject source1 = new JSONObject(getIntent().getStringExtra("jsonobject"));
            Log.d("json", source1.get("status").toString());
            JSONArray articles = source1.getJSONArray("articles");
            JSONObject article1 = articles.getJSONObject(0);
            Log.d("json", article1.get("title").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        pour vérifier le passage de l'objet json
    }
}
