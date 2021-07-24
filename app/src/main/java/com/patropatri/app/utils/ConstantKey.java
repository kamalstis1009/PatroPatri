package com.patropatri.app.utils;

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

}
