package com.example.nicolemorris.lifestyle.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nicolemorris.lifestyle.R;

/*
 * Displays fragment data
 */
public class StepCounterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_step_counter, container, false);
        return view;
    }



}
