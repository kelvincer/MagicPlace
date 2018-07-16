package com.example.proyectomaster.search.activity.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.search.entities.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesApiAdapter extends RecyclerView.Adapter<PlacesApiAdapter.ViewHolder> {


    private List<Result> results;
    OnItemClickListener onItemClickListener;

    public PlacesApiAdapter(List<Result> results, OnItemClickListener onItemClickListener) {
        this.results = results;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result result = results.get(position);
        holder.bindItem(result);
        holder.setOnItemClickListener(onItemClickListener, result.getPlaceId());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateData(List<Result> results) {
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public void clearData() {
        final int size = results.size();
        results.clear();
        notifyItemRangeRemoved(0, size);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_name)
        TextView txvName;
        @BindView(R.id.txv_distance)
        TextView txvDistance;
        @BindView(R.id.txv_address)
        TextView txvAddress;
        private View view;
        @BindView(R.id.txv_rating)
        TextView txvRating;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void bindItem(Result result) {
            txvName.setText(result.getName());

            if (CommonHelper.SEARCH_MODE == 1) {
                txvAddress.setText(result.getFormattedAddress());
                txvDistance.setText((Helper.getDistanceFromMyLocation(result.getGeometry().getLocation())));
            } else if (CommonHelper.SEARCH_MODE == 2) {
                txvAddress.setText(result.getVicinity());
                txvDistance.setText((Helper.getDistanceFromQueryLocation(result.getGeometry().getLocation())));
            } else {
                throw new RuntimeException("Illegal SEARCH_MODE");
            }

            if (result.getRating() != null) {
                txvRating.setText(String.format("%s", result.getRating()));
                txvRating.setBackgroundColor(Color.parseColor("#129067"));
            } else {
                txvRating.setText("--");
                txvRating.setBackgroundColor(Color.parseColor("#a8a8a8"));
            }
        }

        public void setOnItemClickListener(final OnItemClickListener listener, final String id) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(id);
                }
            });
        }
    }
}
