package com.example.centractu;

import android.app.VoiceInteractor;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleAdapter extends BaseAdapter {
    List<Article> liste_articles;
    LayoutInflater inflater;

    public ArticleAdapter(List<Article> liste_articles, Context context) {
        this.liste_articles = liste_articles;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        ImageView img;
        TextView titre;
        TextView description;

    }
    @Override
    public int getCount() {
        return liste_articles.size();
    }

    @Override
    public Article getItem(int position) {
        return liste_articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            Log.v("test", "convertView is null");
            holder = new ViewHolder();
//            if (position % 2 == 0){
//                convertView = inflater.inflate(R.layout.livre_item, null);
//            }
//            else{
//                convertView = inflater.inflate(R.layout.livre_item_2, null);
//            }
            convertView = inflater.inflate(R.layout.article1, null);
            holder.titre = (TextView) convertView.findViewById(R.id.textView);
            holder.description = (TextView) convertView.findViewById(R.id.textView3);

            convertView.setTag(holder);
        } else {
            Log.v("test", "convertView is not null");
            holder = (ViewHolder) convertView.getTag();
        }
        Article article = liste_articles.get(position);
        holder.titre.setText(article.getTitre());
        holder.description.setText(article.getDescription());
        ImageView image = convertView.findViewById(R.id.imageView);
        if (article.getUrl_image() != null){
            Picasso.get().load(article.getUrl_image()).into(image);
        }

        return convertView;
    }
}
