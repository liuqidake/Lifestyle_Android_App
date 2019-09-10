package com.example.nicolemorris.lifestyle;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class NameAgeFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener{

    private Button bDate,bNext;
    private EditText tname;
    String name;
    Calendar date;
    NameAgeOnDataPass mDataPasser;

    //Callback interface
    public interface NameAgeOnDataPass{
        public void onNameAgeDataPass(Calendar data, String name);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (NameAgeOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_age, container, false);
        bDate = view.findViewById(R.id.b_birthday);
        bDate.setOnClickListener(this);

        tname = view.findViewById(R.id.et_name);

        bNext = view.findViewById(R.id.b_next);
        bNext.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.b_birthday:{
                showTimePickerDialog(view);
                break;
            }

            case R.id.b_next: {

                onDateSet((DatePicker)getView(), 0000, 0, 0);
                mDataPasser.onNameAgeDataPass(date,name);
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
        this.name = tname.getText().toString();
    }

    private void showTimePickerDialog(View v) {
        DialogFragment newFragment = new NameAgeFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

}
