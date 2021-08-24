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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.ChatAttachAdapter;
import com.patropatri.app.views.adapters.MessageAdapter;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private Inbox mModel;
    private ArrayList<Message> mArrayList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //-----------------------------------------------| findViewById
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        //mChatText = (EditText) view.findViewById(R.id.sent_chat_text);
        //mAttachImg = (ImageView) view.findViewById(R.id.attach_image);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Inbox.class);

            if (getActivity() != null && mModel != null && mUser != null) {
                mViewModel.getAllMessages(1,5, mUser.getAuth().getId(), mModel.getSenderId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Message>>() {
                    @Override
                    public void onChanged(ArrayList<Message> arrayList) {
                        if (arrayList != null) {
                            mArrayList.addAll(arrayList);
                        }
                        initRecyclerView(mRecyclerView, mArrayList, mModel);
                    }
                });
            }
        }

        ((ImageButton) view.findViewById(R.id.attach_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        ((ImageButton) view.findViewById(R.id.send_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new MessageFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Message> arrayList, Inbox inbox) {
        MessageAdapter mAdapter = new MessageAdapter(getActivity(), arrayList, inbox);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(mItems.size() - 1);
        recyclerView.scrollToPosition(mArrayList.size() - 1);
    }

    private void initImageRecyclerView(RecyclerView recyclerView, String [] arrayList) {
        ChatAttachAdapter mAdapter = new ChatAttachAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        mAdapter.notifyDataSetChanged();
    }
}
