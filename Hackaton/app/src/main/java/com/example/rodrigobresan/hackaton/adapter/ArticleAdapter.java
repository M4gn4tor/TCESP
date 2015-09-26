package com.example.rodrigobresan.hackaton.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigobresan.hackaton.R;
import com.example.rodrigobresan.hackaton.models.Article;

import java.util.List;

/**
 * Created by Rodrigo Bresan on 26/09/2015.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private Context context;
    private int layoutResourceId;
    private List<Article> articles;

    public ArticleAdapter(Context context, int layoutResourceId, List<Article> articles) {
        super(context, layoutResourceId, articles);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ArticleHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ArticleHolder();
            holder.title = (TextView) row.findViewById(R.id.txtTitle);
            holder.image = (ImageView) row.findViewById(R.id.imgPicture);
            holder.description = (TextView) row.findViewById(R.id.txtDescription);

            row.setTag(holder);
        } else {
            holder = (ArticleHolder) row.getTag();
        }

        Article article = articles.get(position);
        holder.title.setText(article.getTitle());

        return row;
    }

    static class ArticleHolder {
        TextView title;
        ImageView image;
        TextView description;
        Article article;
    }

}
