package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.UserAdapter;
import com.patropatri.app.views.dialogs.FilterFragmentDialog;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements FilterFragmentDialog.MyListener {

    private ProgressBar mProgressBar;
    private ArrayList<Candidate> mArrayList = new ArrayList<>();
    private UserAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private boolean isLoading = true;
    private int mInitPageNo = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //-----------------------------------------------| findViewById
        mProgressBar = view.findViewById(R.id.progress_bar);
        EditText editText = (EditText) view.findViewById(R.id.search_item);
        ImageButton searchButton = (ImageButton) view.findViewById(R.id.search_button);
        ImageButton filterButton = (ImageButton) view.findViewById(R.id.filter_button);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            //ArrayList<MealMenuModel> mArrayList = new Gson().fromJson(getArguments().getString("DATA", null), new TypeToken<ArrayList<MealMenuModel>>(){}.getType());
            //mModel = new Gson().fromJson(getArguments().getString("DATA", null), Answered.class);
            //int mIntId = getArguments().getInt("INTERVIEW_ID", 0);
        }

        //-----------------------------------------------| Search Disable Focus
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //-----------------------------------------------| Candidates
        mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        if (mUser != null && mUser.getAuth() != null) {
            Utility.getInstance().onVisibleProgressBar(mProgressBar);
            mViewModel.getAllCandidatesByPaginated(mInitPageNo, 5, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Candidate>>() {
                @Override
                public void onChanged(ArrayList<Candidate> arrayList) {
                    if (arrayList != null) {
                        mArrayList.addAll(arrayList);
                    }
                    initRecyclerView(mRecyclerView, mArrayList);
                    Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                }
            });
        }

        //------------------------------------------------| Scroll-down load more data
        if (getActivity() != null) {
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (dy > 0) {
                        //check for scroll down
                        visibleItemCount = mLayoutManager != null ? mLayoutManager.getChildCount() : 0;
                        totalItemCount = mLayoutManager != null ? mLayoutManager.getItemCount() : 0;
                        pastVisibleItems = mLayoutManager != null ? mLayoutManager.findFirstVisibleItemPosition() : 0;

                        if (isLoading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                isLoading = false;
                                mInitPageNo++;
                                //Log.v("...", "Last Item Wow !");
                                // Do pagination.. i.e. fetch new data
                                if (mUser != null && mUser.getAuth() != null) {
                                    Utility.getInstance().onVisibleProgressBar(mProgressBar);
                                    mViewModel.getAllCandidatesByPaginated(mInitPageNo, 5, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Candidate>>() {
                                        @Override
                                        public void onChanged(ArrayList<Candidate> arrayList) {
                                            if (arrayList != null) {
                                                mArrayList.addAll(arrayList);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                            Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                                        }
                                    });
                                }
                                isLoading = true;
                            }
                        }
                    }
                }
            });
        }

        (filterButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onShowFilterDialog();
                    }
                });
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

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Candidate> arrayList) {
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

    private void onShowFilterDialog() {
        Bundle bundle = new Bundle();
        //bundle.putInt("Position", position);
        FilterFragmentDialog mFragment = new FilterFragmentDialog(this);
        mFragment.setArguments(bundle);
        Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
    }

    @Override
    public void onAddItems(ArrayList<Candidate> arrayList) {
        if (mArrayList != null && mArrayList.size() > 0) {
            mArrayList.clear();
            mArrayList.addAll(arrayList);
            initRecyclerView(mRecyclerView, arrayList);
            if (mAdapter != null) {
                //mAdapter.notifyItemInserted(mArrayList.size());
            }
        }
    }
}