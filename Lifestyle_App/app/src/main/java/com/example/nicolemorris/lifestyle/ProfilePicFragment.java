package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class
ProfilePicFragment extends Fragment
        implements View.OnClickListener {

    Button bTakePic,bSelectPic,bNext;
    ProfilePicOnDataPass mDataPasser;
    String fileName;

    //Callback interface
    public interface ProfilePicOnDataPass{
        public void onProfilePicDataPass(String file_name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (ProfilePicOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_pic, container, false);

        bNext = view.findViewById(R.id.b_next);
        bNext.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){

            case R.id.b_next: {

                //NEED TO ADD DATE TO PASS FOR STORAGE :)
                mDataPasser.onProfilePicDataPass(fileName);

            }
        }
    }
}
