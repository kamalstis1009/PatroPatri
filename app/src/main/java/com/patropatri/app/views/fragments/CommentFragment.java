package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Comment;
import com.patropatri.app.models.CommentDto;
import com.patropatri.app.models.Family;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.utils.VerticalSpaceItemDecoration;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.CommentAdapter;

import java.util.ArrayList;

public class CommentFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        mViewModel.getAllCommentsByUserId(mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<CommentDto>>() {
            @Override
            public void onChanged(ArrayList<CommentDto> arrayList) {
                if (arrayList != null)
                    initRecyclerView(mRecyclerView, arrayList);
            }
        });

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new CommentFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<CommentDto> arrayList) {
        CommentAdapter mAdapter = new CommentAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}