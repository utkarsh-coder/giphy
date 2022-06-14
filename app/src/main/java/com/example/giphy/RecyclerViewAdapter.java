package com.example.giphy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<GifObject> list;
    private String text;

    public RecyclerViewAdapter(Context context, ArrayList<GifObject> list, String text) {
        this.context = context;
        this.list = list;
        this.text = text;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(holder.getAdapterPosition()).getImages().getOriginal().getUrl()+".gif").into(holder.gifImageView);
        holder.uidTextView.setText(list.get(holder.getAdapterPosition()).getId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gifImageView;
        TextView uidTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gifImageView = itemView.findViewById(R.id.gif_image_view);
            uidTextView = itemView.findViewById(R.id.uid_text_view);
        }
    }
}
