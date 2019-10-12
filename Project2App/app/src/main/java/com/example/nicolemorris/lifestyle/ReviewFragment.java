package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nicolemorris.lifestyle.Model.User;

public class ReviewFragment extends Fragment
        implements View.OnClickListener {

    User u;
    Button bEditProfile;
    ReviewOnDataPass mDataPasser;
    TextView name, age, city, state, height, weight, sex;

    //Callback interface
    public interface ReviewOnDataPass {
        public void onReviewDataPass();
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

        u = getArguments().getParcelable("user");

        name = view.findViewById(R.id.tv_name_d);
        name.setText(u.getName());

        age = view.findViewById(R.id.tv_age_d);
        age.setText(Integer.toString(u.getAge()));

        city = view.findViewById(R.id.tv_city_d);
        city.setText(u.getCity());

        state = view.findViewById(R.id.tv_state_d);
        state.setText(u.getState());

        height = view.findViewById(R.id.tv_height_d);
        height.setText(Integer.toString(u.getFeet()) + " feet " + Integer.toString(u.getInches()) + " inches");

        weight = view.findViewById(R.id.tv_weight_d);
        weight.setText(Integer.toString(u.getWeight()) + " pounds");

        sex = view.findViewById(R.id.tv_sex_d);
        sex.setText(u.getSex());

        bEditProfile = view.findViewById(R.id.b_edit_profile);
        bEditProfile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.b_edit_profile:{
                mDataPasser.onReviewDataPass();
                break;
            }

//            case R.id.b_finish: {
//                mDataPasser.onReviewDataPass(6);
//                break;
//            }
        }
    }

}
