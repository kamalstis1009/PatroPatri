package com.patropatri.app.views.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.MessageFragmentDialog;
import com.patropatri.app.views.fragments.UserDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private FragmentActivity mActivity;
    private UserWithToken mUser;
    private ArrayList<Candidate> mArrayList;
    private ArrayList<Candidate> mArrayList1;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    //private String [] iconsNames = {"Categories", "Gift Cards", "Express", "Orders", "Newsfeed"};
    //private int[] icons = { R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24 };

    public UserAdapter(FragmentActivity activity, ArrayList<Candidate> arrayList) {
        if (mArrayList != null) {
            mArrayList.clear();
            mArrayList1.clear();
        }
        mUser = SharedPefManager.getInstance(activity).getUser();
        this.mActivity = activity;
        this.mArrayList = arrayList;
        this.mArrayList1 = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            populateItemRows((MyViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return mArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void populateItemRows(MyViewHolder holder, int position) {
        Candidate model = mArrayList.get(position);

        holder.itemName.setText(model.getFullName());
        holder.itemTitle.setText(model.getProfession() + " | " + model.getDistrict());
        holder.itemDescription.setText(model.getSummary());
        holder.itemViewed.setText(model.getCommentAmount() + "");
        holder.itemLiked.setText(model.getLikedAmount() + "");

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("DATA", new Gson().toJson(model));
                UserDetailFragment mFragment = new UserDetailFragment();
                mFragment.setArguments(bundle);
                Utility.getInstance().onAddFragment(Objects.requireNonNull(mActivity).getSupportFragmentManager(), mFragment);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getAuth().getId() != model.getId()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DATA", new Gson().toJson(model));
                    MessageFragmentDialog mFragment = new MessageFragmentDialog();
                    mFragment.setArguments(bundle);
                    Utility.getInstance().onShowFragmentDialog(Objects.requireNonNull(mActivity).getSupportFragmentManager(), mFragment);
                }
            }
        });

        if (model.getImageName() != null) {
            Picasso.get().load(ConstantKey.RESOURCE_URL + model.getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( holder.imageView );
        }
        if (model.getImageCoverName() != null) {
            Picasso.get().load(ConstantKey.RESOURCE_URL + model.getImageCoverName() ).placeholder(R.drawable.background_image).error(R.drawable.background_image).into( holder.imageCoverView );
        }

        //holder.imageView.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        //holder.title.setText(iconsNames[position]);


        try {
            //String[] currentDate = model.getAttendanceDate().split("-");
            //holder.date.setText(currentDate[2] + "/" + currentDate[1] + "/" + currentDate[0]);
            //String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Utility.getInstance().getDateFromStringDate("yyyy-MM-dd", model.getAttendanceDate()));

            //String day = Utility.getInstance().getFormatDateFromDate("EEEE", Utility.getInstance().getParseDateFromStringDate("yyyy-MM-dd", model.getAttendanceDate()));
            //holder.day.setText(day); //yyyy-MM-dd HH:mm:ss | 2020-07-21 01:57:33.435

            holder.itemDate.setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", model.getCreatedAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    private void setLayoutBackgroundColor(@NonNull MyViewHolder holder) {
        //holder.content.setBackgroundColor(mActivity.getResources().getColor(R.color.colorBlack));
        //holder.itemName.setTextColor(mActivity.getResources().getColor(R.color.colorWhite));
        //(holder.imageButton.getBackground()).setColorFilter(mActivity.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN); //For Vector Drawable
    }

    private void setEnabledEditText(EditText editText, boolean isCursorDisabled, boolean isPressed, boolean isFocusable, boolean isTouchDisabled, boolean isClicked, boolean isEnabled) {
        editText.setCursorVisible(isCursorDisabled);
        editText.setPressed(isPressed);
        editText.setFocusable(isFocusable);
        editText.setFocusableInTouchMode(isTouchDisabled);
        editText.setClickable(isClicked);
        editText.setEnabled(isEnabled);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        CircleImageView imageView;
        ImageView imageCoverView;
        TextView itemName, itemTitle, itemDescription, itemDate, itemViewed, itemLiked;
        Button button;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            imageView = (CircleImageView) itemView.findViewById(R.id.item_image);
            imageCoverView = (ImageView) itemView.findViewById(R.id.item_bg_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemTitle = (TextView) itemView.findViewById(R.id.item_short_title);
            itemDescription = (TextView) itemView.findViewById(R.id.item_description);
            itemDate = (TextView) itemView.findViewById(R.id.item_date);
            itemViewed = (TextView) itemView.findViewById(R.id.item_viewed);
            itemLiked = (TextView) itemView.findViewById(R.id.item_liked);
            button = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }

    //http://programmingroot.com/android-recyclerview-search-filter-tutorial-with-example/
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    mArrayList = mArrayList1;
                } else {
                    ArrayList<Candidate> filterList = new ArrayList<>();
                    for (Candidate model : mArrayList1){
                        if (model.getFullName().toLowerCase().contains(charString.toLowerCase())){
                            filterList.add(model);
                        }
                    }
                    mArrayList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mArrayList = (ArrayList<Candidate>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}