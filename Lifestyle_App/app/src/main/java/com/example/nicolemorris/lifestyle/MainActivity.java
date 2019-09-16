package com.example.nicolemorris.lifestyle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nicolemorris.lifestyle.Model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements BottomButtons.OnBottomDataPass, ReviewFragment.ReviewOnDataPass,
        ChangeGoalFragment.ChangeGoalOnDataPass, GoalsFragment.GoalsOnDataPass, ChangeProfileFragment.ChangeProfileOnDataPass {

    User u = new User("Andrew Android", 24, 5, 8, "Lehi", "Utah", 160, "Male");
    //    User u;
    String username;
    int user_choice;
    double height_inches = 72;
    double weight_pounds = 105;
    boolean hasGoal = false;

    ReviewFragment pf;
    GoalsFragment gf;
    ChangeGoalFragment cgf;
    BmiFragment bf;
    WeatherFragment wf;
    HelpFragment hf;
    ChangeProfileFragment cpf;

    int goal, act_level, goal_amount;

    //variables for find-a-hike
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    String latitude, longitude;

    //Add user or update user
    List<User> users;

    //File information
    FileOutputStream out;
    FileInputStream in;
    String fileName = "user_profile";
    String folder = "profile_images/";

    private FusedLocationProviderClient fusedLocationClient;
    Double lat = 0.0;
    Double longi = 0.0;
    String city = "default city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (getIntent().getExtras() != null) {
            user_choice = getIntent().getExtras().getInt("CHOICE");
        }


        System.out.println(user_choice);
        //Add permission for getting access to the current location
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        if (u == null) {
            Intent messageIntent = new Intent(this, NewUserActivity.class);
            this.startActivity(messageIntent);

        } else if (user_choice != 0) {

            changeFragments();

        } else {

            Intent messageIntent = new Intent(this, HomeActivity.class);
            this.startActivity(messageIntent);
        }

    }

    @Override
    public void onBottomDataPass(int data) {

        user_choice = data;
        changeFragments();

    }

    @Override
    public void onChangeGoalDataPass(int g, int l, int a) {
        hasGoal = true;
        u.setGoal(g, l, a);
        changeFragments();
    }

//    @Override
////    public void onChangeGoalDataPass(){
////        hasGoal = true;
////        changeFragments();
////    }

    @Override
    public void onGoalsDataPass() {
        hasGoal = false;
        changeFragments();
    }

    @Override
    public void onReviewDataPass() {
        user_choice = 7;
        changeFragments();
    }

    @Override
    public void onChangeProfileDataPass(User user) {
        u = user;
    }

    private void changeFragments() {

        //Find each frame layout, replace with corresponding fragment
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (user_choice == 1) {

            //Launch profile information
            pf = new ReviewFragment();

            //Put this into a bundle
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putParcelable("user", u);
            pf.setArguments(fragmentBundle);

            fTrans.replace(R.id.fl_frag_ph_2, pf, "Profile");

        } else if (user_choice == 2) {
            if (u.checkGoal() && hasGoal) {
                //Launch fitness goals
                gf = new GoalsFragment();
                Bundle sentData = new Bundle();
                sentData.putInt("Goal", u.getGoal());
                sentData.putInt("Act_Level", u.getAct_level());
                sentData.putInt("Amount", u.getGoal());
                gf.setArguments(sentData);
                fTrans.replace(R.id.fl_frag_ph_2, gf, "Goals");
            } else {
                //Launch change fitness goals
                cgf = new ChangeGoalFragment();
                fTrans.replace(R.id.fl_frag_ph_2, cgf, "Goals");
            }

        } else if (user_choice == 3) {

            bf = new BmiFragment();

            //Send data to it
            Bundle sentData = new Bundle();
            sentData.putDouble("HEIGHT", height_inches);
            sentData.putDouble("WEIGHT", weight_pounds);
            bf.setArguments(sentData);

            //Launch bmi
            fTrans.replace(R.id.fl_frag_ph_2, bf, "BMI");


        } else if (user_choice == 4) {

            findHikeAround();

        } else if (user_choice == 5) {

            locateForWeather();

            wf = new WeatherFragment();
            Bundle sentData = new Bundle();
            sentData.putString("city",city);
            wf.setArguments(sentData);
            fTrans.replace(R.id.fl_frag_ph_2,wf,"Weather");

        } else if (user_choice == 6){

            //Launch help
            hf = new HelpFragment();
            fTrans.replace(R.id.fl_frag_ph_2,hf,"Help");

        } else if (user_choice == 7) {
            cpf = new ChangeProfileFragment();
            //Put this into a bundle
            Bundle fragmentBundle = new Bundle();
            fragmentBundle.putParcelable("user",u);
            cpf.setArguments(fragmentBundle);

            fTrans.replace(R.id.fl_frag_ph_2,cpf,"Profile");

        }

        fTrans.replace(R.id.fl_frag_ph_1,new HeaderFragment(),"Header");
        fTrans.replace(R.id.fl_frag_ph_3,new BottomButtons(),"Choices");
        fTrans.commit();
    }

    public void locateForWeather(){

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Check gps is enable or not
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Enable gps
            OnGPS();
        }
        else
        {
            //Get latitude and longitude
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            }else{
                Geocoder geocoder = new Geocoder(this);

                Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (LocationGps !=null)
                {
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(LocationGps.getLatitude(), LocationGps.getLongitude(), 1);
                        city = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (LocationNetwork !=null)
                {
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(LocationGps.getLatitude(), LocationGps.getLongitude(), 1);
                        city = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (LocationPassive !=null)
                {
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(LocationGps.getLatitude(), LocationGps.getLongitude(), 1);
                        city = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    city = "Salt Lake City";
                    Log.d("failLoc","fail to locate you for weather, using SLC");
                }
            }

//            Geocoder geocoder = new Geocoder(this);
//            try {
//                List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);
//                city = addresses.get(0).getLocality();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    private void findHikeAround(){
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
            getHikeResult();
        }
    }

    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
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

    private void getHikeResult(){
        Uri searchUri = Uri.parse("geo:"+latitude+","+longitude+"?q=" + "hike");

        //Create the implicit intent
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

        //If there's an activity associated with this intent, launch it
        if(mapIntent.resolveActivity(getPackageManager())!=null){
            startActivity(mapIntent);
        }
    }

    public String serializeUser(User user){
        String content = user.getName()+","+user.getAge()+","+user.getName()+","+user.getState()+","+user.getFeet()+","+
                user.getInches()+","+user.getWeight()+","+user.getSex()+"\n";
        return content;
    }


    private void saveUserProfile(User user){
        try {
            out = openFileOutput(fileName, MODE_PRIVATE);
            String fileContents = serializeUser(user);
            out.write(fileContents.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            FileOutputStream fileOutputStream = openFileOutput("Tutorial File.txt", MODE_PRIVATE);
//            fileOutputStream.write(textToSave.getBytes());
//            fileOutputStream.close();
//
//            Toast.makeText(getApplicationContext(), "Text Saved", Toast.LENGTH_SHORT).show();
//
//            inputField.setText("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void updateUserProfile(User user){
        try {
            in = openFileInput(fileName);
            String temp = "";
            Scanner sc = new Scanner((InputStream)in);
            while(sc.hasNextLine()){
                String next = sc.nextLine();
                String currName = next.substring(0, next.indexOf(","));
                if(user.getName().equals(currName)){
                    temp += serializeUser(user);
                }else{
                    temp += next;
                }
            }
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
            out.write(temp.getBytes());
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveProfileImage(Bitmap profileImage){
        ContextWrapper cw = new ContextWrapper(this);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(folder, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,username+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            profileImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


