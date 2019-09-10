package com.example.nicolemorris.lifestyle.Model;


public class User {
    int age, sex;
    String name,city, state, height, weight;

    public User(){

    }
    public User(int id, String name, int age, String city, String state, String height, String weight, int sex){
        this.name = name;
        this.age = age;
        this.city = city;
        this.state = state;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }

}
