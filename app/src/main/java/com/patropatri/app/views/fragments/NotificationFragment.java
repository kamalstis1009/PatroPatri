package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.User;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.views.adapters.NotificationAdapter;
import com.patropatri.app.views.adapters.UserAdapter;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_items);
        initRecyclerView(mRecyclerView, ConstantKey.getNotifications());

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Notification> arrayList) {
        NotificationAdapter mAdapter = new NotificationAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}