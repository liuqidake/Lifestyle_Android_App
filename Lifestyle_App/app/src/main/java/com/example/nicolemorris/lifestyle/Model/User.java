package com.example.nicolemorris.lifestyle.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    int age, feet, inches;
    String name,city, state, weight, sex;

    public User(){

    }
    public User(String name, int age, int feet, int inches, String city, String state, String weight, String sex){
        this.name = name;
        this.age = age;
        this.city = city;
        this.state = state;
        this.feet = feet;
        this.inches = inches;
        this.weight = weight;
        this.sex = sex;
    }

    protected User(Parcel in) {
        age = in.readInt();
        sex = in.readString();
        feet = in.readInt();
        inches = in.readInt();
        name = in.readString();
        city = in.readString();
        state = in.readString();
        weight = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName(){
        return this.name;
    }
    public int getAge(){return this.age;}
    public int getFeet(){return this.feet;}
    public int getInches(){return this.inches;}
    public String getCity(){return this.city;}
    public String getState(){return this.state;}
    public String getWeight(){return this.weight;}
    public String getSex(){return this.sex;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flags);
    }
}
