package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.NotificationDto;
import com.patropatri.app.repositories.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends AndroidViewModel {

    private Application mApplication;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    //===============================================| Notification
    public LiveData<ArrayList<NotificationDto>> getAllNotificationsByUserId(int pageNo, int rowSize, long userId, String token) {
        MutableLiveData<ArrayList<NotificationDto>> data = new MutableLiveData<>();
        Call<ArrayList<NotificationDto>> call = RetrofitClient.getInstance().getApi().getAllNotificationsByUserId(pageNo, rowSize, userId, token);
        call.enqueue(new Callback<ArrayList<NotificationDto>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<NotificationDto>> call, @NonNull Response<ArrayList<NotificationDto>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<NotificationDto>> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<NotificationDto> addNotification(NotificationDto item, String token) {
        MutableLiveData<NotificationDto> data = new MutableLiveData<>();
        Call<NotificationDto> call = RetrofitClient.getInstance().getApi().addNotification(item, token);
        call.enqueue(new Callback<NotificationDto>() {
            @Override
            public void onResponse(@NonNull Call<NotificationDto> call, @NonNull Response<NotificationDto> response) {
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
            public void onFailure(@NonNull Call<NotificationDto> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Integer> deleteNotificationById(long id, String token) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        Call<Integer> call = RetrofitClient.getInstance().getApi().deleteNotificationById(id, token);
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

    //===============================================| Inbox
    public LiveData<ArrayList<Inbox>> getAllInboxesByUserId(int pageNo, int rowSize, long userId, String token) {
        MutableLiveData<ArrayList<Inbox>> data = new MutableLiveData<>();
        Call<ArrayList<Inbox>> call = RetrofitClient.getInstance().getApi().getAllInboxesByUserId(pageNo, rowSize, userId, token);
        call.enqueue(new Callback<ArrayList<Inbox>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Inbox>> call, @NonNull Response<ArrayList<Inbox>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Inbox>> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Inbox> addInbox(Inbox item, String token) {
        MutableLiveData<Inbox> data = new MutableLiveData<>();
        Call<Inbox> call = RetrofitClient.getInstance().getApi().addInbox(item, token);
        call.enqueue(new Callback<Inbox>() {
            @Override
            public void onResponse(@NonNull Call<Inbox> call, @NonNull Response<Inbox> response) {
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
            public void onFailure(@NonNull Call<Inbox> call, @NonNull Throwable t) {
                data.setValue(null);
                Toast.makeText(mApplication.getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return data;
    }

    public LiveData<Integer> deleteInboxById(long id, String token) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        Call<Integer> call = RetrofitClient.getInstance().getApi().deleteInboxById(id, token);
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
