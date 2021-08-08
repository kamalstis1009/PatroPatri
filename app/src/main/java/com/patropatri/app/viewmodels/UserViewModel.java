package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {

    private Application mApplication;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    //===============================================| Login
    public LiveData<UserWithToken> login(AuthenticationDto model) {
        MutableLiveData<UserWithToken> data = new MutableLiveData<>();
        Call<UserWithToken> call = RetrofitClient.getInstance().getApi().login(model);
        call.enqueue(new Callback<UserWithToken>() {
            @Override
            public void onResponse(@NonNull Call<UserWithToken> call, @NonNull Response<UserWithToken> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(null);
                    }
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserWithToken> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Authentication> register(AuthenticationDto model) {
        MutableLiveData<Authentication> data = new MutableLiveData<>();
        Call<Authentication> call = RetrofitClient.getInstance().getApi().register(model);
        call.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(@NonNull Call<Authentication> call, @NonNull Response<Authentication> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(null);
                    }
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Authentication> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    //===============================================| Login
    public LiveData<User> updateUser(User model, String token) {
        MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = RetrofitClient.getInstance().getApi().addUser(model, token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(null);
                    }
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

}
