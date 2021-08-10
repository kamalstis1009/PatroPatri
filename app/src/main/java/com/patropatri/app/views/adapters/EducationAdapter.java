package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Education;

import java.util.ArrayList;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Education> mArrayList;

    public EducationAdapter(FragmentActivity activity, ArrayList<Education> arrayList) {
        this.mActivity = activity;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_education, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Education model = mArrayList.get(position);

        holder.itemTitle.setText(model.getDegree() + " - " + model.getPassingYear());
        holder.itemSubject.setText("" + model.getSubject());
        holder.itemResult.setText("" + model.getResult());
        holder.itemUniversity.setText("" + model.getInstitution());
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemSubject, itemResult, itemUniversity;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemSubject = (TextView) itemView.findViewById(R.id.item_subject);
            itemResult = (TextView) itemView.findViewById(R.id.item_result);
            itemUniversity = (TextView) itemView.findViewById(R.id.item_university);
        }
    }

}