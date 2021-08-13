package com.patropatri.app.views.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Notification;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.MessageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Notification> mArrayList;

    public NotificationAdapter(FragmentActivity context, ArrayList<Notification> arrayList) {
        this.mActivity = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification model = mArrayList.get(position);

        if (model != null) {
            holder.itemTitle.setText(model.getTitle());
            if (model.getType().contains("LIKED")) {
                holder.itemIcon.setImageResource(R.drawable.ic_baseline_favorite_24);
            }
            if (model.getType().contains("COMMENT")) {
                holder.itemIcon.setImageResource(R.drawable.ic_baseline_comment_24);
            }
            if (model.getType().contains("MESSAGE")) {
                holder.itemIcon.setImageResource(R.drawable.ic_baseline_email_24);
            }
            if (model.getSenderImage() != null) {
                Picasso.get().load(ConstantKey.RESOURCE_URL + model.getSenderImage() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
            }
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
                if (model.getType().contains("MESSAGE")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DATA", new Gson().toJson(model));
                    MessageFragment mFragment = new MessageFragment();
                    mFragment.setArguments(bundle);
                    Utility.getInstance().onAddFragment(Objects.requireNonNull(mActivity).getSupportFragmentManager(), mFragment);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        CircleImageView imageView;
        TextView itemTitle, itemTime;
        ImageView itemIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title);
            itemTime = (TextView) itemView.findViewById(R.id.item_time);
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }
}