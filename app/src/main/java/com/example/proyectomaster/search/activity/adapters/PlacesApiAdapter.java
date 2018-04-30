package com.example.proyectomaster.search.activity.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesApiAdapter extends RecyclerView.Adapter<PlacesApiAdapter.ViewHolder> {


    private List<Result> results;

    public PlacesApiAdapter(List<Result> results) {
        this.results = results;
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
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void updateData(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txv_name)
        TextView txvName;

        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(Result result) {
            txvName.setText(result.getName());
        }
    }
}
