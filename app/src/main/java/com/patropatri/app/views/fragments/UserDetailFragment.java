package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.patropatri.app.R;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.MessageFragmentDialog;

public class UserDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //-----------------------------------------------| Session
        //User mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        //CampaignViewModel mViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            //ArrayList<MealMenuModel> mArrayList = new Gson().fromJson(getArguments().getString("DATA", null), new TypeToken<ArrayList<MealMenuModel>>(){}.getType());
        }

        onReplaceFragment(requireActivity().getSupportFragmentManager(), new UserAboutFragment());

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new UserDetailFragment());
            }
        });

        ((Button) view.findViewById(R.id.message_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //bundle.putInt("Position", position);
                MessageFragmentDialog mFragment = new MessageFragmentDialog();
                mFragment.setArguments(bundle);
                Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
            }
        });

        ((LinearLayout) view.findViewById(R.id.item_about_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReplaceFragment(requireActivity().getSupportFragmentManager(), new UserAboutFragment());
            }
        });

        ((LinearLayout) view.findViewById(R.id.item_photo_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReplaceFragment(requireActivity().getSupportFragmentManager(), new UserPhotoFragment());
            }
        });

        ((LinearLayout) view.findViewById(R.id.viewed_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onReplaceFragment(requireActivity().getSupportFragmentManager(), new CommentFragment());
            }
        });
        ((LinearLayout) view.findViewById(R.id.liked_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onReplaceFragment(requireActivity().getSupportFragmentManager(), new CommentFragment());
            }
        });

        return view;
    }

    public void onReplaceFragment(FragmentManager mManager, Fragment mFragment){
        mManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, 0)
                .replace(R.id.item_body, mFragment)
                .addToBackStack(null)
                .commit();
    }

}