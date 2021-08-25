package com.patropatri.app.views.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;

import java.util.ArrayList;

public class AttachPhotoAdapter extends RecyclerView.Adapter<AttachPhotoAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Bitmap> mArrayList;

    public AttachPhotoAdapter(FragmentActivity context, ArrayList<Bitmap> arrayList) {
        if (mArrayList != null) {
            //mArrayList.clear(); //change here
        }
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
        Bitmap bitmap = mArrayList.get(position);

        holder.imageView.setImageBitmap(bitmap);
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
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }
}
