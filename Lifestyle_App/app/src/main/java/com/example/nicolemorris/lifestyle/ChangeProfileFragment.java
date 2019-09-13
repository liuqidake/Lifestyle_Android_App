package com.example.nicolemorris.lifestyle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

import com.example.nicolemorris.lifestyle.Database.DatabaseHelper;
import com.example.nicolemorris.lifestyle.Model.User;

import java.util.Calendar;

public class ChangeProfileFragment extends DialogFragment
        implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener, View.OnClickListener {

    EditText etName, etCity, etState;
    Spinner s_h_feet,s_h_inches,s_w_pounds,s_sex;
    Calendar date;
    String[] dataToPass;
    Button bSave;

    String name, city, state, weight, sex;
    int feet, inches, age;

    //Add database
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);

        etName = view.findViewById(R.id.et_name);
        etName.setText(getArguments().get("name").toString());

        etCity = view.findViewById(R.id.et_city);
        etCity.setText(getArguments().get("city").toString());

        etState = view.findViewById(R.id.et_state);
        etState.setText(getArguments().get("state").toString());

        ArrayAdapter<CharSequence> num_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_array, android.R.layout.simple_spinner_item);

        s_h_feet  = view.findViewById(R.id.s_feet);
        s_h_feet.setAdapter(num_adapter);
        s_h_feet.setOnItemSelectedListener(this);

        s_h_inches = view.findViewById(R.id.s_inches);
        s_h_inches.setAdapter(num_adapter);
        s_h_inches.setOnItemSelectedListener(this);

        s_w_pounds = view.findViewById(R.id.s_weight);
        s_w_pounds.setAdapter(num_adapter);
        s_w_pounds.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        s_sex = view.findViewById(R.id.s_sex);
        s_sex.setAdapter(gender_adapter);
        s_sex.setOnItemSelectedListener(this);

        bSave = view.findViewById(R.id.b_save);
        bSave.setOnClickListener(this);

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (view.getId()){
            case R.id.s_feet: {
                feet = (Integer)parent.getItemAtPosition(pos);
            }
            case R.id.s_inches: {
                inches = (Integer)parent.getItemAtPosition(pos);
            }
            case R.id.s_weight: {
                weight = (String)parent.getItemAtPosition(pos);
            }
            case R.id.s_sex: {
                sex = (String)parent.getItemAtPosition(pos);
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
                name = etName.getText().toString();
                city = etCity.getText().toString();
                state = etState.getText().toString();

                if(name.equals("") || city.equals("") || state.equals("")){
                    Toast.makeText(getContext(), "You have empty fields!", Toast.LENGTH_SHORT).show();
                }
                Intent userIntent = new Intent(getActivity(), MainActivity.class);
                User user = new User(name, age, feet, inches, city, state, weight, sex);
                userIntent.putExtra("update user", user);
                this.startActivity(userIntent);
                break;
            }
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar calendar = Calendar.getInstance();
        calendar.set(view.getYear(), view.getMonth(), view.getDayOfMonth());
        this.date = calendar;
        age = Calendar.getInstance().get(Calendar.YEAR) - this.date.get(Calendar.YEAR);
    }




}
