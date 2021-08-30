package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.LikedDto;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.utils.VerticalSpaceItemDecoration;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.LikedAdapter;

import java.util.ArrayList;

public class LikedFragment extends Fragment {

    private ArrayList<LikedDto> mArrayList = new ArrayList<>();
    private LikedAdapter mAdapter;
    private Candidate mModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked, container, false);

        //-----------------------------------------------| findViewById
        ProgressBar mProgressBar = view.findViewById(R.id.progress_bar);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);
            int mPosition = getArguments().getInt("POSITION", 0);

            if (mModel != null) {
                if (mPosition == 1) {
                    Utility.getInstance().onVisibleProgressBar(mProgressBar);
                    mViewModel.getAllLikesByUserId(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<LikedDto>>() {
                        @Override
                        public void onChanged(ArrayList<LikedDto> arrayList) {
                            if (arrayList != null) {
                                mArrayList.addAll(arrayList);
                            }
                            initRecyclerView(mRecyclerView, mArrayList);
                            Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                        }
                    });
                }
                if (mPosition == 2) {
                    Utility.getInstance().onVisibleProgressBar(mProgressBar);
                    mViewModel.getAllViewersByUserId(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<LikedDto>>() {
                        @Override
                        public void onChanged(ArrayList<LikedDto> arrayList) {
                            if (arrayList != null) {
                                mArrayList.addAll(arrayList);
                            }
                            initRecyclerView(mRecyclerView, mArrayList);
                            Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                        }
                    });
                }
            }
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new LikedFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<LikedDto> arrayList) {
        mAdapter = new LikedAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}