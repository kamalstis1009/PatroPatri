package com.patropatri.app.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.MessageDto;
import com.patropatri.app.models.Photo;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.viewmodels.FileViewModel;
import com.patropatri.app.viewmodels.NotificationViewModel;
import com.patropatri.app.views.adapters.AttachPhotoAdapter;

import java.util.ArrayList;

public class MessageFragmentDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_dialog, container, false);

        //-----------------------------------------------| findViewById
        EditText editText = (EditText) view.findViewById(R.id.item_message);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        initRecyclerView(mRecyclerView, ConstantKey.getUserPhotos());

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        FileViewModel mFileViewModel = new ViewModelProvider(this).get(FileViewModel.class);
        NotificationViewModel mViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            Candidate mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            ((Button) view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(message)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Empty message did not allowed!", Snackbar.LENGTH_SHORT).show();
                    } else  {
                        if (mModel != null && mUser != null && mUser.getAuth() != null) {
                            MessageDto model = new MessageDto();
                            model.setMessage(message);
                            model.setImageFiles(null);
                            model.setSenderId(mUser.getAuth().getId());
                            model.setReceiverId(mModel.getId());
                            mViewModel.addInbox(model, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<Message>() {
                                @Override
                                public void onChanged(Message item) {
                                    if (item != null)
                                        dismiss();
                                }
                            });
                        }
                    }
                }
            });
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Photo> arrayList) {
        AttachPhotoAdapter mAdapter = new AttachPhotoAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        mAdapter.notifyDataSetChanged();
    }

    //===============================================| Back pressed dismiss
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                dismiss();
            }
        };
    }

    //===============================================| Display all full size at first
    private void expandedFullLayout() {
        getDialog().setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                View bottomSheetInternal = d.findViewById(R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }
}