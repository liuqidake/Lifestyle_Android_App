package com.example.nicolemorris.lifestyle.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

public class UserRepo {
    private final MutableLiveData<User> jsonData = new MutableLiveData<User>();
    private User mUser;

    UserRepo(Application application){
        loadData();
    }

    public void setUser(User user){
        mUser = user;
        loadData();
    }

    public MutableLiveData<User> getData() {
        return jsonData;
    }

    private void loadData(){
        new AsyncTask<User,Void,User>(){
            @Override
            protected User doInBackground(User... users) {
                return users[0];
            }

            @Override
            protected void onPostExecute(User user) {

                    jsonData.setValue(user);


            }
        }.execute(mUser);
    }
}
