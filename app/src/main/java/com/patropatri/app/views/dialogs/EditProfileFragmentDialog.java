package com.patropatri.app.views.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.patropatri.app.R;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.viewmodels.UserViewModel;

import java.util.Objects;

public class EditProfileFragmentDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_dialog, container, false);

        //-----------------------------------------------| Session
        EditText itemSummary = (EditText) view.findViewById(R.id.item_summary);
        EditText itemFullName = (EditText) view.findViewById(R.id.item_full_name);
        Spinner itemGender = (Spinner) view.findViewById(R.id.item_gender);
        EditText itemBirth = (EditText) view.findViewById(R.id.item_birth);
        EditText itemHeight = (EditText) view.findViewById(R.id.item_height);
        EditText itemWeight = (EditText) view.findViewById(R.id.item_weight);
        Spinner itemMaritalStatus = (Spinner) view.findViewById(R.id.item_marital_status);
        Spinner itemReligion = (Spinner) view.findViewById(R.id.item_religion);
        Spinner itemComplex = (Spinner) view.findViewById(R.id.item_complex);
        Spinner itemDistrict = (Spinner) view.findViewById(R.id.item_district);

        EditText itemService = (EditText) view.findViewById(R.id.item_service);
        EditText itemDesignation = (EditText) view.findViewById(R.id.item_designation);
        EditText itemCompany = (EditText) view.findViewById(R.id.item_company);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null) {
            int mPosition = getArguments().getInt("POSITION", 0);
            if (mPosition > 0) {
                if (mPosition == 1)
                    ((LinearLayout) view.findViewById(R.id.part_1)).setVisibility(View.VISIBLE);
                if (mPosition == 2)
                    ((LinearLayout) view.findViewById(R.id.part_2)).setVisibility(View.VISIBLE);
            }
        }

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    //===============================================| Remove modal dialog background scrim grey color
    @Override
    public void onResume() {
        super.onResume();
        //getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    //===============================================| Display all full size at first
    private void expandedFullLayout() {
        Objects.requireNonNull(getDialog()).setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                View view = d.findViewById(R.id.design_bottom_sheet);
                if (view != null)
                    BottomSheetBehavior.from(view).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

}