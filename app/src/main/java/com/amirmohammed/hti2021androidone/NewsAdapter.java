package com.amirmohammed.hti2021androidone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amirmohammed.hti2021androidone.models.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{

    List<Article> articleList;

    public NewsAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Article article = articleList.get(position);

        holder.textViewTitle.setText(article.getTitle());

        Glide.with(holder.itemView)
                .load(article.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class NewsHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_news);
            textViewTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
