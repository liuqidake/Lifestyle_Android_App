package com.example.nicolemorris.lifestyle.Model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<User> jsonData;
    private UserRepo mUserRepo;

    public UserViewModel(Application application){
        super(application);
        mUserRepo = new UserRepo(application);
        jsonData = mUserRepo.getData();
    }
    public void seUser(User user){
        mUserRepo.setUser(user);
    }

    public LiveData<User> getData(){
        return jsonData;
    }


}
