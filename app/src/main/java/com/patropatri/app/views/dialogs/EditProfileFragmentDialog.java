package com.patropatri.app.views.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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
        EditText mItemSummary = (EditText) view.findViewById(R.id.item_summary);
        EditText mItemFullName = (EditText) view.findViewById(R.id.item_full_name);
        Spinner mItemGender = (Spinner) view.findViewById(R.id.item_gender);
        EditText mItemBirth = (EditText) view.findViewById(R.id.item_birth);
        EditText mItemHeight = (EditText) view.findViewById(R.id.item_height);
        EditText mItemWeight = (EditText) view.findViewById(R.id.item_weight);
        Spinner mItemMaritalStatus = (Spinner) view.findViewById(R.id.item_marital_status);
        Spinner mItemReligion = (Spinner) view.findViewById(R.id.item_religion);
        Spinner mItemComplex = (Spinner) view.findViewById(R.id.item_complex);
        Spinner mItemDistrict = (Spinner) view.findViewById(R.id.item_district);

        EditText mItemService = (EditText) view.findViewById(R.id.item_service);
        EditText mItemDesignation = (EditText) view.findViewById(R.id.item_designation);
        EditText mItemCompany = (EditText) view.findViewById(R.id.item_company);
        EditText mItemCompanyAddress = (EditText) view.findViewById(R.id.item_company_address);
        EditText mItemCompanyWebsite = (EditText) view.findViewById(R.id.item_company_website);

        EditText mItemPhone = (EditText) view.findViewById(R.id.item_phone_number);
        EditText mItemEmail = (EditText) view.findViewById(R.id.item_email);
        EditText mItemPresentAddress = (EditText) view.findViewById(R.id.item_present_address);
        EditText mItemPermanentAddress = (EditText) view.findViewById(R.id.item_permanent_address);

        EditText mItemDegreeName = (EditText) view.findViewById(R.id.item_degree_name);
        EditText mItemSubjectName = (EditText) view.findViewById(R.id.item_subject_name);
        EditText mItemPassingYear = (EditText) view.findViewById(R.id.item_passing_year);
        EditText mItemResult = (EditText) view.findViewById(R.id.item_result);
        EditText mItemInstitute = (EditText) view.findViewById(R.id.item_institute);
        Button mAddEducation = (Button) view.findViewById(R.id.add_education_button);
        RecyclerView mEducationRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        EditText mItemMember_name = (EditText) view.findViewById(R.id.item_member_name);
        EditText mItemRelation = (EditText) view.findViewById(R.id.item_relation);
        EditText mItemOccupation = (EditText) view.findViewById(R.id.item_occupation);
        EditText mItemMemberDetails = (EditText) view.findViewById(R.id.item_member_details);
        Button mAddFamily = (Button) view.findViewById(R.id.add_family_button);
        RecyclerView mFamilyRecyclerView = (RecyclerView) view.findViewById(R.id.items_family_recycler_view);

        EditText mItemInterest = (EditText) view.findViewById(R.id.item_interest);
        EditText mItemOtherActivity = (EditText) view.findViewById(R.id.item_other_activity);
        EditText mItemExpectation = (EditText) view.findViewById(R.id.item_expectation);

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
                if (mPosition == 3)
                    ((LinearLayout) view.findViewById(R.id.part_3)).setVisibility(View.VISIBLE);
                if (mPosition == 4)
                    ((LinearLayout) view.findViewById(R.id.part_4)).setVisibility(View.VISIBLE);
                if (mPosition == 5)
                    ((LinearLayout) view.findViewById(R.id.part_5)).setVisibility(View.VISIBLE);
                if (mPosition == 6)
                    ((LinearLayout) view.findViewById(R.id.part_6)).setVisibility(View.VISIBLE);
                if (mPosition == 7)
                    ((LinearLayout) view.findViewById(R.id.part_7)).setVisibility(View.VISIBLE);
                if (mPosition == 8)
                    ((LinearLayout) view.findViewById(R.id.part_8)).setVisibility(View.VISIBLE);
                if (mPosition == 9)
                    ((LinearLayout) view.findViewById(R.id.part_9)).setVisibility(View.VISIBLE);
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