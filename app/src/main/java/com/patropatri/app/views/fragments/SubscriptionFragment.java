package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Subscription;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.SubscriptionAdapter;

import java.util.ArrayList;

public class SubscriptionFragment extends Fragment implements SubscriptionAdapter.CallBackListener {

    private ProgressBar mProgressBar;
    private ArrayList<Subscription> mArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);

        //-----------------------------------------------| findViewById
        mProgressBar = view.findViewById(R.id.progress_bar);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_items);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        if (getActivity() != null && mUser != null) {
            Utility.getInstance().onVisibleProgressBar(mProgressBar);
            mViewModel.getAllSubscriptions("Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Subscription>>() {
                @Override
                public void onChanged(ArrayList<Subscription> arrayList) {
                    if (arrayList != null) {
                        mArrayList.addAll(arrayList);
                    }
                    initRecyclerView(mRecyclerView, mArrayList);
                    Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                }
            });
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new SubscriptionFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Subscription> arrayList) {
        SubscriptionAdapter mAdapter = new SubscriptionAdapter(getActivity(), arrayList, this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddItem(Subscription model) {
        Bundle bundle = new Bundle();
        bundle.putString("DATA", new Gson().toJson(model));
        PaymentSuccessFragment mFragment = new PaymentSuccessFragment();
        mFragment.setArguments(bundle);
        Utility.getInstance().onAddFragment(requireActivity().getSupportFragmentManager(), mFragment);
    }
}
