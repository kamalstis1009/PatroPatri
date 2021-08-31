package com.patropatri.app.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private FragmentActivity mContext;
    private Inbox mInbox;
    private UserWithToken mUser;
    private ArrayList<Message> mArrayList;

    private CallBackListener mListener;

    public interface CallBackListener {
        void onAddItem(Message model);
        void onRecyclerViewItems(RecyclerView recyclerView, String [] arrayList);
    }

    public MessageAdapter(FragmentActivity context, ArrayList<Message> arrayList, Inbox inbox, CallBackListener listener) {
        if (mArrayList != null) {
            mArrayList.clear(); //change here
        }
        mUser = SharedPefManager.getInstance(context).getUser();
        this.mContext = context;
        this.mArrayList = arrayList;
        this.mInbox = inbox;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message model = mArrayList.get(position);

        if (model != null && mUser != null) {
            if (model.getReceiverId() == mUser.getAuth().getId()) {
                setVisibility(true, holder);
                holder.senderText.setText(model.getMessage());
                if (mInbox != null && mInbox.getSenderImage() != null) {
                    Picasso.get().load(ConstantKey.RESOURCE_URL + mInbox.getSenderImage()).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(holder.senderImage);
                }

                if(model.getImageFiles() != null) {
                    if (mListener != null)
                        mListener.onRecyclerViewItems(holder.senderRecyclerView, model.getImageFiles());
                }
            } else {
                setVisibility(false, holder);
                holder.receiverText.setText(model.getMessage());
                if (mUser.getUser().getImageName() != null) {
                    Picasso.get().load(ConstantKey.RESOURCE_URL + mUser.getUser().getImageName()).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(holder.receiverImage);
                }
                if(model.getImageFiles() != null) {
                    if (mListener != null)
                        mListener.onRecyclerViewItems(holder.receiverRecyclerView, model.getImageFiles());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList != null ? mArrayList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout receiverLayout, senderLayout;
        private CircleImageView receiverImage, senderImage;
        private TextView receiverText, senderText;
        private RecyclerView receiverRecyclerView, senderRecyclerView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverLayout = (RelativeLayout) itemView.findViewById(R.id.item_receiver_layout);
            receiverImage = (CircleImageView) itemView.findViewById(R.id.item_receiver_image);
            receiverText = (TextView) itemView.findViewById(R.id.item_receiver_text);
            receiverRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_receiver_recycler_view);

            senderLayout = (RelativeLayout) itemView.findViewById(R.id.item_sender_layout);
            senderImage = (CircleImageView) itemView.findViewById(R.id.item_sender_image);
            senderText = (TextView) itemView.findViewById(R.id.item_sender_text);
            senderRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_sender_recycler_view);
        }
    }

    private void setVisibility(boolean isMine,  MyViewHolder holder) {
        if (isMine) {
            if (holder.senderLayout.getVisibility() == View.GONE) {
                holder.senderLayout.setVisibility(View.VISIBLE);
            }
            if (holder.receiverLayout.getVisibility() == View.VISIBLE) {
                holder.receiverLayout.setVisibility(View.GONE);
            }
        } else {
            if (holder.senderLayout.getVisibility() == View.VISIBLE) {
                holder.senderLayout.setVisibility(View.GONE);
            }
            if (holder.receiverLayout.getVisibility() == View.GONE) {
                holder.receiverLayout.setVisibility(View.VISIBLE);
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
