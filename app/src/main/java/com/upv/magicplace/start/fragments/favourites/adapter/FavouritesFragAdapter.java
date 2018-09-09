package com.upv.magicplace.start.fragments.favourites.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upv.magicplace.R;
import com.upv.magicplace.start.entities.CategoryModel;
import com.upv.magicplace.start.fragments.favourites.listener.OnCategoryClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesFragAdapter extends FirestoreRecyclerAdapter<CategoryModel, FavouritesFragAdapter.ViewHolder> {


    OnCategoryClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FavouritesFragAdapter(@NonNull FirestoreRecyclerOptions<CategoryModel> options, OnCategoryClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder,
                                    int position,
                                    @NonNull CategoryModel model) {

        holder.bindItem(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_favourites, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_category)
        TextView txvCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(final CategoryModel category) {
            txvCategory.setText(category.getType_name());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(category);
                }
            });
        }
    }
}
