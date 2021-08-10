package com.patropatri.app.utils;

import com.patropatri.app.R;
import com.patropatri.app.models.Comment;
import com.patropatri.app.models.Message;
import com.patropatri.app.models.Notification;
import com.patropatri.app.models.Photo;
import com.patropatri.app.models.User;

import java.util.ArrayList;

public class ConstantKey {

    //public static final String SERVER_URL = "http://36.255.68.34:8076/"; //Cloud
    public static final String SERVER_URL = "http://192.168.0.174:5050/patropatri/api/v1/"; //Local Tomcat
    //public static final String SERVER_URL = "http://10.0.2.2:8080/api/v1/"; //Localhost IntelliJ IDEA
    public static final String RESOURCE_URL = SERVER_URL + "file/download/";

    public static ArrayList<User> getUsers() {
        ArrayList<User> mArrayList = new ArrayList<>();
        /*mArrayList.add(new User("Mohammad", "Ali", "Dhaka"));
        mArrayList.add(new User("Barsha", "Ahnaf", "Chittagong"));
        mArrayList.add(new User("Salman", "Rahman", "Rangpur"));
        mArrayList.add(new User("Ashfakur", "Talukdar", "Dhaka"));
        mArrayList.add(new User("Mohammad", "Solaiman", "Dhaka"));*/
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
        Notification model = new Notification();
        model.setTitle("LIKED");
        mArrayList.add(model);
        Notification model2 = new Notification();
        model2.setTitle("COMMENT");
        mArrayList.add(model2);
        Notification model3 = new Notification();
        model3.setTitle("MESSAGE");
        mArrayList.add(model3);
        Notification model4 = new Notification();
        model4.setTitle("LIKED");
        mArrayList.add(model4);
        Notification model5 = new Notification();
        model5.setTitle("MESSAGE");
        mArrayList.add(model5);
        return mArrayList;
    }

    public static ArrayList<Message> getMessages() {
        ArrayList<Message> mArrayList = new ArrayList<>();
        mArrayList.add(new Message("Mohammad Ali", "Very Nice!"));
        mArrayList.add(new Message("Barsha Ahnaf", "Beautiful Nice!"));
        mArrayList.add(new Message("Salman Rahman", "Woow Nice!"));
        mArrayList.add(new Message("Ashfakur Talukdar", "Good Resume!"));
        mArrayList.add(new Message("Mohammad Solaiman", "Very Nice!"));
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
