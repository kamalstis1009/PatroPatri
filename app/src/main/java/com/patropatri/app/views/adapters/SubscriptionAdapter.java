package com.patropatri.app.views.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Subscription;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.MessageFragment;

import java.util.ArrayList;
import java.util.Objects;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Subscription> mArrayList;

    public SubscriptionAdapter(FragmentActivity context, ArrayList<Subscription> arrayList) {
        this.mActivity = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_subscription, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Subscription model = mArrayList.get(position);

        if (model != null) {
            holder.itemPrice.setText(model.getPrice() + "");
            holder.itemName.setText(model.getName());
            try {
                holder.itemTime.setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", model.getCreatedAt()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //holder.itemIcon.setImageResource(model.getIcon());
        //holder.itemIcon.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("DATA", new Gson().toJson(model));
                MessageFragment mFragment = new MessageFragment();
                mFragment.setArguments(bundle);
                Utility.getInstance().onAddFragment(Objects.requireNonNull(mActivity).getSupportFragmentManager(), mFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView itemPrice, itemName, itemTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemTime = (TextView) itemView.findViewById(R.id.item_time);
        }
    }
}