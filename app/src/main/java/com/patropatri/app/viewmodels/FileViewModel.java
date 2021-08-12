package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Gallery;
import com.patropatri.app.repositories.RetrofitClient;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileViewModel extends AndroidViewModel {

    private Application mApplication;

    public FileViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    //===============================================| File Upload
    public MutableLiveData<String> uploadFileForProfileAndCover(MultipartBody.Part file, String fileName, boolean isProfile, long id, String token) {
        MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = RetrofitClient.getInstance().getApi().uploadFileForProfileAndCover(file, fileName, isProfile, id, token);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
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
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<Gallery> uploadGalleryImage(MultipartBody.Part file, long id, String token) {
        MutableLiveData<Gallery> data = new MutableLiveData<>();
        Call<Gallery> call = RetrofitClient.getInstance().getApi().uploadGalleryImage(file, id, token);
        call.enqueue(new Callback<Gallery>() {
            @Override
            public void onResponse(@NonNull Call<Gallery> call, @NonNull Response<Gallery> response) {
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
            public void onFailure(@NonNull Call<Gallery> call, @NonNull Throwable t) {
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                data.setValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<ArrayList<String>> uploadMultiFiles(MultipartBody.Part[] file, String token) {
        MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        Call<ArrayList<String>> call = RetrofitClient.getInstance().getApi().uploadMultiFiles(file, token);
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<String>> call, @NonNull Response<ArrayList<String>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<String>> call, @NonNull Throwable t) {
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                data.setValue(null);
            }
        });
        return data;
    }

}
