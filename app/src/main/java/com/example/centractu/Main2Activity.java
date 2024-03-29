package com.example.centractu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    List<Article> mesArticles = new ArrayList<>();
    JSONArray articles;
    TextView source_title;
    Spinner spinner;
    ArrayList<String> source_list;
    ArrayList<String> source_names;
    ListView liste_article;
    String source_id;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        liste_article = findViewById(R.id.listview);
        spinner = findViewById(R.id.spinner);
        source_list = getIntent().getStringArrayListExtra("source_list");
        source_list.add(0, "Sources");

//        menu déroulant avec les sources en français
        source_names = getIntent().getStringArrayListExtra("source_names");
        source_names.add(0, "Sources");

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
            String name = source_names.get(3);
            source_id = source_list.get(3);
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

            Button btn = new Button(this);
            btn.setText("Load more ...");
            liste_article.addFooterView(btn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    source_id = source_list.get(position);
                    raffraichir_page(source_id);

                    String name = source_names.get(position);
                    source_title.setText(name);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue2 = Volley.newRequestQueue(Main2Activity.this);
                String url = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources="
                +source_id+"&page=2";
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    articles = response.getJSONArray("articles");
                                    for (int i=0; i<articles.length(); i++) {
                                        JSONObject article = articles.getJSONObject(i);
                                        mesArticles.add(new Article(article.get("title").toString(),
                                                article.get("description").toString(),
                                                article.get("url").toString(),
                                                article.get("urlToImage").toString()));
                                        ArticleAdapter adapter =new ArticleAdapter(mesArticles,
                                                Main2Activity.this);
                                        liste_article.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue2.add(request);
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

    private void raffraichir_page(String source){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_source1 = "https://newsapi.org/v2/everything?" +
                "apiKey=35bf446307124bdc80419062b1a6be02&language=fr&sources="+source;
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, url_source1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            articles = response.getJSONArray("articles");
                            remplir_liste_article();
                            ArticleAdapter adapter =new ArticleAdapter(mesArticles, Main2Activity.this);
                            liste_article.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(request3);
    }
}

