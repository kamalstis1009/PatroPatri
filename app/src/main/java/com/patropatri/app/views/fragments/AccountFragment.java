package com.patropatri.app.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.patropatri.app.R;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.activities.SignInActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private UserWithToken mUser;
    private UserViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //-----------------------------------------------| Session
        mUser = SharedPefManager.getInstance(getActivity()).getUser();
        if (mUser.getUser() != null) {
            if (mUser.getUser().getIsActive() != null && mUser.getUser().getIsActive())
                ((Switch) view.findViewById(R.id.is_active_switch)).setChecked(true);
            if (mUser.getUser().getFullName() != null)
                ((TextView) view.findViewById(R.id.item_name)).setText(mUser.getUser().getFullName());
            if (mUser.getUser().getImageName() != null)
                Picasso.get().load(ConstantKey.RESOURCE_URL + mUser.getUser().getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( ((CircleImageView) view.findViewById(R.id.item_photo)) );
        }

        //-----------------------------------------------| Receive and observe the data from view model
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        //-----------------------------------------------| ClickListener
        ((Switch) view.findViewById(R.id.is_active_switch)).setOnCheckedChangeListener(new ActionEventHandler());
        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new ActionEventHandler());
        ((ImageButton) view.findViewById(R.id.item_profile)).setOnClickListener(new ActionEventHandler());
        ((ImageButton) view.findViewById(R.id.item_gallery)).setOnClickListener(new ActionEventHandler());
        ((ImageButton) view.findViewById(R.id.item_notification)).setOnClickListener(new ActionEventHandler());
        ((ImageButton) view.findViewById(R.id.item_message)).setOnClickListener(new ActionEventHandler());

        ((LinearLayout) view.findViewById(R.id.item_setting_button)).setOnClickListener(new ActionEventHandler());
        ((LinearLayout) view.findViewById(R.id.item_terms_button)).setOnClickListener(new ActionEventHandler());
        ((LinearLayout) view.findViewById(R.id.item_about_button)).setOnClickListener(new ActionEventHandler());
        ((LinearLayout) view.findViewById(R.id.item_sign_out_button)).setOnClickListener(new ActionEventHandler());

        return view;
    }

    private class ActionEventHandler implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (getActivity() != null && mUser != null && mUser.getAuth() != null) {
                mUser.getUser().setIsActive(isChecked);
                SharedPefManager.getInstance(getActivity()).saveUser(mUser);
                if (isChecked) {
                    Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new SubscriptionFragment());
                } else {
                    mViewModel.updateUserPost(mUser.getAuth().getId(), isChecked, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean isActive) {
                            if (isActive) {
                                //Snackbar.make(getActivity().findViewById(android.R.id.content), "Activated successfully!", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Did not active this post unsuccessfully!", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }

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
            if (v.getId() == R.id.item_setting_button) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new SettingFragment());
            }
            if (v.getId() == R.id.item_terms_button) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new TermConditionFragment());
            }
            if (v.getId() == R.id.item_about_button) {
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new AboutFragment());
            }
            if (v.getId() == R.id.item_sign_out_button) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        }
    }

}