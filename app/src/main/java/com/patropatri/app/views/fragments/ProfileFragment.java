package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.patropatri.app.R;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.EditProfileFragmentDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new ActionHandler());

        ((ImageButton) view.findViewById(R.id.summary_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.personal_info_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_contact_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_education_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_family_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_interest_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_expectation_button)).setOnClickListener(new ActionHandler());

        return view;
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_button) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new ProfileFragment());
            }
            if (v.getId() == R.id.summary_button) {
                onShowDialog(1);
            }
            if (v.getId() == R.id.personal_info_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_contact_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_education_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_family_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_interest_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_expectation_button) {
                onShowDialog(2);
            }
        }
    }

    private void onShowDialog(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("Position", position);
        EditProfileFragmentDialog mFragment = new EditProfileFragmentDialog();
        mFragment.setArguments(bundle);
        Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
    }
}