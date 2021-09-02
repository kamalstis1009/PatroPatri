package com.patropatri.app.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.Education;
import com.patropatri.app.models.Family;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.adapters.EducationAdapter;
import com.patropatri.app.views.adapters.FamilyAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserAboutFragment extends Fragment {

    private UserWithToken mUser;
    private UserViewModel mViewModel;

    private RecyclerView mEducationRecyclerView, mFamilyRecyclerView;
    private TextView mItemName, mItemGender, mItemBirth, mItemAge, mIemHeight, mItemWeight, mItemMarital, mItemReligion, mItemComplex, mItemDistrict, mItemCitizenship, mItemMotherTongue, mItemProfession, mItemDesignation, mItemCompany, mItemCompanyAddress, mItemWebsite, mItemPhone, mItemEmail, mItemPresent, mItemPermanent, mItemSummary, mItemInterest, mItemActivity, mItemExpectation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_about, container, false);

        //-----------------------------------------------| Toolbar
        if (getActivity() != null) {
            //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

        //-----------------------------------------------| findViewById
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
        mItemCitizenship = (TextView) view.findViewById(R.id.item_citizenship);
        mItemMotherTongue = (TextView) view.findViewById(R.id.item_mother_tongue);

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

        mEducationRecyclerView = (RecyclerView) view.findViewById(R.id.items_education_recycler_view);
        mFamilyRecyclerView = (RecyclerView) view.findViewById(R.id.items_family_recycler_view);

        //-----------------------------------------------| Session
        mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            Candidate mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            if (mModel != null) {
                mViewModel.getUserById(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<User>() {
                    @Override
                    public void onChanged(User model) {
                        if (model != null) {
                            if (model.getFullName() != null)
                                mItemName.setText(model.getFullName());
                            if (model.getGender() != null)
                                mItemGender.setText(model.getGender());
                            if (model.getBirthDate() != null)
                                mItemBirth.setText(model.getBirthDate() + "");
                            if (model.getBirthDate() != null)
                                mItemAge.setText(calculateAge(Utility.getInstance().getDateFromStringDate(model.getBirthDate())) + "");
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
                            if (model.getCitizenship() != null)
                                mItemCitizenship.setText(model.getCitizenship());
                            if (model.getMotherTongue() != null)
                                mItemMotherTongue.setText(model.getMotherTongue());

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
                            if (model.getAddressLine1() != null) {
                                String address = model.getAddressLine1() + ", " + model.getDistrict1() + ", " + model.getPoliceStation1() + ", " + model.getPostalCode1() + ", " + model.getCountry1();
                                mItemPresent.setText(address);
                            }
                            if (model.getAddressLine2() != null) {
                                String address = model.getAddressLine2() + ", " + model.getDistrict2() + ", " + model.getPoliceStation2() + ", " + model.getPostalCode2() + ", " + model.getCountry2();
                                mItemPermanent.setText(address);
                            }

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
                });

                if (getActivity() != null) {
                    mViewModel.getAllEducationsByUserId(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Education>>() {
                        @Override
                        public void onChanged(ArrayList<Education> arrayList) {
                            if (arrayList != null) {
                                EducationAdapter mAdapter = new EducationAdapter(getActivity(), arrayList);
                                mEducationRecyclerView.setAdapter(mAdapter);
                                mEducationRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mEducationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }

                if (getActivity() != null) {
                    mViewModel.getAllFamiliesByUserId(mModel.getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Family>>() {
                        @Override
                        public void onChanged(ArrayList<Family> arrayList) {
                            if (arrayList != null) {
                                FamilyAdapter mAdapter = new FamilyAdapter(getActivity(), arrayList);
                                mFamilyRecyclerView.setAdapter(mAdapter);
                                mFamilyRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mFamilyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                //mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(5 /*px spacing*/));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }

        /*((TextView) view.findViewById(R.id.search_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onAddFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), new SearchFragment());
            }
        });*/

        return view;
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

}