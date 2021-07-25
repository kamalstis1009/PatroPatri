package com.patropatri.app.utils;

import com.patropatri.app.R;
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

}
