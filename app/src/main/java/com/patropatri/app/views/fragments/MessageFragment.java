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
import com.patropatri.app.models.ChatMessage;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //-----------------------------------------------| findViewById
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        //mChatText = (EditText) view.findViewById(R.id.chat_text);
        //mAttachImg = (ImageView) view.findViewById(R.id.attach_image);

        //-----------------------------------------------| Session
        //mUser = new Gson().fromJson(Prefs.getString(ConstantKey.USER_MODEL, null), UserModel.class);

        //-----------------------------------------------| Receive and observe the data from view model
        //mViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        ArrayList<ChatMessage> mArrayList = new ArrayList<>();
        mArrayList.add(new ChatMessage(true, null, "Hi!", null));
        mArrayList.add(new ChatMessage(false, null, "Hi!", null));
        mArrayList.add(new ChatMessage(true, null, "How are you?", null));
        mArrayList.add(new ChatMessage(false, null, "I'm well, You?", null));
        initRecyclerView(mRecyclerView, mArrayList);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null) {
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new MessageFragment());
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<ChatMessage> mArrayList) {
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
