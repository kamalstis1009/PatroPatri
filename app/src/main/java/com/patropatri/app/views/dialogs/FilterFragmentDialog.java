package com.patropatri.app.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.patropatri.app.R;

import java.util.Objects;

public class FilterFragmentDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container, false);

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