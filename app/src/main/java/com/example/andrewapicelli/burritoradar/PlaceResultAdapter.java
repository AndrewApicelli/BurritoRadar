package com.example.andrewapicelli.burritoradar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * RecycleViewAdapter for representing a PlaceResult in the RecycleView
 */
public class PlaceResultAdapter extends RecyclerView.Adapter<PlaceResultAdapter.ViewHolder> {

    private Context mContext;
    private List<PlaceResult> results = new ArrayList<>();
    private PlaceListFragment.PlaceListSelectionListener clickListener;

    private Observable selectedItem = new Observable();

    public PlaceResultAdapter(Context context, PlaceListFragment.PlaceListSelectionListener clickListener){
        mContext = context;
        this.clickListener = clickListener;
    }

    /**
     * Appends to the current list
     * @param newResults
     */
    public void addAll(List<PlaceResult> newResults){
        for (PlaceResult result : newResults) {
            results.add(result);
            int positon = results.indexOf(result);
            notifyItemInserted(positon);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.result_card, parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TextView tvName = holder.tvName;
        TextView tvAddress = holder.tvAddress;
        TextView tvRating = holder.tvRating;

        PlaceResult currentResult = results.get(position);

        tvName.setText(currentResult.getName());
        tvAddress.setText(currentResult.getAddress());
        tvRating.setText(currentResult.getRating() + mContext.getString(R.string.ascii_star));

        holder.setResult(currentResult);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvAddress;
        TextView tvRating;

        PlaceResult result;

        public ViewHolder(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.result_name);
            tvAddress = itemView.findViewById(R.id.result_address);
            tvRating = itemView.findViewById(R.id.result_rating);

            itemView.setOnClickListener(this);

        }

        public void setResult(PlaceResult result) {
            this.result = result;
        }

        public PlaceResult getResult() {
            return result;
        }

        @Override
        public void onClick(View view) {
            clickListener.placeSelected(getResult());
        }
    }
}
