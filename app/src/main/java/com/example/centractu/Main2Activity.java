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
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    List<Article> mesArticles = new ArrayList<Article>();
    JSONArray articles;
    List<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListView liste_article = findViewById(R.id.listview);
        ArrayList<String> source_list = getIntent().getStringArrayListExtra("source_list");
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
//                source_list);
//        liste_article.setAdapter(arrayAdapter);

//        solution temporaire
        try {
            JSONObject source1 = new JSONObject(getIntent().getStringExtra("jsonobject"));
            articles = source1.getJSONArray("articles");
            JSONObject article1 = articles.getJSONObject(0);
            Log.d("json", article1.get("title").toString());
            this.remplir_liste_article();

            ArticleAdapter adapter =new ArticleAdapter(mesArticles, this);
            liste_article.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        pour vérifier le passage de l'objet json
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
