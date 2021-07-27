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
        ((ImageButton) view.findViewById(R.id.item_gallery)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_notification)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_message)).setOnClickListener(new ActionHandler());
        ((TextView) view.findViewById(R.id.item_settings)).setOnClickListener(new ActionHandler());

        return view;
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_button) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new AccountFragment());
            }
            if (v.getId() == R.id.item_profile) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new ProfileFragment());
            }
            if (v.getId() == R.id.item_gallery) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new GalleryFragment());
            }
            if (v.getId() == R.id.item_notification) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new NotificationFragment());
            }
            if (v.getId() == R.id.item_message) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new InboxFragment());
            }
            if (v.getId() == R.id.item_settings) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new SettingFragment());
            }
        }
    }

}