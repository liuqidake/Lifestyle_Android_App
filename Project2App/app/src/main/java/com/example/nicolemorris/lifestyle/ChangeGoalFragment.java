package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangeGoalFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ChangeGoalOnDataPass mDataPasser;
    Button bSetGoal;
    Spinner sGoal, sActLevel, sAmount;
    int goal; //0 = lose weight, 1 = maintain weight, 2 = gain weight
    int act_level; //0 = sedentary, 1 = light, 2 = moderate, 3 = very, 4 = extremely
    int weight_amt; //If goal to lose or gain weight, amount to lose or gain

    //Callback interface
    public interface ChangeGoalOnDataPass{
        public void onChangeGoalDataPass(int goal, int act_level, int amount);
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
        sGoal.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> act_level_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activity_levels, android.R.layout.simple_spinner_item);
        sActLevel = view.findViewById(R.id.s_act_level);
        sActLevel.setAdapter(act_level_adapter);
        sActLevel.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> amt_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.weight_amount, android.R.layout.simple_spinner_item);
        sAmount = view.findViewById(R.id.s_amount);
        sAmount.setAdapter(amt_adapter);
        sAmount.setOnItemSelectedListener(this);

        //Set buttons
        bSetGoal = view.findViewById(R.id.b_set_goal);
        bSetGoal.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (parent.getId()) {
            case R.id.s_goal: {
                goal = parent.getSelectedItemPosition();
                break;
            }
            case R.id.s_act_level: {
                act_level = parent.getSelectedItemPosition();
                break;
            }
            case R.id.s_amount: {
                weight_amt = parent.getSelectedItemPosition() + 1;
                if( weight_amt>2){
                    Toast.makeText(getContext(), "amount over 2 pounds!", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            }

        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_set_goal: {

                /*
                NEED TO SAVE THE DATA FIRST (ADD)
                 */

                System.out.println("Goal = " + goal);
                System.out.println("Level = " + act_level);
                System.out.println("Amount = " + weight_amt);

                //Tell view data was saved
                mDataPasser.onChangeGoalDataPass(goal,act_level,weight_amt);
                break;
            }
        }
    }

}
