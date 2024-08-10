package com.example.herambtinder.Models;

public class swipeProfile {

    int profileImage;
    String Name;
    String Age;
    String bio;

    public swipeProfile(int profileImage, String name, String age, String bio) {
        this.profileImage = profileImage;
        Name = name;
        Age = age;
        this.bio = bio;
    }


    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
