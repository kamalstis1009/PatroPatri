package com.patropatri.app.repositories;

import com.patropatri.app.models.Authentication;
import com.patropatri.app.models.AuthenticationDto;
import com.patropatri.app.models.Candidate;
import com.patropatri.app.models.Comment;
import com.patropatri.app.models.CommentDto;
import com.patropatri.app.models.Education;
import com.patropatri.app.models.Family;
import com.patropatri.app.models.Filter;
import com.patropatri.app.models.Gallery;
import com.patropatri.app.models.Inbox;
import com.patropatri.app.models.Liked;
import com.patropatri.app.models.LikedDto;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.MessageDto;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.NotificationDto;
import com.patropatri.app.models.Subscription;
import com.patropatri.app.models.User;
import com.patropatri.app.models.UserWithToken;
import com.patropatri.app.models.Viewer;

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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("/")
    public ResponseBody getAll();

    //======================================================| File
    @Multipart
    @POST("file/upload/update-profile-cover")
    Call<String> uploadFileForProfileAndCover(@Part MultipartBody.Part file, @Query("fileName") String fileName, @Query("isProfile") boolean isProfile, @Query("userId") long userId, @Header("Authorization") String token);

    @Multipart
    @POST("file/upload/gallery-image")
    Call<Gallery> uploadGalleryImage(@Part MultipartBody.Part file, @Query("userId") long userId, @Header("Authorization") String token);

    @Multipart
    @POST("file/upload/update-files")
    Call<ArrayList<String>> uploadMultiFiles(@Part MultipartBody.Part[] file, @Header("Authorization") String token);


    //======================================================| SignIn & SignUp
    @Headers("Content-Type: application/json")
    @POST("jwt/login")
    Call<UserWithToken> login(@Body AuthenticationDto request);

    @Headers("Content-Type: application/json")
    @POST("jwt/register")
    Call<Authentication> register(@Body AuthenticationDto request);

    //======================================================| Home
    @GET("users")
    Call<ArrayList<Candidate>> getAllCandidates(@Header("Authorization") String token);

    @GET("users/{no}")
    Call<ArrayList<Candidate>> getAllCandidatesByPaginated(@Path("no") int pageNo, @Query("row") int rowSize, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("users-filter")
    Call<ArrayList<Candidate>> getAllCandidatesByFilter(@Body Filter item, @Query("no") int pageNo, @Query("row") int rowSize, @Header("Authorization") String token);

    //======================================================| User
    @GET("user/{id}")
    Call<User> getUserById(@Path("id") long id, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT("user")
    Call<User> addUser(@Body User item, @Header("Authorization") String token);

    @PATCH("user/{id}")
    Call<Boolean> updateUserPost(@Path("id") long id, @Query("isActive") boolean isActive, @Header("Authorization") String token);

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

    //======================================================| Subscription
    @GET("subscriptions")
    Call<ArrayList<Subscription>> getAllSubscriptions(@Header("Authorization") String token);

    //======================================================| Gallery
    @GET("galleries/{userId}")
    Call<ArrayList<Gallery>> getAllGalleriesByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @DELETE("gallery/{id}")
    Call<Integer> deleteGalleryById(@Path("id") long id, @Query("fileName") String fileName, @Header("Authorization") String token);

    //======================================================| Notification
    @GET("message/notifications/{no}")
    Call<ArrayList<Notification>> getAllNotificationsByUserId(@Path("no") int pageNo, @Query("row") int rowSize, @Query("userId") long userId, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("message/notification")
    Call<NotificationDto> addNotification(@Body NotificationDto item, @Header("Authorization") String token);

    @DELETE("message/notification/{id}")
    Call<Integer> deleteNotificationById(@Path("id") long id, @Header("Authorization") String token);

    //======================================================| Inbox
    @GET("message/inboxes/{no}")
    Call<ArrayList<Inbox>> getAllInboxesByUserId(@Path("no") int pageNo, @Query("row") int rowSize, @Query("userId") long userId, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("message/inbox")
    Call<MessageDto> addInbox(@Body MessageDto item, @Header("Authorization") String token);

    @DELETE("message/inbox/{id}")
    Call<Integer> deleteInboxById(@Path("id") long id, @Header("Authorization") String token);

    //======================================================| Chat/Message
    @GET("message/chats/{no}")
    Call<ArrayList<Message>> getAllMessages(@Path("no") int pageNo, @Query("row") int rowSize, @Query("senderId") long senderId, @Query("receiverId") long receiverId, @Header("Authorization") String token);

    //======================================================| Comment
    @GET("message/comments/{userId}")
    Call<ArrayList<CommentDto>> getAllCommentsByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("message/comment")
    Call<Comment> addComment(@Body Comment item, @Header("Authorization") String token);

    @DELETE("message/comment/{id}")
    Call<Integer> deleteCommentById(@Path("id") long id, @Header("Authorization") String token);

    //======================================================| Liked
    @GET("message/like/{userId}")
    Call<Liked> getLikedByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @GET("message/likes/{userId}")
    Call<ArrayList<LikedDto>> getAllLikesByUserId(@Path("userId") long userId, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("message/like")
    Call<Liked> addLiked(@Body Liked item, @Header("Authorization") String token);

    //======================================================| Visitor
    @Headers("Content-Type: application/json")
    @POST("message/viewer")
    Call<Viewer> addViewer(@Body Viewer item, @Header("Authorization") String token);

    @GET("message/viewers/{userId}")
    Call<ArrayList<LikedDto>> getAllViewersByUserId(@Path("userId") long userId, @Header("Authorization") String token);

}
