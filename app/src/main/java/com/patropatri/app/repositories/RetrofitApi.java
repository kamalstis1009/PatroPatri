package com.patropatri.app.repositories;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.JwtRequest;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("/")
    public ResponseBody getAll();

    //======================================================| SignIn & SignUp
    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Call<UserWithToken> login(@Body JwtRequest request);

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Call<Authentication> register(@Body JwtRequest item);

    //======================================================| User
    @GET("api/access/GetAllAccessByUserId")
    Call<ArrayList<User>> GetAllAccessByUserId(@Query("userId") String userId, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("api/user/AddUser")
    Call<User> addUser(@Body User model);

    //======================================================| File Upload & Download
    @Multipart
    @POST("api/file/UploadFile")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Query("fileName") String fileName);



}
