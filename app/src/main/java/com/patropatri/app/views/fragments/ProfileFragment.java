package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.views.dialogs.EditProfileFragmentDialog;

import java.util.Calendar;
import java.util.Date;

public class ProfileFragment extends Fragment implements EditProfileFragmentDialog.BottomSheetListener {

    private TextView mItemName, mItemGender, mItemBirth, mItemAge, mIemHeight, mItemWeight, mItemMarital, mItemReligion, mItemComplex, mItemDistrict, mItemProfession, mItemDesignation, mItemCompany, mItemCompanyAddress, mItemWebsite, mItemPhone, mItemEmail, mItemPresent, mItemPermanent, mItemSummary, mItemInterest, mItemActivity, mItemExpectation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mItemName = (TextView) view.findViewById(R.id.item_name);
        mItemGender = (TextView) view.findViewById(R.id.item_gender);
        mItemBirth = (TextView) view.findViewById(R.id.item_birth);
        mItemAge = (TextView) view.findViewById(R.id.item_age);
        mIemHeight = (TextView) view.findViewById(R.id.item_height);
        mItemWeight = (TextView) view.findViewById(R.id.item_weight);
        mItemMarital = (TextView) view.findViewById(R.id.item_marital_status);
        mItemReligion = (TextView) view.findViewById(R.id.item_religion);
        mItemComplex = (TextView) view.findViewById(R.id.item_complex);
        mItemDistrict = (TextView) view.findViewById(R.id.item_district);

        mItemProfession = (TextView) view.findViewById(R.id.item_profession);
        mItemDesignation = (TextView) view.findViewById(R.id.item_designation);
        mItemCompany = (TextView) view.findViewById(R.id.item_company);
        mItemCompanyAddress = (TextView) view.findViewById(R.id.item_company_address);
        mItemWebsite = (TextView) view.findViewById(R.id.item_website);

        mItemPhone = (TextView) view.findViewById(R.id.item_phone);
        mItemEmail = (TextView) view.findViewById(R.id.item_email);
        mItemPresent = (TextView) view.findViewById(R.id.item_present_address);
        mItemPermanent = (TextView) view.findViewById(R.id.item_permanent_address);

        mItemSummary = (TextView) view.findViewById(R.id.item_summary);
        mItemInterest = (TextView) view.findViewById(R.id.item_interest);
        mItemActivity = (TextView) view.findViewById(R.id.item_other_activity);
        mItemExpectation = (TextView) view.findViewById(R.id.item_expectation);

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new ActionHandler());

        ((ImageButton) view.findViewById(R.id.item_summary_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_personal_info_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_profession_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_contact_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_education_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_family_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_interest_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_activity_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_expectation_button)).setOnClickListener(new ActionHandler());

        return view;
    }

    @Override
    public void onAddItem(User model) {
        if (model != null) {
            if (model.getFullName() != null)
                mItemName.setText(model.getFullName());
            if (model.getGender() != null)
                mItemGender.setText(model.getGender());
            if (model.getBirthDate() != null)
                mItemBirth.setText(model.getBirthDate() + "");
            if (model.getBirthDate() != null)
                mItemAge.setText(calculateAge(model.getBirthDate()) + "");
            if (model.getHeight() != null)
                mIemHeight.setText(model.getHeight());
            if (model.getWeight() != null)
                mItemWeight.setText(model.getWeight());
            if (model.getMaritalStatus() != null)
                mItemMarital.setText(model.getMaritalStatus());
            if (model.getReligion() != null)
                mItemReligion.setText(model.getReligion());
            if (model.getComplex() != null)
                mItemComplex.setText(model.getComplex());
            if (model.getDistrict() != null)
                mItemDistrict.setText(model.getDistrict());

            if (model.getProfession() != null)
                mItemProfession.setText(model.getProfession());
            if (model.getDesignation() != null)
                mItemDesignation.setText(model.getDesignation());
            if (model.getCompany() != null)
                mItemCompany.setText(model.getCompany());
            if (model.getCompanyAddress() != null)
                mItemCompanyAddress.setText(model.getCompanyAddress());
            if (model.getCompanyWebsite() != null)
                mItemWebsite.setText(model.getCompanyWebsite());

            if (model.getPhoneNumber() != null)
                mItemPhone.setText(model.getPhoneNumber());
            if (model.getEmail() != null)
                mItemEmail.setText(model.getEmail());
            if (model.getPresentAddress() != null)
                mItemPresent.setText(model.getPresentAddress());
            if (model.getPermanentAddress() != null)
                mItemPermanent.setText(model.getPermanentAddress());

            if (model.getSummary() != null)
                mItemSummary.setText(model.getSummary());
            if (model.getInterest() != null)
                mItemInterest.setText(model.getInterest());
            if (model.getOtherActivity() != null)
                mItemActivity.setText(model.getOtherActivity());
            if (model.getExpectation() != null)
                mItemExpectation.setText(model.getExpectation());
        }
    }

    public int calculateAge(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        int i = 0;
        while (cal1.before(cal2)) {
            cal1.add(Calendar.YEAR, 1);
            i += 1;
        }
        return i;
    }

    private class ActionHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.back_button) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new ProfileFragment());
            }
            if (v.getId() == R.id.item_summary_button) {
                onShowDialog(1);
            }
            if (v.getId() == R.id.item_personal_info_button) {
                onShowDialog(2);
            }
            if (v.getId() == R.id.item_profession_button) {
                onShowDialog(3);
            }
            if (v.getId() == R.id.item_contact_button) {
                onShowDialog(4);
            }
            if (v.getId() == R.id.item_education_button) {
                onShowDialog(5);
            }
            if (v.getId() == R.id.item_family_button) {
                onShowDialog(6);
            }
            if (v.getId() == R.id.item_interest_button) {
                onShowDialog(7);
            }
            if (v.getId() == R.id.item_activity_button) {
                onShowDialog(8);
            }
            if (v.getId() == R.id.item_expectation_button) {
                onShowDialog(9);
            }
        }
    }

    private void onShowDialog(int position) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onShow(position);
            }
        });
    }
    private void onShow(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        EditProfileFragmentDialog mFragment = new EditProfileFragmentDialog(this);
        mFragment.setArguments(bundle);
        Utility.getInstance().onShowFragmentDialog(requireActivity().getSupportFragmentManager(), mFragment);
    }
}