package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.patropatri.app.R;

public class AboutFragment extends Fragment  {

    //private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        //-----------------------------------------------| findViewById
        //mProgressBar = view.findViewById(R.id.progress_bar);

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

        return view;
    }
}
