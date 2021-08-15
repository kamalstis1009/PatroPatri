package com.patropatri.app.views.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;

import com.patropatri.app.R;
import com.patropatri.app.utils.ConstantKey;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageViewerDialog {

    private static ImageViewerDialog mInstance;
    private Context mContext;
    private AlertDialog mDialog;
    private int mPosition;

    public ImageViewerDialog(Context context) {
        this.mContext = context;
    }

    public static synchronized ImageViewerDialog getInstance(Context context) {
        /*if (mInstance == null) {
            mInstance = new SearchableSpinnerDialog(context);
        }
        return mInstance;*/
        return new ImageViewerDialog(context);
    }

    public void getAlertDialog(ArrayList<String> items, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_image_viewer, null, false);
        builder.setView(view);
        builder.setCancelable(true);
        builder.create();
        try {
            mDialog = builder.show();
            
            //pop up the soft keyboard
            if(mDialog.getWindow() != null) {
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
        //if(!((Activity) mContext).isFinishing()) {}

        this.mPosition = position;

        ImageView imageView = (ImageView) view.findViewById(R.id.item_image);
        if (items.get(mPosition) != null) {
            Picasso.get().load(ConstantKey.RESOURCE_URL + items.get(mPosition)).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(imageView);
        }

        ((ImageButton) view.findViewById(R.id.previous_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() > 0) {
                    if (mPosition > 0) {
                        mPosition--;
                        String item = items.get(mPosition);
                        if (item != null) {
                            Picasso.get().load(ConstantKey.RESOURCE_URL + item).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(imageView);
                        }
                    }
                }
            }
        });

        ((ImageButton) view.findViewById(R.id.next_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size() > 0) {
                    if (mPosition < items.size() - 1) {
                        mPosition++;
                        String item = items.get(mPosition);
                        if (item != null) {
                            Picasso.get().load(ConstantKey.RESOURCE_URL + item).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(imageView);
                        }
                    }
                }
            }
        });

        //NestedScrollView mRootView = (NestedScrollView) view.findViewById(R.id.root_view);
    }

}
