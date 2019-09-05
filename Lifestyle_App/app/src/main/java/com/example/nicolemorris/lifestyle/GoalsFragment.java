package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GoalsFragment extends Fragment
        implements View.OnClickListener {

    GoalsOnDataPass mDataPasser;
    Button bChangeGoal;

    //Callback interface
    public interface GoalsOnDataPass{
        public void onGoalsDataPass();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (GoalsOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals, container, false);

        bChangeGoal = view.findViewById(R.id.b_change_goal);
        bChangeGoal.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_change_goal: {
                mDataPasser.onGoalsDataPass();
                break;
            }
        }
    }

}
