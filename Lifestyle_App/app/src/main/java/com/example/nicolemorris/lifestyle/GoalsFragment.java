package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GoalsFragment extends Fragment
        implements View.OnClickListener {

    GoalsOnDataPass mDataPasser;
    Button bChangeGoal;
    TextView tvCalAmt, tvGoalTxt, tvGoalAmt, tvGoalHC;
    int user_weight = 72; //Weight of user
    int user_height = 175; //Height of user
    boolean userIsMale = true; //Male or female
    int user_age = 27; //Age of user
    int goal; //0 = lose weight, 1 = maintain weight, 2 = gain weight
    int act_level; //0 = sedentary, 1 = light, 2 = moderate, 3 = very, 4 = extremely
    int weight_amt; //If goal to lose or gain weight, amount to lose or gain

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

        //Get arguments
        goal = getArguments().getInt("Goal");
        act_level = getArguments().getInt("Act_Level");
        weight_amt = getArguments().getInt("Amount");

        tvGoalTxt = view.findViewById(R.id.tv_goal_txt_d);
        tvGoalHC = view.findViewById(R.id.tv_goal_hc);

        tvCalAmt = view.findViewById(R.id.tv_cal_amt_d);
        tvCalAmt.setText(Long.toString(Math.round(calcCalories())));

        tvGoalAmt = view.findViewById(R.id.tv_goal_amt_d);
        tvGoalAmt.setText(Integer.toString(weight_amt));


        bChangeGoal = view.findViewById(R.id.b_change_goal);
        bChangeGoal.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_change_goal: {
                String cal = tvCalAmt.getText().toString().trim();
                String goal = tvGoalAmt.getText().toString().trim();
                if(cal.equals("")){
                    Toast.makeText(getContext(), "Please input how many calories to eat today", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(goal == null){
                    Toast.makeText(getContext(), "Please choose your goal", Toast.LENGTH_SHORT).show();
                    break;
                }
                mDataPasser.onGoalsDataPass();
                break;
            }
        }
    }

    private double calcCalories(){

        //Daily calorie requirement to maintain weight
        double calories = calcDailyCalToMaintain(calcBMR());

        //Goal lose weight
        if(goal==0){
            tvGoalTxt.setText("Lose");
            tvGoalHC.setText("pounds this week");
            calories = calories - (weight_amt*500);

        }

        //Goal gain weight
        if (goal==2) {
            tvGoalTxt.setText("Gain");
            tvGoalHC.setText("pounds this week");
            calories = calories + (weight_amt*500);

        }

        return calories;
    }

    private double calcBMR(){
        double bmr = 0.0;

        if(userIsMale){
            bmr = 66 + (6.3 * user_weight) + (12.9 * user_height) - (6.8 * user_age);
        } else {
            bmr = 655 + (4.3 * user_weight) + (4.7 * user_height) - (4.7 * user_age);
        }
        return bmr;
    }

    private double calcDailyCalToMaintain(double bmr){

        if(act_level == 0) {
            //sedentary
            return bmr * 1.2;

        } else if(act_level == 1) {
            //light active
            return bmr * 1.375;

        } else if(act_level == 2) {
            //moderate active
            return bmr * 1.55;

        } else if(act_level == 3) {
            //very active
            return bmr * 1.725;

        } else {
            //extremely active
            return bmr * 1.9;
        }
    }

}
