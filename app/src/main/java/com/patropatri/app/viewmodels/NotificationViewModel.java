package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Education;
import com.patropatri.app.models.Family;
import com.patropatri.app.models.Gallery;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.RetrofitClient;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends AndroidViewModel {

    private Application mApplication;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    //===============================================| Family
    public LiveData<Family> addFamily(Family model, String token) {
        MutableLiveData<Family> data = new MutableLiveData<>();
        Call<Family> call = RetrofitClient.getInstance().getApi().addFamily(model, token);
        call.enqueue(new Callback<Family>() {
            @Override
            public void onResponse(@NonNull Call<Family> call, @NonNull Response<Family> response) {
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
            public void onFailure(@NonNull Call<Family> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Integer> deleteFamilyById(long id, String token) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        Call<Integer> call = RetrofitClient.getInstance().getApi().deleteFamilyById(id, token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(@NonNull Call<Integer> call, @NonNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(0);
                    }
                } else {
                    data.setValue(0);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Integer> call, @NonNull Throwable t) {
                data.setValue(0);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

}
