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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.NotificationAdapter;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private ProgressBar mProgressBar;
    private NotificationAdapter mAdapter;
    private ArrayList<Notification> mArrayList = new ArrayList<>();

    private boolean isLoading = true;
    private int mInitPageNo = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_items);
        if (getActivity() != null && mUser != null && mUser.getAuth() != null) {
            Utility.getInstance().onVisibleProgressBar(mProgressBar);
            mViewModel.getAllNotificationsByUserId(mInitPageNo, 15, mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Notification>>() {
                @Override
                public void onChanged(ArrayList<Notification> arrayList) {
                    if (arrayList != null) {
                        mArrayList.addAll(arrayList);
                    }
                    initRecyclerView(mRecyclerView, mArrayList);
                    Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                }
            });
        }

        if (getActivity() != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (dy > 0) {
                        //check for scroll down
                        visibleItemCount = mLayoutManager != null ? mLayoutManager.getChildCount() : 0;
                        totalItemCount = mLayoutManager != null ? mLayoutManager.getItemCount() : 0;
                        pastVisibleItems = mLayoutManager != null ? mLayoutManager.findFirstVisibleItemPosition() : 0;

                        if (isLoading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                isLoading = false;
                                mInitPageNo++;
                                //Log.v("...", "Last Item Wow !");
                                // Do pagination.. i.e. fetch new data
                                if (mUser != null && mUser.getAuth() != null) {
                                    Utility.getInstance().onVisibleProgressBar(mProgressBar);
                                    mViewModel.getAllNotificationsByUserId(mInitPageNo, 15, mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Notification>>() {
                                        @Override
                                        public void onChanged(ArrayList<Notification> arrayList) {
                                            if (arrayList != null) {
                                                mArrayList.addAll(arrayList);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                            Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                                        }
                                    });
                                }
                                isLoading = true;
                            }
                        }
                    }
                }
            });
        }


        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new NotificationFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Notification> arrayList) {
        mAdapter = new NotificationAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}