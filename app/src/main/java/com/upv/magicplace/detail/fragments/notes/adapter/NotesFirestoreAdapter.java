package com.upv.magicplace.detail.fragments.notes.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.upv.magicplace.Helper;
import com.upv.magicplace.R;
import com.upv.magicplace.detail.fragments.notes.entities.Comment;
import com.upv.magicplace.lib.ImageLoader;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_two, parent, false);
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
        @BindView(R.id.txv_character_name)
        TextView txvCharacterName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Comment model) {

            if (model.getName() != null && !model.getName().isEmpty()) {
                txvName.setText(model.getName());
                txvCharacterName.setText(String.valueOf(model.getName().toUpperCase().charAt(0)));
                GradientDrawable background = (GradientDrawable) txvCharacterName.getBackground();
                background.setColor(Helper.generateRandomCodeColor());
            } else {
                if (model.getEmail() != null) {
                    txvName.setText(model.getEmail());
                    txvCharacterName.setText(String.valueOf(model.getEmail().toUpperCase().charAt(0)));
                    GradientDrawable background = (GradientDrawable) txvCharacterName.getBackground();
                    background.setColor(Helper.generateRandomCodeColor());
                }
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
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
