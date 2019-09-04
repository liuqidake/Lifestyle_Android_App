package com.example.nicolemorris.lifestyle;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;

public class NameAgeFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    private Button mButton;
    java.util.Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_age, container, false);
        mButton = (Button)view.findViewById(R.id.b_birthday);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.b_birthday:{
                showTimePickerDialog(view);
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
        this.date =  calendar.getTime();
    }

    private void showTimePickerDialog(View v) {
        DialogFragment newFragment = new NameAgeFragment();
        newFragment.show(getFragmentManager(), "timePicker");
        onDateSet((DatePicker) v,2019,9,3);
    }

}
