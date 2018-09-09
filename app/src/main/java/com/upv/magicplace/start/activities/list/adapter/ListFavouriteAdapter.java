package com.upv.magicplace.start.activities.list.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upv.magicplace.R;
import com.upv.magicplace.start.activities.list.entitites.FavouritePlaceModel;
import com.upv.magicplace.start.activities.list.listeners.OnClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFavouriteAdapter extends FirestoreRecyclerAdapter<FavouritePlaceModel, ListFavouriteAdapter.ViewHolder> {


    OnClickListener listener;

    public ListFavouriteAdapter(@NonNull FirestoreRecyclerOptions<FavouritePlaceModel> options, OnClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FavouritePlaceModel model) {
        holder.bindItem(model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_place, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_name)
        TextView txvName;
        @BindView(R.id.txv_address)
        TextView txvAddress;
        @BindView(R.id.igv_delete_favourite)
        ImageView igvDeleteFavourite;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(final FavouritePlaceModel model) {
            txvName.setText(model.getPlaceName());
            txvAddress.setText(model.getAddress());
            igvDeleteFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteItemClickListener(model);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListenr(model);
                }
            });
        }
    }
}
