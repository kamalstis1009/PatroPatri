package com.patropatri.app.views.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.Comment;
import com.patropatri.app.models.CommentDto;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.utils.VerticalSpaceItemDecoration;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.CommentAdapter;

import java.util.ArrayList;

public class CommentFragment extends Fragment {

    private ArrayList<CommentDto> mArrayList = new ArrayList<>();
    private CommentAdapter mAdapter;
    private ProgressDialog mProgress;
    private Candidate mModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        EditText mEditText = (EditText) view.findViewById(R.id.comment_text);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            if (mModel != null) {
                mViewModel.getAllCommentsByUserId(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<CommentDto>>() {
                    @Override
                    public void onChanged(ArrayList<CommentDto> arrayList) {
                        if (arrayList != null) {
                            mArrayList.addAll(arrayList);
                        }
                        initRecyclerView(mRecyclerView, mArrayList);
                    }
                });
            }
        }

        ((ImageButton) view.findViewById(R.id.comment_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mModel != null && mUser != null && mUser.getUser() != null) {
                    String message = mEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(message)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Empty comment did not allowed!", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Comment model = new Comment();
                        model.setMessage(message);
                        model.setCreatedBy(mUser.getAuth().getId());
                        model.setUserId(mModel.getId());
                        mProgress = Utility.getInstance().showProgressDialog(getActivity(), getResources().getString(R.string.progress), true);
                        mViewModel.addComment(model, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<Comment>() {
                            @Override
                            public void onChanged(Comment item) {
                                if (item != null) {
                                    CommentDto comment = new CommentDto();
                                    comment.setFullName(mUser.getUser().getFullName());
                                    comment.setImageName(mUser.getUser().getImageName());
                                    comment.setMessage(item.getMessage());
                                    comment.setCreatedAt(item.getCreatedAt());
                                    comment.setId(item.getId());
                                    comment.setUserId(mModel.getId());
                                    if (mAdapter != null) {
                                        mArrayList.add(comment);
                                        mAdapter.notifyItemInserted(mArrayList.size());
                                    }
                                } else {
                                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Unfortunately item could not be store!", Snackbar.LENGTH_SHORT).show();
                                }
                                Utility.getInstance().dismissProgressDialog(mProgress);
                            }
                        });
                    }
                }
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
        mAdapter = new CommentAdapter(getActivity(), arrayList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

}