package com.patropatri.app.utils;

import java.util.ArrayList;

public class ConstantKey {

    //public static final String SERVER_URL = "http://114.31.10.20:5050/patropatri/api/v1/"; //Cloud
    //public static final String SERVER_URL = "http://192.168.0.174:5050/patropatri/api/v1/"; //Local Tomcat
    public static final String SERVER_URL = "http://10.0.2.2:8080/api/v1/"; //Localhost IntelliJ IDEA
    public static final String RESOURCE_URL = SERVER_URL + "file/download/";

    public static final String SOCKET_SERVER_URL = "ws://10.0.2.2:8080/stomp/websocket";
    public static final String PLACEHOLDER = "placeholder";
    public static final String CHAT = "/chat";
    // replace {@code placeholder} with user id
    public static final String CHAT_RESPONSE = "/user/" + PLACEHOLDER + "/msg";

    public static ArrayList<String> getYears(String title, int current) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(title);
        for (int i=0; i<current - 1950; i++) {
            arrayList.add( (i+1950) + "");
        }
        return arrayList;
    }


    /*
    INSERT INTO patropatri.notifications (id, created_at, is_enabled, receiver_id, sender_id, title, type)
    VALUES (1, '2020-04-27T13:39:28', true, 1, 1, 'Hello', 'LIKED');

    '1', '2021-08-11', 'Jessore', '0', 'Subra Systems Ltd.', 'Karwan Bazar, Dhaka, Bangladesh', 'www.subrasystems.com.bd', 'Fair', '2021-08-10 20:15:01.485000', '1', 'Software Engineer', 'Madaripur', 'mk@gmail.com', 'bibahabd-vue', 'Mustofa Kamal', 'Male', '5.4', NULL, 'IMG_20210810_202307_691.jpg', 'bibahabd-vue', '0', '0', '0', 'Never Married', 'bibahabd-vue', 'Kachukhet, Dhaka, Bangladesh', '01914141707', 'Kachukhet, Dhaka Bangladesh', 'Job Holder', 'Muslim', 'bibahabd-vue', NULL, NULL, NULL, '60 KG'
    */

}
