package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.LikedDto;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<LikedDto> mArrayList;

    public LikedAdapter(FragmentActivity activity, ArrayList<LikedDto> arrayList) {
        this.mActivity = activity;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        LikedDto model = mArrayList.get(position);

        holder.itemName.setText("" + model.getFullName());
        holder.itemIcon.setVisibility(View.GONE);

        if (model.getImageName() != null) {
            Picasso.get().load(ConstantKey.RESOURCE_URL + model.getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
        }
        try {
            holder.itemDate.setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", model.getCreatedAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        ImageView itemIcon;
        TextView itemName, itemDate;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_title);
            itemDate = (TextView) itemView.findViewById(R.id.item_time);
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
        }
    }

}