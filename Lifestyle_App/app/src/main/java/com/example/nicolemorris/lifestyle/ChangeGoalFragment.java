package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ChangeGoalFragment extends Fragment
        implements View.OnClickListener {

    ChangeGoalOnDataPass mDataPasser;
    Button bSetGoal;
    Spinner sGoal, sActLevel, sAmount;

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

        //Set spinners
        ArrayAdapter<CharSequence> goal_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.goal_choices, android.R.layout.simple_spinner_item);
        sGoal = view.findViewById(R.id.s_goal);
        sGoal.setAdapter(goal_adapter);

        ArrayAdapter<CharSequence> act_level_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_levels, android.R.layout.simple_spinner_item);
        sActLevel = view.findViewById(R.id.s_act_level);
        sActLevel.setAdapter(act_level_adapter);

        ArrayAdapter<CharSequence> amt_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weight_amount, android.R.layout.simple_spinner_item);
        sAmount = view.findViewById(R.id.s_amount);
        sAmount.setAdapter(amt_adapter);

        //Set buttons
        bSetGoal = view.findViewById(R.id.b_set_goal);
        bSetGoal.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_set_goal: {

                /*
                NEED TO SAVE THE DATA FIRST (ADD)
                 */


                //Tell view data was saved
                mDataPasser.onChangeGoalDataPass();
                break;
            }
        }
    }

}
