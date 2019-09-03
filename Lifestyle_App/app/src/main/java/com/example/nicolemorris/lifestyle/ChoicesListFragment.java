package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChoicesListFragment extends Fragment
        implements View.OnClickListener {

    BottomButtons.OnDataPass mDataPasser;

    //Callback interface
    public interface OnDataPass{
        public void onDataPass(int data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (BottomButtons.OnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choices_list, container, false);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_profile: {
                mDataPasser.onDataPass(1);
                break;
            }

            case R.id.b_goals: {
                mDataPasser.onDataPass(2);
                break;
            }

            case R.id.b_bmi: {
                mDataPasser.onDataPass(3);
                break;
            }

            case R.id.b_hikes: {
                mDataPasser.onDataPass(4);
                break;
            }

            case R.id.b_weather: {
                mDataPasser.onDataPass(5);
                break;
            }

            case R.id.b_help: {
                mDataPasser.onDataPass(6);
                break;
            }


        }
    }

}
