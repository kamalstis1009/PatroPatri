package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Gallery;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.repositories.RetrofitClient;

import java.util.ArrayList;

import okhttp3.MultipartBody;
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

    //===============================================| User
    public LiveData<User> getUserById(long id, String token) {
        MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = RetrofitClient.getInstance().getApi().getUserById(id, token);
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

    //===============================================| Gallery
    public LiveData<ArrayList<Gallery>> getGalleriesById(long userId, String token) {
        MutableLiveData<ArrayList<Gallery>> data = new MutableLiveData<>();
        Call<ArrayList<Gallery>> call = RetrofitClient.getInstance().getApi().getGalleriesById(userId, token);
        call.enqueue(new Callback<ArrayList<Gallery>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Gallery>> call, @NonNull Response<ArrayList<Gallery>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Gallery>> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
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

}
