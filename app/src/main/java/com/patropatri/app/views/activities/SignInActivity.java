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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.patropatri.app.R;
import com.patropatri.app.models.JwtRequest;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.services.MyNetworkReceiver;
import com.patropatri.app.session.SharedPefManager;
import com.patropatri.app.utils.Utility;
import com.patropatri.app.viewmodels.UserViewModel;

public class SignInActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
    private MyNetworkReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //-----------------------------------------------| Check network and location
        mNetworkReceiver = new MyNetworkReceiver(this);

        //-----------------------------------------------| Receive and observe the data from view model
        UserViewModel mViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);

        ((Button) findViewById(R.id.sign_in_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim(),
                        pass = password.getText().toString().trim();
                if (TextUtils.isEmpty(user)) {
                    Snackbar.make(findViewById(android.R.id.content), "Empty is not allowed!", Snackbar.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    Snackbar.make(findViewById(android.R.id.content), "Empty is not allowed!", Snackbar.LENGTH_SHORT).show();
                } else {
                    JwtRequest model = new JwtRequest();
                    model.setPassword(user);
                    model.setPassword(pass);
                    mProgress = Utility.getInstance().showProgressDialog(SignInActivity.this, getResources().getString(R.string.progress), true);
                    mViewModel.login(model).observe(SignInActivity.this, new Observer<UserWithToken>() {
                        @Override
                        public void onChanged(UserWithToken item) {
                            if (item != null) {
                                SharedPefManager.getInstance(SignInActivity.this).saveUser(item);
                                startActivity(new Intent(SignInActivity.this, HomeActivity.class));
                                //Toast.makeText(SignInActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Did not match username or password!", Snackbar.LENGTH_SHORT).show();
                            }
                            Utility.getInstance().dismissProgressDialog(mProgress);
                        }
                    });
                }
            }
        });

        ((TextView) findViewById(R.id.sign_up_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
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