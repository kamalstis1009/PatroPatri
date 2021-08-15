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
import com.patropatri.app.models.Gallery;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.views.dialogs.ImageViewerDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<Gallery> mArrayList;

    public PhotoAdapter(FragmentActivity context, ArrayList<Gallery> arrayList) {
        this.mActivity = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_photo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Gallery model = mArrayList.get(position);

        if (model.getImageName() != null)
            Picasso.get().load(ConstantKey.RESOURCE_URL + model.getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
        //holder.imageView.setImageResource(model.getIcon());
        //holder.imageView.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strings = new ArrayList<>();
                for (int i=0; i<mArrayList.size(); i++) {
                    strings.add(mArrayList.get(i).getImageName());
                }
                if (strings.size() > 0) {
                    ImageViewerDialog.getInstance(mActivity).getAlertDialog(strings, position);
                }
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
