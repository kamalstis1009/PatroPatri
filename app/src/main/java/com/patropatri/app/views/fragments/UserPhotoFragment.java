package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.Gallery;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.PhotoAdapter;

import java.util.ArrayList;

public class UserPhotoFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_photo, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //-----------------------------------------------| findViewById
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            Candidate mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            if (mModel != null) {
                mViewModel.getGalleriesById(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Gallery>>() {
                    @Override
                    public void onChanged(ArrayList<Gallery> arrayList) {
                        if (arrayList != null)
                            initRecyclerView(mRecyclerView, arrayList);
                    }
                });
            }
        }

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Gallery> arrayList) {
        PhotoAdapter mAdapter = new PhotoAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        mAdapter.notifyDataSetChanged();
    }

}