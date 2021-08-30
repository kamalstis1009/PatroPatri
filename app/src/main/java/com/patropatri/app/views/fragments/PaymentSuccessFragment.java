package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Subscription;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

import java.util.Objects;

public class PaymentSuccessFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.exit_to_left, 0).remove(new PaymentSuccessFragment()).addToBackStack("tag").commit(); // without FrameLayout
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_success, container, false);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            Subscription mModel = new Gson().fromJson(getArguments().getString("DATA", null), Subscription.class);

            if (getActivity() != null && mUser != null && mUser.getAuth() != null) {
                mViewModel.updateUserPost(mUser.getAuth().getId(), mUser.getUser().getIsActive(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<Boolean>() {
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

        ((Button) view.findViewById(R.id.check_order_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new PaymentSuccessFragment());
                Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), new AccountFragment());
            }
        });

        return view;
    }


}
