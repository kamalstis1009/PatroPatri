package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.patropatri.app.R;
import com.patropatri.app.repositories.WebSocketClient;

import org.json.JSONException;
import org.json.JSONObject;

import ua.naiksoftware.stomp.StompClient;

public class DemoFragment extends Fragment  {

    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);

        //-----------------------------------------------| findViewById
        mProgressBar = view.findViewById(R.id.progress_bar);
        EditText itemTitle = (EditText) view.findViewById(R.id.item_title);
        Spinner itemType = (Spinner) view.findViewById(R.id.item_type);

        //-----------------------------------------------| Session
        //mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        //QuestionViewModel mViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null) {
            /*
            //mArrayList = new Gson().fromJson(getArguments().getString("PRODUCT", null), new TypeToken<ArrayList<Product>>() {}.getType());
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Answered.class);
            if (mModel != null && getActivity() != null) {
                Utility.getInstance().onVisibleProgressBar(mProgressBar);
                mViewModel.getAllQuestionCategoryByChecklistId(mModel.getChecklistId()).observe(getActivity(), new Observer<ArrayList<QuestionCategory>>() {
                    @Override
                    public void onChanged(ArrayList<QuestionCategory> items) {
                        if (items != null) {
                            mArrayList.addAll(items);
                        }
                        initRecyclerView(mRecyclerView, items);
                        Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                    }
                });
            }
            */
        }

        //-----------------------------------------------| StompClient Connect
        StompClient mClient = WebSocketClient.getInstance().getStompClient();

        ((Button) view.findViewById(R.id.send_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = itemTitle.getText().toString().trim(),
                        type = itemType.getSelectedItem().toString().trim();
                JSONObject json = new JSONObject();
                try {
                    json.put("title", title);
                    json.put("type", type);
                    json.put("senderId", 1L);
                    json.put("receiverId", 2L);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mClient.send("/specific-user", json.toString()).subscribe();
            }
        });

        return view;
    }
}
