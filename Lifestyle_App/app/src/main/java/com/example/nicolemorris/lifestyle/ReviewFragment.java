package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReviewFragment extends Fragment
        implements View.OnClickListener {

    Button bEditProfile;
    ReviewOnDataPass mDataPasser;

    //Callback interface
    public interface ReviewOnDataPass {
        public void onReviewDataPass(int choice);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (ReviewOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        bEditProfile = view.findViewById(R.id.b_edit_profile);
        bEditProfile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.b_edit_profile:{
                mDataPasser.onReviewDataPass(5);
                break;
            }

            case R.id.b_finish: {
                mDataPasser.onReviewDataPass(6);
                break;
            }
        }
    }

}
