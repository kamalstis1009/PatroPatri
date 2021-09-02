package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.views.dialogs.ImageViewerDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatAttachAdapter extends RecyclerView.Adapter<ChatAttachAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private String [] mArrayList;

    public ChatAttachAdapter(FragmentActivity context, String [] arrayList) {
        this.mActivity = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_attach_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String imageName = mArrayList[position];

        if (imageName != null)
            Picasso.get().load(ConstantKey.RESOURCE_URL + imageName ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
        //holder.imageView.setImageResource(model.getIcon());
        //holder.imageView.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>(Arrays.asList(mArrayList));
                if (strings.size() > 0) {
                    ImageViewerDialog.getInstance(mActivity).getAlertDialog(strings, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null && mArrayList.length > 0) ? mArrayList.length : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
