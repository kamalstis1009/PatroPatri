package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.adapters.UserAdapter;
import com.patropatri.app.views.dialogs.FilterFragmentDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private UserAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //-----------------------------------------------| findViewById
        EditText editText = (EditText) view.findViewById(R.id.search_item);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search_button);
        ImageButton filterButton = (ImageButton) view.findViewById(R.id.filter_button);

        //-----------------------------------------------| Session
        //User mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        //CampaignViewModel mViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            //ArrayList<MealMenuModel> mArrayList = new Gson().fromJson(getArguments().getString("DATA", null), new TypeToken<ArrayList<MealMenuModel>>(){}.getType());
        }

        //-----------------------------------------------| Search Disable Focus
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //-----------------------------------------------| Category
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        initRecyclerView(mRecyclerView, ConstantKey.getUsers());
        /*mProductViewModel.getAllProductCategory().observe(getActivity(), new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> arrayList) {
                initRecyclerView(mRecyclerView0, arrayList);
            }
        });*/

        (filterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //bundle.putInt("Position", position);
                FilterFragmentDialog mFragment = new FilterFragmentDialog();
                mFragment.setArguments(bundle);
                Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
            }
        });

        (searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getVisibility() == View.VISIBLE) {
                    searchButton.setImageResource(R.drawable.ic_baseline_search_24);
                    editText.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_left_out));
                    editText.setVisibility(View.GONE);
                } else {
                    searchButton.setImageResource(R.drawable.ic_baseline_close_24);
                    editText.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_right_in));
                    editText.setVisibility(View.VISIBLE);
                }
            }
        });

        (editText).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true  /* enabled by default */ ) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button even
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<User> arrayList) {
        mAdapter = new UserAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(45 /*px spacing*/));
        mAdapter.notifyDataSetChanged();
    }

    private void showSearchDialog() {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_search, null, false);
            builder.setView(view);
            builder.setCancelable(true);
            builder.create();
            final AlertDialog dialog = builder.show();
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            ((EditText) view.findViewById(R.id.search_item)).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(s);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
            /*((Button) view.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });*/
        }
    }

}