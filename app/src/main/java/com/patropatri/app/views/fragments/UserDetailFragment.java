package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.MessageFragmentDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailFragment extends Fragment {

    private Candidate mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            if (mModel != null) {
                Picasso.get().load(ConstantKey.RESOURCE_URL + mModel.getImageName() ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into( ((CircleImageView) view.findViewById(R.id.item_image)) );

                try {
                    ((TextView) view.findViewById(R.id.item_date)).setText(Utility.getInstance().getFormatFromStringDate("dd MMM yyyy", mModel.getCreatedAt()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((TextView) view.findViewById(R.id.item_comment)).setText(mModel.getCommentAmount() + "");
                ((TextView) view.findViewById(R.id.item_liked)).setText(mModel.getLikedAmount() + "");

                ((TextView) view.findViewById(R.id.item_title)).setText(mModel.getFullName());
                ((TextView) view.findViewById(R.id.item_address_designation)).setText(mModel.getProfession() + " | " + mModel.getDistrict());

                Bundle bundle = new Bundle();
                bundle.putString("DATA", new Gson().toJson(mModel));
                UserAboutFragment mFragment = new UserAboutFragment();
                mFragment.setArguments(bundle);
                onReplaceFragment(requireActivity().getSupportFragmentManager(), mFragment);
            }
        }

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
                bundle.putString("DATA", new Gson().toJson(mModel));
                MessageFragmentDialog mFragment = new MessageFragmentDialog();
                mFragment.setArguments(bundle);
                Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
            }
        });

        ((LinearLayout) view.findViewById(R.id.item_about_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DATA", new Gson().toJson(mModel));
                    UserAboutFragment mFragment = new UserAboutFragment();
                    mFragment.setArguments(bundle);
                    onReplaceFragment(requireActivity().getSupportFragmentManager(), mFragment);
                }
            }
        });

        ((LinearLayout) view.findViewById(R.id.item_photo_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DATA", new Gson().toJson(mModel));
                    UserPhotoFragment mFragment = new UserPhotoFragment();
                    mFragment.setArguments(bundle);
                    onReplaceFragment(requireActivity().getSupportFragmentManager(), mFragment);
                }
            }
        });

        ((LinearLayout) view.findViewById(R.id.show_comment_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("DATA", new Gson().toJson(mModel));
                    CommentFragment mFragment = new CommentFragment();
                    mFragment.setArguments(bundle);
                    Utility.getInstance().onReplaceFragment(requireActivity().getSupportFragmentManager(), mFragment);
                }
            }
        });
        ((LinearLayout) view.findViewById(R.id.show_liked_button)).setOnClickListener(new View.OnClickListener() {
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