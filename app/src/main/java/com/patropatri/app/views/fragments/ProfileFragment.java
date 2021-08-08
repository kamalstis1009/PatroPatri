package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.EditProfileFragmentDialog;

public class ProfileFragment extends Fragment implements EditProfileFragmentDialog.BottomSheetListener {

    private TextView mItemSummary, mItemName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        mItemSummary = (TextView) view.findViewById(R.id.item_summary);
        mItemName = (TextView) view.findViewById(R.id.item_name);

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new ActionHandler());

        ((ImageButton) view.findViewById(R.id.item_summary_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_personal_info_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_profession_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_contact_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_education_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_family_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_interest_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_activity_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_expectation_button)).setOnClickListener(new ActionHandler());

        return view;
    }

    @Override
    public void onAddItem(User model) {
        if (model != null) {
            if (model.getSummary() != null)
                mItemSummary.setText(model.getSummary());
            if (model.getFullName() != null)
                mItemName.setText(model.getFullName());
        }
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_button) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new ProfileFragment());
            }
            if (v.getId() == R.id.item_summary_button) {
                onShowDialog(1);
            }
            if (v.getId() == R.id.item_personal_info_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_profession_button) {
                onShowDialog(3);
            }
            if (v.getId() == R.id.item_contact_button) {
                onShowDialog(4);
            }
            if (v.getId() == R.id.item_education_button) {
                onShowDialog(5);
            }
            if (v.getId() == R.id.item_family_button) {
                onShowDialog(6);
            }
            if (v.getId() == R.id.item_interest_button) {
                onShowDialog(7);
            }
            if (v.getId() == R.id.item_activity_button) {
                onShowDialog(8);
            }
            if (v.getId() == R.id.item_expectation_button) {
                onShowDialog(9);
            }
        }
    }

    private void onShowDialog(int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onShow(position);
            }
        });
    }
    private void onShow(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        EditProfileFragmentDialog mFragment = new EditProfileFragmentDialog(this);
        mFragment.setArguments(bundle);
        Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
    }
}