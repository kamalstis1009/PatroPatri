package com.patropatri.app.views.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
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
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.FileViewModel;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.AttachPhotoAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

public class MessageFragmentDialog extends BottomSheetDialogFragment implements EasyPermissions.PermissionCallbacks {

    private static final int ACTION_PICK_REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 4;
    private static final int REQUEST_CAMERA = 102;
    private static final int REQUEST_GALLERY = 103;

    private static final String[] CAMERA_PERMISSIONS = { android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, };
    private static final String[] GALLERY_PERMISSIONS = { android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    private FileViewModel mFileViewModel;

    private String mImageFileName;
    private String mCurrentPhotoPath;
    private File mFile;

    private ArrayList<Bitmap> mArrayList = new ArrayList<>();
    private ArrayList<MultipartBody.Part> mFileList = new ArrayList<>();
    private AttachPhotoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_dialog, container, false);

        //-----------------------------------------------| findViewById
        EditText editText = (EditText) view.findViewById(R.id.item_message);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        initRecyclerView(mRecyclerView, mArrayList);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        mFileViewModel = new ViewModelProvider(this).get(FileViewModel.class);
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            Candidate mModel = new Gson().fromJson(getArguments().getString("DATA", null), Candidate.class);

            ((Button) view.findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) {
                        String message = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(message)) {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Empty message did not allowed!", Snackbar.LENGTH_SHORT).show();
                        } else {
                            if (mModel != null && mUser != null && mUser.getAuth() != null) {
                                if (mFileList != null && mFileList.size() > 0) {
                                    mFileViewModel.uploadMultiFiles(mFileList.toArray(new MultipartBody.Part[mFileList.size()]), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<String>>() {
                                        @Override
                                        public void onChanged(ArrayList<String> arrayList) {
                                            if (arrayList != null) {
                                                MessageDto model = new MessageDto();
                                                model.setMessage(message);
                                                model.setImageFiles(arrayList.toArray(new String[arrayList.size()]));
                                                model.setSenderId(mUser.getAuth().getId());
                                                model.setReceiverId(mModel.getId());
                                                model.setIsEnabled(false);
                                                mViewModel.addInbox(model, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<MessageDto>() {
                                                    @Override
                                                    public void onChanged(MessageDto item) {
                                                        if (item != null) {
                                                            dismiss();
                                                        }  else {
                                                            Snackbar.make(getActivity().findViewById(android.R.id.content), "Message not sent successfully!", Snackbar.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Unfortunately the picture could not be sent!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    MessageDto model = new MessageDto();
                                    model.setMessage(message);
                                    model.setSenderId(mUser.getAuth().getId());
                                    model.setReceiverId(mModel.getId());
                                    model.setIsEnabled(false);
                                    mViewModel.addInbox(model, "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<MessageDto>() {
                                        @Override
                                        public void onChanged(MessageDto item) {
                                            if (item != null) {
                                                dismiss();
                                            }  else {
                                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Message not sent successfully!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
        }

        ((ImageButton) view.findViewById(R.id.add_photo_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageFileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date()) + ".jpg";
                showDialog();
                //String[] array = list.toArray(new String[list.size()]);
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

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Bitmap> arrayList) {
        mAdapter = new AttachPhotoAdapter(getActivity(), arrayList);
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
            String mImagePath = Utility.getInstance().saveToInternalStorage(getActivity(), bitmap, mImageFileName);
            mFile = new File(mImagePath, mImageFileName);
            if (mAdapter != null) {
                mArrayList.add(bitmap);
                mFileList.add(uploadFile(mFile, mImageFileName));
                mAdapter.notifyItemInserted(mArrayList.size());
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && mCurrentPhotoPath != null) {
            Uri uri = Uri.fromFile(new File(mCurrentPhotoPath));
            Bitmap bitmap = Utility.getInstance().getDownBitmap(getActivity(), uri, 250, 250);
            String mImagePath = Utility.getInstance().saveToInternalStorage(getActivity(), bitmap, mImageFileName);
            mFile = new File(mImagePath, mImageFileName);
            if (mAdapter != null) {
                mArrayList.add(bitmap);
                mFileList.add(uploadFile(mFile, mImageFileName));
                mAdapter.notifyItemInserted(mArrayList.size());
            }
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

}