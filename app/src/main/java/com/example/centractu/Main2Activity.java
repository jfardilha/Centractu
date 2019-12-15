package com.example.centractu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    List<Article> mesArticles = new ArrayList<>();
    JSONArray articles;
    List<String> urls = new ArrayList<>();
    TextView source_title;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final ListView liste_article = findViewById(R.id.listview);
        spinner = findViewById(R.id.spinner);
        ArrayList<String> source_list = getIntent().getStringArrayListExtra("source_list");

//        menu déroulant avec les sources en français
        ArrayList<String> source_names = getIntent().getStringArrayListExtra("source_names");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                source_names);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


//        solution temporaire
        try {
            JSONObject source1 = new JSONObject(getIntent().getStringExtra("jsonobject"));
            articles = source1.getJSONArray("articles");
            JSONObject article1 = articles.getJSONObject(0);
//            JSONObject source = article1.getJSONObject("source");
            String name = source_names.get(2);
            source_title = findViewById(R.id.textView2);
            source_title.setText(name);
            Log.d("json", article1.get("title").toString());
            this.remplir_liste_article();

            ArticleAdapter adapter =new ArticleAdapter(mesArticles, this);
            liste_article.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        pour vérifier le passage de l'objet json

        liste_article.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main2Activity.this, WebviewActivity.class);
                intent.putExtra("url", mesArticles.get(position).getUrl());
                startActivity(intent);
            }
        });
    }
    private void remplir_liste_article() throws JSONException {
        mesArticles.clear();
        for (int i=0; i<articles.length(); i++) {
            JSONObject article = articles.getJSONObject(i);
            mesArticles.add(new Article(article.get("title").toString(),
                    article.get("description").toString(), article.get("url").toString(),
                    article.get("urlToImage").toString()));
        }
    }
}
