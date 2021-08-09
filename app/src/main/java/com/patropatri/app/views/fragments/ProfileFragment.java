package com.patropatri.app.views.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.ConstantKey;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;
import com.patropatri.app.views.dialogs.EditProfileFragmentDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

public class ProfileFragment extends Fragment implements EasyPermissions.PermissionCallbacks, EditProfileFragmentDialog.BottomSheetListener {

    private static final int ACTION_PICK_REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 4;
    private static final int REQUEST_CAMERA = 102;
    private static final int REQUEST_GALLERY = 103;

    private static final String[] CAMERA_PERMISSIONS = { android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, };
    private static final String[] GALLERY_PERMISSIONS = { android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    private UserWithToken mUser;
    private ProgressDialog mProgress;
    private UserViewModel mViewModel;

    private String mImageFileName;
    private String mCurrentPhotoPath;
    private File mFile;

    private CircleImageView mImageView;
    private TextView mItemName, mItemGender, mItemBirth, mItemAge, mIemHeight, mItemWeight, mItemMarital, mItemReligion, mItemComplex, mItemDistrict, mItemProfession, mItemDesignation, mItemCompany, mItemCompanyAddress, mItemWebsite, mItemPhone, mItemEmail, mItemPresent, mItemPermanent, mItemSummary, mItemInterest, mItemActivity, mItemExpectation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mImageView = (CircleImageView) view.findViewById(R.id.item_image);

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

        (mImageView).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_summary_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_personal_info_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_profession_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_contact_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_education_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_family_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_interest_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_activity_button)).setOnClickListener(new ActionHandler());
        ((ImageButton) view.findViewById(R.id.item_expectation_button)).setOnClickListener(new ActionHandler());

        //-----------------------------------------------| Session
        mUser = SharedPefManager.getInstance(getActivity()).getUser();
        if (mUser != null && mUser.getAuth() != null) {
            onAddItem(mUser.getUser());
            //if (mUser.getUser() != null && mUser.getUser().getImageName() != null) {
                Picasso.get().load(ConstantKey.RESOURCE_URL + "IMG_20210809_171250_288.jpg" ).placeholder(R.drawable.ic_add_photo).error(R.drawable.ic_add_photo).into(mImageView);
            //}
        }

        //-----------------------------------------------| Receive and observe the data from view model
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        /*if (getActivity() != null && mUser != null && mUser.getAuth() != null) {
            mViewModel.getUserById(mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<User>() {
                @Override
                public void onChanged(User item) {
                    if (item != null) {
                        onAddItem(item);
                    }
                }
            });
        }*/

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
            if (v.getId() == R.id.item_image) {
                mImageFileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault()).format(new Date()) + ".jpg";
                if (mUser != null && mUser.getUser() != null && mUser.getUser().getImageName() != null) {
                    mImageFileName = mUser.getUser().getImageName();
                }
                showDialog();

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

    //====================================================| Check Permissions for Mic, Camera, Gallery
    //@AfterPermissionGranted(REQUEST_CAMERA)
    private void cameraRequestPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), CAMERA_PERMISSIONS)) {
            // Already have permission, do the thing
            getCamera();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "This app needs access to your camera to capture photo", REQUEST_CAMERA, CAMERA_PERMISSIONS);
        }
    }

    //@AfterPermissionGranted(REQUEST_GALLERY)
    private void galleryRequestPermissions() {
        if (EasyPermissions.hasPermissions(getActivity(), GALLERY_PERMISSIONS)) {
            // Already have permission, do the thing
            getGallery();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "This app needs access to your gallery to access images", REQUEST_GALLERY, GALLERY_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> permissions) {
        // Some permissions have been granted
        if (Arrays.equals(permissions.toArray(new String[0]), CAMERA_PERMISSIONS) && requestCode == REQUEST_CAMERA) {
            getCamera();
        }
        if (Arrays.equals(permissions.toArray(new String[0]), GALLERY_PERMISSIONS) && requestCode == REQUEST_GALLERY) {
            getGallery();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // Some permissions have been denied
    }

    //====================================================| Permissions Result for Camera, Gallery
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_PICK_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri uri = data.getData();
            Bitmap bitmap = Utility.getInstance().getDownBitmap(getActivity(), uri, 250, 250);
            mImageView.setImageBitmap(bitmap);
            String mImagePath = Utility.getInstance().saveToInternalStorage(getActivity(), bitmap, mImageFileName);
            mFile = new File(mImagePath, mImageFileName);
            storeImage();
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && mCurrentPhotoPath != null) {
            Uri uri = Uri.fromFile(new File(mCurrentPhotoPath));
            Bitmap bitmap = Utility.getInstance().getDownBitmap(getActivity(), uri, 250, 250);
            mImageView.setImageBitmap(bitmap);
            String mImagePath = Utility.getInstance().saveToInternalStorage(getActivity(), bitmap, mImageFileName);
            mFile = new File(mImagePath, mImageFileName);
            storeImage();
        }
    }

    //====================================================| Camera and Gallery Dialog
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo_upload_option, null, false);
        builder.setView(view);
        builder.setCancelable(true);
        builder.create();
        final AlertDialog dialog = builder.show();
        ((ImageButton) view.findViewById(R.id.camera_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraRequestPermissions();
                dialog.dismiss();
            }
        });
        ((ImageButton) view.findViewById(R.id.gallery_id)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryRequestPermissions();
                dialog.dismiss();
            }
        });
    }

    private void getCamera() {
        if (getActivity() != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            try {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", photoFile); //BuildConfig.APPLICATION_ID || getActivity().getOpPackageName()
                    //Log.d(TAG, "Image Uri: " + photoURI);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private File createImageFile() throws IOException {
        if (getActivity() != null) {
            //imageFileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(mImageFileName.replace(".jpg", ""),/* prefix */".jpg",/* suffix */storageDir/* directory */);
            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }
        return null;
    }

    private void getGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), ACTION_PICK_REQUEST_CODE);
    }

    private MultipartBody.Part uploadFile(File file, String fileName) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", fileName, requestBody);
        return part;
    }

    private void storeImage() {
        if (mUser != null && getActivity() != null) {
            if (mFile == null) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Empty image is not allowed!", Snackbar.LENGTH_SHORT).show();
            } else {
                mProgress = Utility.getInstance().showProgressDialog(getActivity(), getResources().getString(R.string.progress), true);
                mViewModel.uploadFileForProfileAndCover(uploadFile(mFile, mImageFileName), mImageFileName, true, mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<String>() {
                    @Override
                    public void onChanged(String result) {
                        if (result != null) {
                            if (mUser.getUser() != null) {
                                mUser.getUser().setImageName(result);
                                SharedPefManager.getInstance(getActivity()).saveUser(mUser);
                            }
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Did not store image file Unsuccessfully!", Snackbar.LENGTH_SHORT).show();
                        }
                        Utility.getInstance().dismissProgressDialog(mProgress);
                    }
                });
            }
        }
    }
}