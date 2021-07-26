package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.patropatri.app.R;
import com.patropatri.app.utils.Utility;

import java.util.Objects;

public class AccountFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //-----------------------------------------------| ClickListener
        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_profile)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_photo)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_notification)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_message)).setOnClickListener(new ActionHandler());
        ((TextView) view.findViewById(R.id.item_settings)).setOnClickListener(new ActionHandler());
        ((TextView) view.findViewById(R.id.item_change_password)).setOnClickListener(new ActionHandler());
        ((TextView) view.findViewById(R.id.item_change_language)).setOnClickListener(new ActionHandler());

        return view;
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_button) {
                Utility.getInstance().onBackFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new AccountFragment());
            }
            if (v.getId() == R.id.item_profile) {
                //Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new OrderFragment());
            }
            if (v.getId() == R.id.item_photo) {
                //Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new ProfileFragment());
            }
            if (v.getId() == R.id.item_notification) {
                Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new NotificationFragment());
            }
            if (v.getId() == R.id.item_message) {
                Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new MessageFragment());
            }
            if (v.getId() == R.id.item_settings) {
                Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new SettingFragment());
            }
            if (v.getId() == R.id.item_change_password) {
                Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new PasswordFragment());
            }
            if (v.getId() == R.id.item_change_language) {
                //Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new PasswordFragment());
            }
        }
    }

}