package com.example.nicolemorris.lifestyle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nicolemorris.lifestyle.Model.User;

import java.util.Calendar;

public class ChangeProfileFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etName, etCity, etState;
    Spinner s_h_feet,s_h_inches,s_weight1, s_weight2, s_weight3,s_sex;
    Calendar date;
    String[] dataToPass;
    Button bSave, bDate;

    String w1="",w2="",w3="";

    String name, city, state, sex;
    int feet, inches, age, newFeet, newInches, weight, newWeight;


    User user;
    ChangeProfileOnDataPass userDataPasser;

    DatePickerDialog picker;

    public interface ChangeProfileOnDataPass{
        public void onChangeProfileDataPass(User user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            userDataPasser = (ChangeProfileOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ChangeProfileOnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);
        user = getArguments().getParcelable("user");
        etName = view.findViewById(R.id.et_name);
        name = user.getName();
        etName.setText(name);

        bDate = view.findViewById(R.id.b_birthday);
        bDate.setOnClickListener(this);

        etCity = view.findViewById(R.id.tv_city_hc);
        city = user.getCity();
        etCity.setText(city);

        etState = view.findViewById(R.id.tv_state_hc);
        state = user.getState();
        etState.setText(state);

        ArrayAdapter<CharSequence> num_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_array, android.R.layout.simple_spinner_item);

        s_h_feet  = (Spinner)view.findViewById(R.id.s_feet);
        s_h_feet.setOnItemSelectedListener(this);
        s_h_feet.setAdapter(num_adapter);
        feet = user.getFeet();


        s_h_inches = (Spinner)view.findViewById(R.id.s_inches);
        s_h_inches.setOnItemSelectedListener(this);
        s_h_inches.setAdapter(num_adapter);
        inches = user.getInches();

        s_weight1 = (Spinner)view.findViewById(R.id.s_weight1);
        s_weight1.setOnItemSelectedListener(this);
        s_weight1.setAdapter(num_adapter);

        s_weight2 = (Spinner)view.findViewById(R.id.s_weight2);
        s_weight2.setOnItemSelectedListener(this);
        s_weight2.setAdapter(num_adapter);

        s_weight3 = (Spinner)view.findViewById(R.id.s_weight3);
        s_weight3.setOnItemSelectedListener(this);
        s_weight3.setAdapter(num_adapter);
        weight = user.getWeight();

        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        s_sex = (Spinner)view.findViewById(R.id.s_sex);
        s_sex.setOnItemSelectedListener(this);
        s_sex.setAdapter(gender_adapter);
        sex = user.getSex();

        bSave = view.findViewById(R.id.b_save);
        bSave.setOnClickListener(this);

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (parent.getId()){
            case R.id.s_feet: {
                newFeet = Integer.parseInt((String)parent.getItemAtPosition(pos));
                break;
            }
            case R.id.s_inches: {
                newInches = Integer.parseInt((String)parent.getItemAtPosition(pos));
                break;
            }
            case R.id.s_weight1: {
                w1 = (String)parent.getItemAtPosition(pos);
                break;
            }
            case R.id.s_weight2: {
                w2 = (String)parent.getItemAtPosition(pos);
                break;
            }
            case R.id.s_weight3: {
                w3 = (String)parent.getItemAtPosition(pos);
                break;
            }
            case R.id.s_sex: {
                sex = (String)parent.getItemAtPosition(pos);
                break;
            }
            default:{
                System.out.println("Nothing selected");
            }
        }

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_save: {
                name = etName.getText().toString().trim();
                city = etCity.getText().toString();
                state = etState.getText().toString();

                if(name.equals("") || city.equals("") || state.equals("")){
                    Toast.makeText(getContext(), "You have empty fields!", Toast.LENGTH_SHORT).show();
                }

                if(newFeet!=0) feet = newFeet;
                if(newInches != 0) inches = newInches;
                newWeight = Integer.parseInt(w1+w2+w3);
                if(newWeight != 0) weight = newWeight;
                user = new User(name, age, feet, inches, city, state, weight, sex);
                System.out.println(user.getName());
                System.out.println(user.getAge());
                System.out.println(user.getFeet());
                System.out.println(user.getInches());
                System.out.println(user.getCity());
                System.out.println(user.getState());
                System.out.println(user.getWeight());
                System.out.println(user.getSex());
                userDataPasser.onChangeProfileDataPass(user);
                break;
            }
            case R.id.b_birthday:{
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar c = Calendar.getInstance();
                                c.set(year, monthOfYear, dayOfMonth);
                                date = c;
                                Calendar today = Calendar.getInstance();
                                age = today.get(Calendar.YEAR) - date.get(Calendar.YEAR);
                                if (today.get(Calendar.DAY_OF_YEAR) < date.get(Calendar.DAY_OF_YEAR)) {
                                    age--;
                                }
                            }
                        }, year, month, day);
                picker.show();
                break;
            }
        }
    }




}
