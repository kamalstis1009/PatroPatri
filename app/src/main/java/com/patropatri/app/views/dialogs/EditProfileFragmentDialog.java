package com.patropatri.app.views.dialogs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class EditProfileFragmentDialog extends BottomSheetDialogFragment {

    private static final String TAG = "EditProfileFragment";

    private int mPosition;
    private ProgressDialog mProgress;

    private UserWithToken mUser;
    private UserViewModel mViewModel;

    private BottomSheetListener mListener;

    public interface BottomSheetListener {
        void onAddItem(User model);
    }

    public EditProfileFragmentDialog(BottomSheetListener listener) {
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_dialog, container, false);

        //-----------------------------------------------| findViewById
        EditText mItemFullName = (EditText) view.findViewById(R.id.item_full_name);
        Spinner mItemGender = (Spinner) view.findViewById(R.id.item_gender);
        EditText mItemBirth = (EditText) view.findViewById(R.id.item_birth);
        EditText mItemBirthPlace = (EditText) view.findViewById(R.id.item_birth_place);
        EditText mItemHeight = (EditText) view.findViewById(R.id.item_height);
        EditText mItemWeight = (EditText) view.findViewById(R.id.item_weight);
        Spinner mItemMaritalStatus = (Spinner) view.findViewById(R.id.item_marital_status);
        Spinner mItemReligion = (Spinner) view.findViewById(R.id.item_religion);
        Spinner mItemComplex = (Spinner) view.findViewById(R.id.item_complex);
        Spinner mItemDistrict = (Spinner) view.findViewById(R.id.item_district);

        Spinner mItemService = (Spinner) view.findViewById(R.id.item_service);
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
        RecyclerView mEducationRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);

        EditText mItemMemberName = (EditText) view.findViewById(R.id.item_member_name);
        EditText mItemRelation = (EditText) view.findViewById(R.id.item_relation);
        EditText mItemOccupation = (EditText) view.findViewById(R.id.item_occupation);
        EditText mItemMemberDetails = (EditText) view.findViewById(R.id.item_member_details);
        RecyclerView mFamilyRecyclerView = (RecyclerView) view.findViewById(R.id.items_family_recycler_view);

        EditText mItemSummary = (EditText) view.findViewById(R.id.item_summary);
        EditText mItemInterest = (EditText) view.findViewById(R.id.item_interest);
        EditText mItemOtherActivity = (EditText) view.findViewById(R.id.item_other_activity);
        EditText mItemExpectation = (EditText) view.findViewById(R.id.item_expectation);

        //-----------------------------------------------| Session
        mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null) {
            mPosition = getArguments().getInt("POSITION", 0);
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

        mItemBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().getDatePickerDialog(new Utility.DatePickerListener() {
                    @Override
                    public void onDate(String date) {
                        mItemBirth.setText(date);
                    }
                }, getActivity());
            }
        });

        ((Button) view.findViewById(R.id.add_education_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == 5) {
                    String degree = mItemDegreeName.getText().toString().trim(),
                            subject = mItemSubjectName.getText().toString().trim(),
                            passingYear = mItemPassingYear.getText().toString().trim(),
                            result = mItemResult.getText().toString().trim(),
                            institute = mItemInstitute.getText().toString().trim();
                    //
                }
            }
        });

        ((Button) view.findViewById(R.id.add_family_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition == 6) {
                    String memberName = mItemMemberName.getText().toString().trim(),
                            relation = mItemRelation.getText().toString().trim(),
                            occupation = mItemOccupation.getText().toString().trim(),
                            memberDetails = mItemMemberDetails.getText().toString().trim();
                    //
                }
            }
        });

        ((Button) view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null && mUser.getAuth() != null) {
                    User model = new User();
                    model.setId(mUser.getAuth().getId());

                    if (mPosition == 1) {
                        String summary = mItemSummary.getText().toString().trim();
                        model.setSummary(summary);
                        storeUser(model);
                    }
                    if (mPosition == 2) {
                        String fullName = mItemFullName.getText().toString().trim(),
                                gender = mItemGender.getSelectedItem().toString().trim(),
                                birth = mItemBirth.getText().toString().trim(),
                                place = mItemBirthPlace.getText().toString().trim(),
                                height = mItemHeight.getText().toString().trim(),
                                weight = mItemWeight.getText().toString().trim(),
                                status = mItemMaritalStatus.getSelectedItem().toString().trim(),
                                religion = mItemReligion.getSelectedItem().toString().trim(),
                                complex = mItemComplex.getSelectedItem().toString().trim(),
                                district = mItemDistrict.getSelectedItem().toString().trim();
                        //name,birth,status,
                        model.setFullName(fullName);
                        model.setGender(gender);
                        model.setBirthDate(Utility.getInstance().getDateFromStringDate(birth));
                        model.setBirthPlace(place);
                        model.setHeight(height);
                        model.setWeight(weight);
                        model.setMaritalStatus(status);
                        model.setReligion(religion);
                        model.setComplex(complex);
                        model.setDistrict(district);
                        storeUser(model);
                    }
                    if (mPosition == 3) {
                        String service = mItemService.getSelectedItem().toString().trim(),
                                designation = mItemDesignation.getText().toString().trim(),
                                company = mItemCompany.getText().toString().trim(),
                                address = mItemCompanyAddress.getText().toString().trim(),
                                website = mItemCompanyWebsite.getText().toString().trim();
                        model.setProfession(service);
                        model.setDesignation(designation);
                        model.setCompany(company);
                        model.setCompanyAddress(address);
                        model.setCompanyWebsite(website);
                        storeUser(model);
                    }
                    if (mPosition == 4) {
                        String phone = mItemPhone.getText().toString().trim(),
                                email = mItemEmail.getText().toString().trim(),
                                present = mItemPresentAddress.getText().toString().trim(),
                                permanent = mItemPermanentAddress.getText().toString().trim();
                        model.setPhoneNumber(phone);
                        model.setEmail(email);
                        model.setPresentAddress(present);
                        model.setPermanentAddress(permanent);
                        storeUser(model);
                    }
                    if (mPosition == 7) {
                        String interest = mItemInterest.getText().toString().trim();
                        model.setInterest(interest);
                        storeUser(model);
                    }
                    if (mPosition == 8) {
                        String otherActivity = mItemOtherActivity.getText().toString().trim();
                        model.setOtherActivity(otherActivity);
                        storeUser(model);
                    }
                    if (mPosition == 9) {
                        String expectation = mItemExpectation.getText().toString().trim();
                        model.setExpectation(expectation);
                        storeUser(model);
                    }
                    /*if (TextUtils.isEmpty(summary)) {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Empty question id did not allowed!", Snackbar.LENGTH_SHORT).show();
                    }*/
                }
            }
        });

        return view;
    }

    private void storeUser(User model) {
        if (getActivity() != null && mUser != null) {
            mProgress = Utility.getInstance().showProgressDialog(getActivity(), getResources().getString(R.string.progress), true);
            mViewModel.updateUser(model, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User item) {
                    if (item != null) {
                        mUser.setUser(item);
                        SharedPefManager.getInstance(getActivity()).saveUser(mUser);
                        if (mListener != null) {
                            mListener.onAddItem(item);
                        }
                        dismiss();
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Did not save unsuccessfully!", Snackbar.LENGTH_SHORT).show();
                    }
                    Utility.getInstance().dismissProgressDialog(mProgress);
                }
            });
        }
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