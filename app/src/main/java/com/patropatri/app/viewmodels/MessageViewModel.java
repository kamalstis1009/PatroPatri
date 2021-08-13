package com.patropatri.app.viewmodels;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.MessageDto;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.NotificationDto;
import com.patropatri.app.repositories.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageViewModel extends AndroidViewModel {

    private Application mApplication;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    //===============================================| Notification
    public LiveData<ArrayList<Notification>> getAllNotificationsByUserId(int pageNo, int rowSize, long userId, String token) {
        MutableLiveData<ArrayList<Notification>> data = new MutableLiveData<>();
        Call<ArrayList<Notification>> call = RetrofitClient.getInstance().getApi().getAllNotificationsByUserId(pageNo, rowSize, userId, token);
        call.enqueue(new Callback<ArrayList<Notification>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Notification>> call, @NonNull Response<ArrayList<Notification>> response) {
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
            public void onFailure(@NonNull Call<ArrayList<Notification>> call, @NonNull Throwable t) {
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

    public LiveData<Message> addInbox(MessageDto item, String token) {
        MutableLiveData<Message> data = new MutableLiveData<>();
        Call<Message> call = RetrofitClient.getInstance().getApi().addInbox(item, token);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
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
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
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
