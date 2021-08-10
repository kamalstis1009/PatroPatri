package com.patropatri.app.repositories;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Education;
import com.patropatri.app.models.Family;
import com.patropatri.app.models.Gallery;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //======================================================| Family
    @Headers("Content-Type: application/json")
    @POST("family")
    Call<Family> addFamily(@Body Family item, @Header("Authorization") String token);

    @DELETE("family/{id}")
    Call<Integer> deleteFamilyById(@Path("id") long id, @Header("Authorization") String token);

    @GET("families/{userId}")
    Call<ArrayList<Family>> getAllFamiliesByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    //======================================================| Education
    @Headers("Content-Type: application/json")
    @POST("education")
    Call<Education> addEducation(@Body Education item, @Header("Authorization") String token);

    @DELETE("education/{id}")
    Call<Integer> deleteEducationById(@Path("id") long id, @Header("Authorization") String token);

    @GET("educations/{userId}")
    Call<ArrayList<Education>> getAllEducationsByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    //======================================================| Gallery
    @GET("galleries/{userId}")
    Call<ArrayList<Gallery>> getAllGalleriesByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @DELETE("gallery/{id}")
    Call<Integer> deleteGalleryById(@Path("id") long id, @Query("fileName") String fileName, @Header("Authorization") String token);

    //======================================================| File Upload & Download
    @Multipart
    @POST("file/upload/update-profile-cover")
    Call<String> uploadFileForProfileAndCover(@Part MultipartBody.Part file, @Query("fileName") String fileName, @Query("isProfile") boolean isProfile, @Query("userId") long userId, @Header("Authorization") String token);

    @Multipart
    @POST("file/upload/gallery-image")
    Call<Gallery> uploadGalleryImage(@Part MultipartBody.Part file, @Query("userId") long userId, @Header("Authorization") String token);



}
