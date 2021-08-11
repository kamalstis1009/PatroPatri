package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Candidate;
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

    //===============================================| Home
    public LiveData<ArrayList<Candidate>> getAllCandidatesByPaginated(int pageNo, int rowSize, String token) {
        MutableLiveData<ArrayList<Candidate>> data = new MutableLiveData<>();
        Call<ArrayList<Candidate>> call = RetrofitClient.getInstance().getApi().getAllCandidatesByPaginated(pageNo, rowSize, token);
        call.enqueue(new Callback<ArrayList<Candidate>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Candidate>> call, @NonNull Response<ArrayList<Candidate>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Candidate>> call, @NonNull Throwable t) {
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

    public LiveData<Boolean> updateUserPost(long id, boolean isActive, String token) {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        Call<Boolean> call = RetrofitClient.getInstance().getApi().updateUserPost(id, isActive, token);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());
                    } else {
                        data.setValue(false);
                    }
                } else {
                    data.setValue(false);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                data.setValue(false);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
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

    public LiveData<ArrayList<Family>> getAllFamiliesByUserId(long userId, String token) {
        MutableLiveData<ArrayList<Family>> data = new MutableLiveData<>();
        Call<ArrayList<Family>> call = RetrofitClient.getInstance().getApi().getAllFamiliesByUserId(userId, token);
        call.enqueue(new Callback<ArrayList<Family>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Family>> call, @NonNull Response<ArrayList<Family>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Family>> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    //===============================================| Education
    public LiveData<Education> addEducation(Education model, String token) {
        MutableLiveData<Education> data = new MutableLiveData<>();
        Call<Education> call = RetrofitClient.getInstance().getApi().addEducation(model, token);
        call.enqueue(new Callback<Education>() {
            @Override
            public void onResponse(@NonNull Call<Education> call, @NonNull Response<Education> response) {
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
            public void onFailure(@NonNull Call<Education> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Integer> deleteEducationById(long id, String token) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        Call<Integer> call = RetrofitClient.getInstance().getApi().deleteEducationById(id, token);
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

    public LiveData<ArrayList<Education>> getAllEducationsByUserId(long userId, String token) {
        MutableLiveData<ArrayList<Education>> data = new MutableLiveData<>();
        Call<ArrayList<Education>> call = RetrofitClient.getInstance().getApi().getAllEducationsByUserId(userId, token);
        call.enqueue(new Callback<ArrayList<Education>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Education>> call, @NonNull Response<ArrayList<Education>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Education>> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    //===============================================| Gallery
    public LiveData<ArrayList<Gallery>> getGalleriesById(long userId, String token) {
        MutableLiveData<ArrayList<Gallery>> data = new MutableLiveData<>();
        Call<ArrayList<Gallery>> call = RetrofitClient.getInstance().getApi().getAllGalleriesByUserId(userId, token);
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

    public LiveData<Integer> deleteGalleryById(long id, String fileName, String token) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        Call<Integer> call = RetrofitClient.getInstance().getApi().deleteGalleryById(id, fileName, token);
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
