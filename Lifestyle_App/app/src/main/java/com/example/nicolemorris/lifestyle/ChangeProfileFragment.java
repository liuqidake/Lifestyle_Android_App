package com.example.nicolemorris.lifestyle;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import java.util.List;

public class ChangeProfileFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etName, etCity, etState, etAge;
    Spinner s_h_feet,s_h_inches,s_weight1, s_weight2, s_weight3,s_sex;
    Calendar date;
    String[] dataToPass;
    Button bSave, bDate,bLocation;

    String w1="",w2="",w3="";

    String name, city, state, sex;
    int feet, inches, age, newFeet, newInches, weight, newWeight;


    User user;
    Bitmap profile_image;
    ChangeProfileOnDataPass userDataPasser;

    DatePickerDialog picker;

    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    Double latitude, longitude;

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
        //byte[] imageByte = getArguments().getByteArray("image");
        //profile_image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

        etName = view.findViewById(R.id.et_name);
        name = user.getName();
        etName.setText(name);

        bDate = view.findViewById(R.id.b_birthday);
        bDate.setOnClickListener(this);

        bLocation = view.findViewById(R.id.b_location);
        bLocation.setOnClickListener(this);

        etCity = view.findViewById(R.id.tv_city_hc);
        city = user.getCity();
        etCity.setText(city);

        etState = view.findViewById(R.id.tv_state_hc);
        state = user.getState();
        etState.setText(state);

        etAge = view.findViewById(R.id.et_age);
        age = user.getAge();
        etAge.setText(""+age);

        ArrayAdapter<CharSequence> num_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_array, android.R.layout.simple_spinner_item);


        s_h_feet  = (Spinner)view.findViewById(R.id.s_feet);
        s_h_feet.setOnItemSelectedListener(this);
        s_h_feet.setAdapter(num_adapter);
        int feetPosition = num_adapter.getPosition(""+user.getFeet());
        s_h_feet.setSelection(feetPosition);
        feet = user.getFeet();

        s_h_inches = (Spinner)view.findViewById(R.id.s_inches);
        s_h_inches.setOnItemSelectedListener(this);
        s_h_inches.setAdapter(num_adapter);
        int inchPosition = num_adapter.getPosition(""+user.getInches());
        s_h_inches.setSelection(inchPosition);
        inches = user.getInches();

        s_weight1 = (Spinner)view.findViewById(R.id.s_weight1);
        s_weight1.setOnItemSelectedListener(this);
        s_weight1.setAdapter(num_adapter);
        int w1Position = num_adapter.getPosition((user.getWeight()+"").substring(0, 1));
        s_weight1.setSelection(w1Position);

        s_weight2 = (Spinner)view.findViewById(R.id.s_weight2);
        s_weight2.setOnItemSelectedListener(this);
        s_weight2.setAdapter(num_adapter);
        int w2Position = num_adapter.getPosition((user.getWeight()+"").substring(1, 2));
        s_weight2.setSelection(w2Position);

        s_weight3 = (Spinner)view.findViewById(R.id.s_weight3);
        s_weight3.setOnItemSelectedListener(this);
        s_weight3.setAdapter(num_adapter);
        int w3Position = num_adapter.getPosition((user.getWeight()+"").substring(2));
        s_weight3.setSelection(w3Position);

        weight = user.getWeight();

        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        s_sex = (Spinner)view.findViewById(R.id.s_sex);
        s_sex.setOnItemSelectedListener(this);
        s_sex.setAdapter(gender_adapter);
        int genderPosition = num_adapter.getPosition(user.getSex());
        s_sex.setSelection(genderPosition);
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
                    break;
                }

                if(date == null){
                    age = user.getAge();
                }

                if(newFeet!=0) feet = newFeet;
                if(newInches != 0) inches = newInches;
                newWeight = Integer.parseInt(w1+w2+w3);
                if(newWeight != 0) weight = newWeight;
                user = new User(name, age, feet, inches, city, state, weight, sex);
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
                                etAge.setText(""+age);
                            }
                        }, year, month, day);
                picker.show();
                break;
            }
            case R.id.b_location:{
                findLocation();
                Geocoder g = new Geocoder(getContext());
                try {
                    List<Address> addresses = g.getFromLocation(latitude, longitude, 1);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    etCity.setText(city);
                    etState.setText(state);
                }catch(Exception e){
                    Toast.makeText(getActivity(), "Can't Get Your Location", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void findLocation(){
        locationManager=(LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //Check gps is enable or not
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Enable gps
            OnGPS();
        }
        else
        {
            //Get latitude and longitude and open map based on the location
            getLocation();
        }
    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                latitude=LocationGps.getLatitude();
                longitude=LocationGps.getLongitude();
            }
            else if (LocationNetwork !=null)
            {
                latitude=LocationNetwork.getLatitude();
                longitude=LocationNetwork.getLongitude();
            }
            else if (LocationPassive !=null)
            {
                latitude=LocationPassive.getLatitude();
                longitude=LocationPassive.getLongitude();
            }
            else
            {
                Toast.makeText(getActivity(), "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }




}
