package com.sauloaguiar.neonapplication.data;

/**
 * Created by sauloaguiar on 11/25/16.
 */

public class Friend {

    private int friendId;
    private String name;
    private String phone;
    private int imagePath;

    public Friend(int id, String name, String phone, int imagePath) {
        this.friendId = id;
        this.name = name;
        this.phone = phone;
        this.imagePath = imagePath;
    }

    public int getImageResource() {
        return imagePath;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return friendId;
    }

    public String getStringId(){
        return String.valueOf(friendId);
    }

}
