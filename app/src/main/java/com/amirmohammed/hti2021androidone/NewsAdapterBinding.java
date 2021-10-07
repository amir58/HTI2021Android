package com.amirmohammed.hti2021androidone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amirmohammed.hti2021androidone.databinding.ItemNewsBinding;
import com.amirmohammed.hti2021androidone.models.Article;
import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapterBinding extends RecyclerView.Adapter<NewsAdapterBinding.NewsHolder> {

    List<Article> articleList;

    public NewsAdapterBinding(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                ,R.layout.item_news, parent, false));

//        return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Article article = articleList.get(position);
        holder.binding.setArticle(article);

//        holder.binding.tvTitle.setText(article.getTitle());

//        Glide.with(holder.itemView)
//                .load(article.getImageUrl())
//                .placeholder(R.mipmap.ic_launcher)
//                .into(holder.binding.ivNews);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }


    class NewsHolder extends RecyclerView.ViewHolder {
        //        ImageView imageView;
//        TextView textViewTitle;
        ItemNewsBinding binding;

        public NewsHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

//            imageView = itemView.findViewById(R.id.iv_news);
//            textViewTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
