package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.utils.StompUtils;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.ChatAttachAdapter;
import com.patropatri.app.views.adapters.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.reactivex.functions.Consumer;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class MessageFragment extends Fragment implements MessageAdapter.CallBackListener {

    private static final String TAG = "MessageFragment";

    private EditText editText;
    private Inbox mModel;
    private MessageAdapter mAdapter;
    private ArrayList<Message> mArrayList = new ArrayList<>();

    private String youChatId = String.valueOf(new Random().nextInt(100));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //-----------------------------------------------| findViewById
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        RecyclerView mRecyclerView2 = (RecyclerView) view.findViewById(R.id.items_attach_recycler_view);
        editText = (EditText) view.findViewById(R.id.sent_chat_text);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| StompClient Connect
        // Start connecting to server
        StompClient mClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, ConstantKey.SOCKET_SERVER_URL);
        mClient.connect();
        StompUtils.lifecycle(mClient); //Connection callback

        // Subscribe chat endpoint to receive response by RX Java
        if (getActivity() != null && mModel != null && mUser != null) {
            mClient.topic(ConstantKey.CHAT_RESPONSE.replace(ConstantKey.PLACEHOLDER, mUser.getAuth().getId() + "")).subscribe(new Consumer<StompMessage>() {
                @Override
                public void accept(StompMessage message) throws Exception {
                    JSONObject json = new JSONObject(message.getPayload());
                    Log.i(TAG, "Receive: " + json.toString());

                    Message model = new Message();
                    model.setMessage(json.getString("message"));
                    model.setImageFiles(new String[]{json.getString("imageFiles")});
                    model.setSenderId(Long.parseLong(json.getString("senderId")));
                    model.setReceiverId(Long.parseLong(json.getString("receiverId")));
                    model.setIsEnabled(Boolean.parseBoolean(json.getString("isEnabled")));
                    model.setInboxId(Long.parseLong(json.getString("inboxId")));
                    model.setCreatedAt(new Date(json.getString("createdAt")));
                    if (mAdapter != null) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                    }
                }
            });
        }

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

        //-----------------------------------------------| Sent Message
        ((ImageButton) view.findViewById(R.id.send_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString().trim();
                if (mModel != null && mUser != null) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("message", message);
                        json.put("imageFiles", new JSONArray());
                        json.put("senderId", mUser.getAuth().getId());
                        json.put("receiverId", mModel.getSenderId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mClient.send(ConstantKey.CHAT, json.toString()).subscribe();

                    Message model = new Message();
                    model.setMessage(message);
                    model.setImageFiles(null);
                    model.setSenderId(mUser.getAuth().getId());
                    model.setReceiverId(mModel.getSenderId());
                    model.setIsEnabled(true);
                    model.setInboxId(mModel.getId());
                    model.setCreatedAt(new Date());
                    if (mAdapter != null) {
                        mArrayList.add(model);
                        mAdapter.notifyItemInserted(mArrayList.size());
                    }
                }
            }
        });

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new MessageFragment());
            }
        });

        return view;
    }

    @Override
    public void onAddItem(Message model) {

    }

    @Override
    public void onRecyclerViewItems(RecyclerView recyclerView, String [] arrayList) {
        initImageRecyclerView(recyclerView, arrayList);
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Message> arrayList, Inbox inbox) {
        mAdapter = new MessageAdapter(getActivity(), arrayList, inbox, this);
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
        ChatAttachAdapter adapter = new ChatAttachAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        adapter.notifyDataSetChanged();
    }
}
