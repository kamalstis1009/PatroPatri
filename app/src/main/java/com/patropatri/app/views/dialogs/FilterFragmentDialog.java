package com.patropatri.app.views.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.patropatri.app.R;
import com.patropatri.app.models.Filter;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FilterFragmentDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

        //-----------------------------------------------| findViewById
        Spinner mItemGender = (Spinner) view.findViewById(R.id.item_gender);
        Spinner mItemMaritalStatus = (Spinner) view.findViewById(R.id.item_marital_status);
        Spinner mItemReligion = (Spinner) view.findViewById(R.id.item_religion);
        Spinner mItemProfession = (Spinner) view.findViewById(R.id.item_profession);
        Spinner mItemFromYear = (Spinner) view.findViewById(R.id.item_from_year);
        Spinner mItemToYear = (Spinner) view.findViewById(R.id.item_to_year);
        Spinner mItemDistrict = (Spinner) view.findViewById(R.id.item_district);

        //-----------------------------------------------| Districts sort
        String[] districts = getResources().getStringArray(R.array.district_array);
        Arrays.sort(districts);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(districts));
        list.add(0, "District");
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        mItemDistrict.setAdapter(mArrayAdapter);

        ArrayAdapter<String> mArrayAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, ConstantKey.getYears( Integer.parseInt(Utility.getInstance().getYear()) ));
        mItemFromYear.setAdapter(mArrayAdapter2);
        mItemToYear.setAdapter(mArrayAdapter2);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        ((Button) view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = mItemGender.getSelectedItem().toString().trim(),
                        status = mItemMaritalStatus.getSelectedItem().toString().trim(),
                        religion = mItemReligion.getSelectedItem().toString().trim(),
                        profession = mItemProfession.getSelectedItem().toString().trim(),
                        fromYear = mItemFromYear.getSelectedItem().toString().trim(),
                        toYear = mItemToYear.getSelectedItem().toString().trim(),
                        district = mItemDistrict.getSelectedItem().toString().trim();

                Filter model = new Filter();
                model.setGender(gender.contains("Gender") ? null : gender);
                model.setMaritalStatus(status.contains("Marital Status") ? null : status);
                model.setReligion(religion.contains("Religion") ? null : religion);
                model.setProfession(profession.contains("Profession") ? null : profession);
                model.setFromYear(fromYear.contains("From Age") ? null : Integer.parseInt(fromYear));
                model.setToYear(toYear.contains("To Age") ? null : Integer.parseInt(toYear));
                model.setDistrict(district.contains("District") ? null : district);
            }
        });

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