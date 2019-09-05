package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ChangeGoalFragment extends Fragment
        implements View.OnClickListener {

    ChangeGoalOnDataPass mDataPasser;
    Button bSetGoal;

    //Callback interface
    public interface ChangeGoalOnDataPass{
        public void onChangeGoalDataPass();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (ChangeGoalOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_goal, container, false);
        bSetGoal = view.findViewById(R.id.b_set_goal);
        bSetGoal.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_set_goal: {
                mDataPasser.onChangeGoalDataPass();
                break;
            }
        }
    }

}
