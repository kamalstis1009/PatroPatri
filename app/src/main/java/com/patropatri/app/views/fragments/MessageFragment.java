package com.patropatri.app.views.fragments;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.patropatri.app.R;
import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.WebSocketClient;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.GridSpacingItemDecoration;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.FileViewModel;
import com.patropatri.app.viewmodels.MessageViewModel;
import com.patropatri.app.views.adapters.AttachPhotoAdapter;
import com.patropatri.app.views.adapters.ChatAttachAdapter;
import com.patropatri.app.views.adapters.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressWarnings({"FieldCanBeLocal", "ResultOfMethodCallIgnored", "CheckResult"})
public class MessageFragment extends Fragment implements MessageAdapter.CallBackListener, EasyPermissions.PermissionCallbacks {

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

    private AttachPhotoAdapter mAttachAdapter;
    private ArrayList<Bitmap> mBitmapList = new ArrayList<>();
    private ArrayList<MultipartBody.Part> mFileList = new ArrayList<>();

    private ProgressBar mProgressBar;
    private EditText editText;
    private Inbox mModel;
    private MessageAdapter mAdapter;
    private ArrayList<Message> mArrayList = new ArrayList<>();

    private boolean isLoading = true;
    private int mInitPageNo = 1;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //-----------------------------------------------| findViewById
        mProgressBar = view.findViewById(R.id.progress_bar);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.items_recycler_view);
        RecyclerView mRecyclerView2 = (RecyclerView) view.findViewById(R.id.items_attach_recycler_view);
        editText = (EditText) view.findViewById(R.id.sent_chat_text);
        initAttachRecyclerView(mRecyclerView2, mBitmapList);

        //-----------------------------------------------| Session
        UserWithToken mUser = SharedPefManager.getInstance(getActivity()).getUser();

        //-----------------------------------------------| Receive and observe the data from view model
        mFileViewModel = new ViewModelProvider(this).get(FileViewModel.class);
        MessageViewModel mViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        //------------------------------------------------| Get Bundle Data
        if (getArguments() != null && getArguments().getString("DATA", null) != null) {
            mModel = new Gson().fromJson(getArguments().getString("DATA", null), Inbox.class);

            if (getActivity() != null && mModel != null && mUser != null) {
                Utility.getInstance().onVisibleProgressBar(mProgressBar);
                mViewModel.getAllMessages(mInitPageNo,10, mModel.getSenderId(), mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Message>>() {
                    @Override
                    public void onChanged(ArrayList<Message> arrayList) {
                        if (arrayList != null) {
                            mArrayList.addAll(arrayList);
                        }
                        initRecyclerView(mRecyclerView, mArrayList, mModel);
                        Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                    }
                });
            }
        }

        //------------------------------------------------| Scroll-down load more data
        if (getActivity() != null) {
            /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    LinearLayoutManager mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (dy > 0) {
                        //check for scroll down
                        visibleItemCount = mLayoutManager != null ? mLayoutManager.getChildCount() : 0;
                        totalItemCount = mLayoutManager != null ? mLayoutManager.getItemCount() : 0;
                        pastVisibleItems = mLayoutManager != null ? mLayoutManager.findFirstVisibleItemPosition() : 0;

                        if (isLoading) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                isLoading = false;
                                mInitPageNo++;
                                if (mUser != null && mUser.getAuth() != null) {
                                    Utility.getInstance().onVisibleProgressBar(mProgressBar);
                                    mViewModel.getAllMessages(mInitPageNo,10, mModel.getSenderId(), mUser.getAuth().getId(), "Bearer " + mUser.getToken()).observe(getActivity(), new Observer<ArrayList<Message>>() {
                                        @Override
                                        public void onChanged(ArrayList<Message> arrayList) {
                                            if (arrayList != null) {
                                                mArrayList.addAll(arrayList);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                            Utility.getInstance().onInvisibleProgressBar(mProgressBar);
                                        }
                                    });
                                }
                                isLoading = true;
                            }
                        }
                    }
                }
            });*/
        }

        //-----------------------------------------------| StompClient Connect
        StompClient mClient = WebSocketClient.getInstance().getStompClient();

        // Subscribe chat endpoint to receive response by RX Java
        if (getActivity() != null && mModel != null && mUser != null) {
            mClient.topic("/user/" + mUser.getAuth().getId() + "/msg").subscribe(new Consumer<StompMessage>() {
                @Override
                public void accept(StompMessage message) throws Exception {
                    JSONObject json = new JSONObject(message.getPayload());
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Message model = new Message();
                                model.setId(Long.parseLong(json.getString("id")));
                                model.setMessage(json.getString("message"));
                                JSONArray arr = json.getJSONArray("imageFiles");
                                if(arr.length() > 0) {
                                    model.setImageFiles(json.getString("imageFiles").replaceAll("\\[|\\]|\"", "").split(","));
                                }
                                model.setSenderId(Long.parseLong(json.getString("senderId")));
                                model.setReceiverId(Long.parseLong(json.getString("receiverId")));
                                model.setIsEnabled(Boolean.parseBoolean(json.getString("isEnabled")));
                                model.setInboxId(mModel.getId());
                                model.setCreatedAt(new Date());
                                if (mAdapter != null) {
                                    mArrayList.add(model);
                                    mAdapter.notifyItemInserted(mArrayList.size());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

        ((ImageButton) view.findViewById(R.id.attach_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageFileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date()) + ".jpg";
                showDialog();
                //String[] array = list.toArray(new String[list.size()]);
            }
        });

        //-----------------------------------------------| Sent Message
        ((ImageButton) view.findViewById(R.id.send_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        JSONObject json = new JSONObject();
                                        try {
                                            //json.put("id", null);
                                            json.put("message", message);
                                            json.put("imageFiles", new JSONArray(arrayList.toArray(new String[arrayList.size()])));
                                            json.put("senderId", mUser.getAuth().getId());
                                            json.put("receiverId", mModel.getSenderId());
                                            json.put("isEnabled", true);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        mClient.send("/app/chat", json.toString()).subscribe();

                                        Message model = new Message();
                                        model.setMessage(message);
                                        model.setImageFiles(arrayList.toArray(new String[arrayList.size()]));
                                        model.setSenderId(mUser.getAuth().getId());
                                        model.setReceiverId(mModel.getSenderId());
                                        model.setIsEnabled(true);
                                        model.setInboxId(mModel.getId());
                                        model.setCreatedAt(new Date());
                                        if (mAdapter != null) {
                                            mArrayList.add(model);
                                            mAdapter.notifyItemInserted(mArrayList.size());
                                        }
                                        if (mFileList != null && mFileList.size() > 0) {
                                            mFileList.clear();
                                        }
                                        if (mBitmapList != null && mBitmapList.size() > 0) {
                                            mBitmapList.clear();
                                            initAttachRecyclerView(mRecyclerView2, mBitmapList);
                                        }
                                        editText.getText().clear();
                                    } else {
                                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Unfortunately the picture could not be sent!", Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            JSONObject json = new JSONObject();
                            try {
                                //json.put("id", null);
                                json.put("message", message);
                                json.put("imageFiles", new JSONArray());
                                json.put("senderId", mUser.getAuth().getId());
                                json.put("receiverId", mModel.getSenderId());
                                json.put("isEnabled", true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            mClient.send("/app/chat", json.toString()).subscribe();

                            Message model = new Message();
                            model.setMessage(message);
                            model.setImageFiles(null);
                            model.setSenderId(mUser.getAuth().getId());
                            model.setReceiverId(mModel.getSenderId());
                            model.setIsEnabled(true);
                            model.setInboxId(mModel.getId());
                            model.setCreatedAt(new Date());
                            if (mAdapter != null) {
                                mArrayList.add(model);
                                mAdapter.notifyItemInserted(mArrayList.size());
                            }
                            if (mFileList != null && mFileList.size() > 0) {
                                mFileList.clear();
                            }
                            if (mBitmapList != null && mBitmapList.size() > 0) {
                                mBitmapList.clear();
                                initAttachRecyclerView(mRecyclerView2, mBitmapList);
                            }
                            editText.getText().clear();
                        }
                    }
                }
            }
        });

        ((ImageButton) view.findViewById(R.id.back_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.getInstance().onBackFragment(requireActivity().getSupportFragmentManager(), new MessageFragment());
            }
        });

        return view;
    }

    @Override
    public void onRecyclerViewItems(RecyclerView recyclerView, String [] arrayList) {
        initImageRecyclerView(recyclerView, arrayList);
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Message> arrayList, Inbox inbox) {
        mAdapter = new MessageAdapter(getActivity(), arrayList, inbox, this);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(mItems.size() - 1);
        recyclerView.scrollToPosition(mArrayList.size() - 1);
    }

    private void initImageRecyclerView(RecyclerView recyclerView, String [] arrayList) {
        ChatAttachAdapter adapter = new ChatAttachAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1){
            @Override
            protected boolean isLayoutRTL() {
                return true;
            }
        });
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        adapter.notifyDataSetChanged();
    }

    private void initAttachRecyclerView(RecyclerView recyclerView, ArrayList<Bitmap> arrayList) {
        mAttachAdapter = new AttachPhotoAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(mAttachAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2 /*spanCount*/, 5 /*px spacing*/, false /*includeEdge*/));
        mAttachAdapter.notifyDataSetChanged();
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
            if (mAttachAdapter != null) {
                mBitmapList.add(bitmap);
                mFileList.add(uploadFile(mFile, mImageFileName));
                mAttachAdapter.notifyItemInserted(mBitmapList.size());
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && mCurrentPhotoPath != null) {
            Uri uri = Uri.fromFile(new File(mCurrentPhotoPath));
            Bitmap bitmap = Utility.getInstance().getDownBitmap(getActivity(), uri, 250, 250);
            String mImagePath = Utility.getInstance().saveToInternalStorage(getActivity(), bitmap, mImageFileName);
            mFile = new File(mImagePath, mImageFileName);
            if (mAttachAdapter != null) {
                mBitmapList.add(bitmap);
                mFileList.add(uploadFile(mFile, mImageFileName));
                mAttachAdapter.notifyItemInserted(mBitmapList.size());
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
