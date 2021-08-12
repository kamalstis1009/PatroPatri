package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Comment;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.utils.VerticalSpaceItemDecoration;
import com.patropatri.app.views.adapters.CommentAdapter;

import java.util.ArrayList;

public class CommentFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        initRecyclerView(mRecyclerView, ConstantKey.getComments());

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new CommentFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<Comment> arrayList) {
        CommentAdapter mAdapter = new CommentAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}