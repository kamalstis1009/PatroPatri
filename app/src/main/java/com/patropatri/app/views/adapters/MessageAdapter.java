package com.patropatri.app.views.adapters;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private FragmentActivity mContext;
    private ArrayList<ChatMessage> mArrayList;
    private ProgressDialog mProgress;

    public MessageAdapter(FragmentActivity context, ArrayList<ChatMessage> arrayList) {
        if (mArrayList != null) {
            mArrayList.clear(); //change here
        }
        this.mContext = context;
        this.mArrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChatMessage model = mArrayList.get(position);

        if (model.isUser()) {
            setVisibility(true, holder);

            if (model.getAttachmentUrl() != null) {
                try {
                    //ImageLoader.getInstance().displayImage(model.getAttachmentUrl(), holder.myAttachment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (holder.myAttachment.getVisibility() == View.VISIBLE) {
                    holder.myAttachment.setVisibility(View.GONE);
                }
            }
            if (model.getChatText().isEmpty()) {
                holder.myText.setText("Attached Image");
            } else {
                holder.myText.setText(model.getChatText());
            }
        } else {
            setVisibility(false, holder);
            if (model.getAttachmentUrl() != null) {
                try {
                    //ImageLoader.getInstance().displayImage(model.getAttachmentUrl(), holder.otherAttachment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (holder.otherAttachment.getVisibility() == View.VISIBLE) {
                    holder.otherAttachment.setVisibility(View.GONE);
                }
            }
            if (model.getChatText().isEmpty()) {
                holder.otherText.setText("Attached Image");
            } else {
                holder.otherText.setText(model.getChatText());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList != null ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout otherChatLayout, myChatLayout;
        private CircleImageView otherImage, myImage;
        private TextView otherText, myText;
        private ImageView otherAttachment, myAttachment;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            otherChatLayout = (RelativeLayout) itemView.findViewById(R.id.other_chat_layout);
            otherImage = (CircleImageView) itemView.findViewById(R.id.other_chat_image);
            otherText = (TextView) itemView.findViewById(R.id.other_chat_text);
            otherAttachment = (ImageView) itemView.findViewById(R.id.other_chat_attachment);

            myChatLayout = (RelativeLayout) itemView.findViewById(R.id.my_chat_layout);
            myImage = (CircleImageView) itemView.findViewById(R.id.my_chat_image);
            myText = (TextView) itemView.findViewById(R.id.my_chat_text);
            myAttachment = (ImageView) itemView.findViewById(R.id.my_chat_attachment);
        }
    }

    private void setVisibility(boolean isMine,  MyViewHolder holder) {
        if (isMine) {
            if (holder.myChatLayout.getVisibility() == View.GONE) {
                holder.myChatLayout.setVisibility(View.VISIBLE);
            }
            if (holder.otherChatLayout.getVisibility() == View.VISIBLE) {
                holder.otherChatLayout.setVisibility(View.GONE);
            }
        } else {
            if (holder.myChatLayout.getVisibility() == View.VISIBLE) {
                holder.myChatLayout.setVisibility(View.GONE);
            }
            if (holder.otherChatLayout.getVisibility() == View.GONE) {
                holder.otherChatLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    //https://stackoverflow.com/questions/29980281/android-create-a-custom-recyclerview-adapter-and-create-other-class-from-it
    //https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data

    /*
    There are also ones for particular list elements, like notifyItemInserted(), notifyItemRemoved() or even notifyItemChanged()
    and more. That’s way the proper animations will fire correctly
    //notifyItemChanged(holder.getAdapterPosition()); //reload one item
    //notifyDataSetChanged(); //reload all item



    public void insert(final Chat model, int index) {
        mArrayList.add(index, model);
        notifyItemInserted(index);
    }

    public void remove(Chat model) {
        final int position = getPosition(model);
        mArrayList.remove(model);
        notifyItemRemoved(position);
    }

    1) REMOVE: There are 4 steps to remove an item from a RecyclerView
    list.remove(position);
    recycler.removeViewAt(positon);
    mAdapter.notifyItemRemoved(position);
    mAdapter.notifyItemRangeChanged(position, list.size());

    2) UPDATE THE DATA: The only things I had to do is
    mAdapter.notifyDataSetChanged();

    private int getPosition(final Chat item) {
        return mArrayList.indexOf(item);
    }*/
}
