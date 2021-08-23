package com.patropatri.app.views.fragments;

import android.graphics.PorterDuff;
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
import com.patropatri.app.models.Liked;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.MessageAdapter;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private ArrayList<Message> mArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //-----------------------------------------------| findViewById
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        //mChatText = (EditText) view.findViewById(R.id.chat_text);
        //mAttachImg = (ImageView) view.findViewById(R.id.attach_image);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null) {
        }

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        if (mUser != null && getActivity() != null) {
            mViewModel.getAllMessages(1,5, mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Message>>() {
                @Override
                public void onChanged(ArrayList<Message> arrayList) {
                    if (arrayList != null) {
                        mArrayList.addAll(arrayList);
                    }
                    initRecyclerView(mRecyclerView, mArrayList);
                }
            });
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new MessageFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<Message> mArrayList) {
        MessageAdapter mAdapter = new MessageAdapter(getActivity(), mArrayList);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(mItems.size() - 1);
        mRecyclerView.scrollToPosition(mArrayList.size() - 1);
    }
}
