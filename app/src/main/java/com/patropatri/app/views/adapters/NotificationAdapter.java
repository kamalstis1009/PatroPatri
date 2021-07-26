package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Notification;

import java.util.ArrayList;

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
            if (model.getType().contains("LIKED")) {
                holder.textView.setText("Adnan liked your resume.");
            }
            if (model.getType().contains("COMMENT")) {
                holder.textView.setText("Ahmed Hasan posted a comment on your resume.");
            }
            if (model.getType().contains("MESSAGE")) {
                holder.textView.setText("Hasan sent a message on your inbox.");
            }
        }

        //holder.imageView.setImageResource(model.getIcon());
        //holder.imageView.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Bundle bundle = new Bundle();
                bundle.putString("category", title);
                FragmentCategoryWiseProduct mFragment = new FragmentCategoryWiseProduct();
                mFragment.setArguments(bundle);
                Utility.getInstance().onAddFragment(Objects.requireNonNull(mActivity).getSupportFragmentManager(), mFragment);*/
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
        TextView textView, textView2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            textView = (TextView) itemView.findViewById(R.id.item_title);
            textView2 = (TextView) itemView.findViewById(R.id.item_time);
        }
    }
}