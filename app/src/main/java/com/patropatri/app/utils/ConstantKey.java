package com.patropatri.app.utils;

import com.patropatri.app.R;
import com.patropatri.app.models.Comment;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.Photo;
import com.patropatri.app.models.User;

import java.util.ArrayList;

public class ConstantKey {

    public static ArrayList<User> getUsers() {
        ArrayList<User> mArrayList = new ArrayList<>();
        mArrayList.add(new User("Mohammad", "Ali", "Dhaka"));
        mArrayList.add(new User("Barsha", "Ahnaf", "Chittagong"));
        mArrayList.add(new User("Salman", "Rahman", "Rangpur"));
        mArrayList.add(new User("Ashfakur", "Talukdar", "Dhaka"));
        mArrayList.add(new User("Mohammad", "Solaiman", "Dhaka"));
        return mArrayList;
    }

    public static ArrayList<Photo> getUserPhotos() {
        ArrayList<Photo> mArrayList = new ArrayList<>();
        mArrayList.add(new Photo(1, R.drawable.ic_add_photo));
        mArrayList.add(new Photo(2, R.drawable.ic_add_photo));
        mArrayList.add(new Photo(3, R.drawable.ic_add_photo));
        mArrayList.add(new Photo(4, R.drawable.ic_add_photo));
        mArrayList.add(new Photo(5, R.drawable.ic_add_photo));
        return mArrayList;
    }

    public static ArrayList<Notification> getNotifications() {
        ArrayList<Notification> mArrayList = new ArrayList<>();
        mArrayList.add(new Notification("LIKED"));
        mArrayList.add(new Notification("COMMENT"));
        mArrayList.add(new Notification("MESSAGE"));
        mArrayList.add(new Notification("LIKED"));
        mArrayList.add(new Notification("MESSAGE"));
        return mArrayList;
    }

    public static ArrayList<Message> getMessages() {
        ArrayList<Message> mArrayList = new ArrayList<>();
        mArrayList.add(new Message("Mohammad Ali", "Very Nice!"));
        mArrayList.add(new Message("Barsha Ahnaf", "Beautiful Nice!"));
        mArrayList.add(new Message("Salman Rahman", "Woow Nice!"));
        mArrayList.add(new Message("Ashfakur Talukdar", "Good Resume!"));
        mArrayList.add(new Message("Mohammad Solaiman", "Very Nice!"));
        return mArrayList;
    }

    public static ArrayList<Comment> getComments() {
        ArrayList<Comment> mArrayList = new ArrayList<>();
        mArrayList.add(new Comment("Mohammad Ali", "Very Nice!"));
        mArrayList.add(new Comment("Barsha Ahnaf", "Beautiful Nice!"));
        mArrayList.add(new Comment("Salman Rahman", "Woow Nice!"));
        mArrayList.add(new Comment("Ashfakur Talukdar", "Good Resume!"));
        mArrayList.add(new Comment("Mohammad Solaiman", "Very Nice!"));
        return mArrayList;
    }

}
