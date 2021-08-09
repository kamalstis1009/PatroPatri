package com.patropatri.app.repositories;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Gallery;
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
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("/")
    public ResponseBody getAll();

    //======================================================| SignIn & SignUp
    @Headers("Content-Type: application/json")
    @POST("jwt/login")
    Call<UserWithToken> login(@Body AuthenticationDto request);

    @Headers("Content-Type: application/json")
    @POST("jwt/register")
    Call<Authentication> register(@Body AuthenticationDto request);

    //======================================================| User
    @GET("user/{id")
    Call<User> getUserById(@Path("id") long id, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT("user")
    Call<User> addUser(@Body User item, @Header("Authorization") String token);

    //======================================================| Gallery
    @GET("galleries/{id}")
    Call<ArrayList<Gallery>> getGalleriesById(@Path("id") long userId, @Header("Authorization") String token);

    //======================================================| File Upload & Download
    @Multipart
    @POST("file/upload/update-profile-cover")
    Call<String> uploadFileForProfileAndCover(@Part MultipartBody.Part file, @Query("fileName") String fileName, @Query("isProfile") boolean isProfile, @Query("id") long id, @Header("Authorization") String token);

    @Multipart
    @POST("file/upload/gallery-image")
    Call<Gallery> uploadGalleryImage(@Part MultipartBody.Part file, @Query("userId") long userId, @Header("Authorization") String token);



}
