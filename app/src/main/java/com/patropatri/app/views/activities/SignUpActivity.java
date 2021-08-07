package com.patropatri.app.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.patropatri.app.R;
import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.JwtRequest;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
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

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        EditText confirm = (EditText) findViewById(R.id.confirm_password);

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
                    JwtRequest model = new JwtRequest();
                    model.setPassword(user);
                    model.setPassword(pass);
                    mProgress = Utility.getInstance().showProgressDialog(SignUpActivity.this, getResources().getString(R.string.progress), true);
                    mViewModel.register(model).observe(SignUpActivity.this, new Observer<Authentication>() {
                        @Override
                        public void onChanged(Authentication item) {
                            if (item != null) {
                                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Not successfully authenticated!", Snackbar.LENGTH_SHORT).show();
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
}