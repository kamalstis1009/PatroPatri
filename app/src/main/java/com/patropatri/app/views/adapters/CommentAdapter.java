package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.CommentDto;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private FragmentActivity mActivity;
    private ArrayList<CommentDto> mArrayList;

    public CommentAdapter(FragmentActivity activity, ArrayList<CommentDto> arrayList) {
        this.mActivity = activity;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CommentDto model = mArrayList.get(position);



        if (model.getImageName() != null) {
            Picasso.get().load(ConstantKey.RESOURCE_URL + model.getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
        }
        holder.fullName.setText("" + model.getFullName());
        holder.comment.setText("" + model.getMessage());
        try {
            holder.commentDate.setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", model.getCreatedAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.commentReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView fullName, comment, commentDate, commentReply;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            fullName = (TextView) itemView.findViewById(R.id.item_name);
            comment = (TextView) itemView.findViewById(R.id.item_comment);
            commentDate = (TextView) itemView.findViewById(R.id.item_date);
            commentReply = (TextView) itemView.findViewById(R.id.item_reply);
        }
    }

}