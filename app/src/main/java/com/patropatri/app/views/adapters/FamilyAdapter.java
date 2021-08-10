package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Family;

import java.util.ArrayList;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Family> mArrayList;

    public FamilyAdapter(FragmentActivity activity, ArrayList<Family> arrayList) {
        this.mActivity = activity;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_family, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Family model = mArrayList.get(position);

        holder.itemRelation.setText("" + model.getRelation());
        holder.itemName.setText("" + model.getFullName());
        holder.itemOccupation.setText("" + model.getOccupation());
        holder.itemDetails.setText("" + model.getSummary());
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemRelation, itemName, itemOccupation, itemDetails;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRelation = (TextView) itemView.findViewById(R.id.item_relation);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemOccupation = (TextView) itemView.findViewById(R.id.item_occupation);
            itemDetails = (TextView) itemView.findViewById(R.id.item_details);
        }
    }

}