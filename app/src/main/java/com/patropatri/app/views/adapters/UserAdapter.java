package com.patropatri.app.views.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.fragments.UserDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {

    private FragmentActivity mActivity;
    private ArrayList<User> mArrayList;
    private ArrayList<User> mArrayList1;

    //private String [] iconsNames = {"Categories", "Gift Cards", "Express", "Orders", "Newsfeed"};
    //private int[] icons = { R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24, R.drawable.ic_baseline_lock_24 };

    public UserAdapter(FragmentActivity activity, ArrayList<User> arrayList) {
        if (mArrayList != null) {
            mArrayList.clear();
            mArrayList1.clear();
        }
        this.mActivity = activity;
        this.mArrayList = arrayList;
        this.mArrayList1 = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User model = mArrayList.get(position);



        holder.textView.setText(model.getFirstName() + " " + model.getLastName());
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

        if (model.getImageName() != null) {
            //Picasso.get().load(ConstantKey.SERVER_URL + "Uploads/" + model.getImageName()).placeholder(R.drawable.ic_baseline_shopping_cart_24).error(R.drawable.ic_baseline_shopping_cart_24).into(((ImageView) view.findViewById(R.id.item_image)));
        }

        //holder.imageView.setColorFilter(mActivity.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        //holder.title.setText(iconsNames[position]);


        try {
            //String[] currentDate = model.getAttendanceDate().split("-");
            //holder.date.setText(currentDate[2] + "/" + currentDate[1] + "/" + currentDate[0]);
            //String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Utility.getInstance().getDateFromStringDate("yyyy-MM-dd", model.getAttendanceDate()));

            //String day = Utility.getInstance().getFormatDateFromDate("EEEE", Utility.getInstance().getParseDateFromStringDate("yyyy-MM-dd", model.getAttendanceDate()));
            //holder.day.setText(day); //yyyy-MM-dd HH:mm:ss | 2020-07-21 01:57:33.435
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (mArrayList != null) ? mArrayList.size() : 0;
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
        TextView textView;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
            textView = (TextView) itemView.findViewById(R.id.item_name);
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
                    ArrayList<User> filterList = new ArrayList<>();
                    for (User model : mArrayList1){
                        if (model.getFirstName().toLowerCase().contains(charString.toLowerCase())){
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
                mArrayList = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}