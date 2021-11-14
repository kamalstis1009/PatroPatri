package com.patropatri.app.views.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.patropatri.app.R;
import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class SignUpActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private static final int REQUEST_CODE = 1;
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_PHONE_STATE};


    private ProgressDialog mProgress;
    private EditText username;
    private MyNetworkReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //-----------------------------------------------| Check network and location
        mNetworkReceiver = new MyNetworkReceiver(this);

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //mPriority.setSelection(Arrays.asList(priorities).indexOf(mTaskModel.getPriority()));

        username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirm = (EditText) findViewById(R.id.confirm_password);

        //onChekRequestPermissions();

        ((Button) findViewById(R.id.submit_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim(),
                        pass = password.getText().toString().trim(),
                        conf = confirm.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    Snackbar.make(findViewById(android.R.id.content), "Empty phone number is not allowed!", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Snackbar.make(findViewById(android.R.id.content), "Empty password is not allowed!", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(conf)) {
                    Snackbar.make(findViewById(android.R.id.content), "Empty confirm password is not allowed!", Snackbar.LENGTH_SHORT).show();
                } else if (!pass.contains(conf)) {
                    Snackbar.make(findViewById(android.R.id.content), "Password and confirm password are not the same!", Snackbar.LENGTH_SHORT).show();
                } else {
                    AuthenticationDto model = new AuthenticationDto();
                    model.setUsername(user);
                    model.setPassword(pass);
                    mProgress = Utility.getInstance().showProgressDialog(SignUpActivity.this, getResources().getString(R.string.progress), true);
                    mViewModel.register(model).observe(SignUpActivity.this, new Observer<Authentication>() {
                        @Override
                        public void onChanged(Authentication item) {
                            if (item != null) {
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "The registration process was not successful!", Snackbar.LENGTH_SHORT).show();
                            }
                            Utility.getInstance().dismissProgressDialog(mProgress);
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //====================================================| Check Permissions for Mic, Camera, Gallery
    //@AfterPermissionGranted(REQUEST_CODE)
    private void onChekRequestPermissions() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            // Already have permission, do the thing
            getPhone();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "This app needs access to your camera to capture photo", REQUEST_CODE, PERMISSIONS);
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
        if (Arrays.equals(permissions.toArray(new String[0]), PERMISSIONS) && requestCode == REQUEST_CODE) {
            getPhone();
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
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            getPhone();
        }
    }

    private void getPhone() {
        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        username.setText(manager.getLine1Number());
    }

}