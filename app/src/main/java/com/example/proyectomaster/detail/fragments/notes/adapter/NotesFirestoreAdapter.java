package com.example.proyectomaster.detail.fragments.notes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.fragments.notes.entities.Comment;
import com.example.proyectomaster.lib.ImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesFirestoreAdapter extends FirestoreRecyclerAdapter<Comment, NotesFirestoreAdapter.ViewHolder> {

    String TAG = NotesFirestoreAdapter.class.getSimpleName();
    ImageLoader imageLoader;

    public NotesFirestoreAdapter(@NonNull FirestoreRecyclerOptions<Comment> options, ImageLoader imageLoader) {
        super(options);
        this.imageLoader = imageLoader;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Comment model) {
        holder.bindItem(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_name)
        TextView txvName;
        @BindView(R.id.txv_date)
        TextView txvDate;
        @BindView(R.id.txv_comment)
        TextView txvComment;
        @BindView(R.id.imageView)
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Comment model) {

            if (model.getName() != null) {
                txvName.setText(model.getName());
            }

            if (model.getDate() != null) {
                txvDate.setText(model.getDate());
            }

            if (model.getComment() != null) {
                txvComment.setText(model.getComment());
            }

            if (model.getUrl() != null) {
                imageLoader.load(imageView, model.getUrl());
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
