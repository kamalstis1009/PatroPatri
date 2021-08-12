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
import com.patropatri.app.models.Inbox;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.MessageFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Inbox> mArrayList;

    public InboxAdapter(FragmentActivity context, ArrayList<Inbox> arrayList) {
        this.mActivity = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_inbox, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Inbox model = mArrayList.get(position);

        if (model != null) {
            holder.itemName.setText(model.getSenderName());
            holder.itemMessage.setText(model.getMessage());
            try {
                holder.itemTime.setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", model.getCreatedAt()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (model.getSenderImage() != null) {
                Picasso.get().load(ConstantKey.RESOURCE_URL + model.getSenderImage() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
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
        CircleImageView imageView;
        TextView itemName, itemMessage, itemTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemMessage = (TextView) itemView.findViewById(R.id.item_message);
            itemTime = (TextView) itemView.findViewById(R.id.item_time);
        }
    }
}