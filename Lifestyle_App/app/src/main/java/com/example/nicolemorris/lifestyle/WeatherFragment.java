package com.example.nicolemorris.lifestyle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private TextView mTvTemp;
    private TextView mTvHum;
    private WeatherData mWeatherData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        //super.onCreate(savedInstanceState);

        mTvTemp = view.findViewById(R.id.tv_temp_d);
        mTvHum = view.findViewById(R.id.tv_rain_d);

        String inputLocation = getArguments().getString("city");
        loadWeatherData(inputLocation);

        LoaderManager.getInstance(this).initLoader(SEARCH_LOADER, null, this);

        return view;
    }

    //Uniquely identify loader
    private static final int SEARCH_LOADER = 11;

    //Uniquely identify string you passed in
    public static final String URL_STRING = "query";

    private void loadWeatherData(String location){
        Bundle searchQueryBundle = new Bundle();
        searchQueryBundle.putString(URL_STRING,location);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        Loader<String> searchLoader = loaderManager.getLoader(SEARCH_LOADER);
        if(searchLoader==null){
            loaderManager.initLoader(SEARCH_LOADER,searchQueryBundle,this);
        }
        else{
            loaderManager.restartLoader(SEARCH_LOADER,searchQueryBundle,this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<String>(getActivity()) { // changed from this to getActivity()
            private String mLoaderData;

            @Override
            protected void onStartLoading() {
                if(bundle==null){
                    return;
                }
                if(mLoaderData!=null){
                    //Cache data for onPause instead of loading all over again
                    //Other config changes are handled automatically
                    deliverResult(mLoaderData);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String location = bundle.getString(URL_STRING);
                URL weatherDataURL = NetworkUtils.buildURLFromString(location);
                String jsonWeatherData = null;
                try{
                    jsonWeatherData = NetworkUtils.getDataFromURL(weatherDataURL);
                    return jsonWeatherData;
                }catch(Exception e){
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String data) {
                mLoaderData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String jsonWeatherData) {
        if (jsonWeatherData!=null){
            try {
                mWeatherData = JSONWeatherUtils.getWeatherData(jsonWeatherData);
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            if(mWeatherData!=null) {
                String rain = (mWeatherData.getRain().getTime()==null)?"Not gonna rain today":mWeatherData.getRain().getTime();
                mTvTemp.setText("" + Math.round(mWeatherData.getTemperature().getTemp() - 273.15) + " C");
                mTvHum.setText("" + rain);
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}

class WeatherData {
    private CurrentCondition mCurrentCondition = new CurrentCondition();
    private Temperature mTemperature = new Temperature();
    private Wind mWind = new Wind();
    private Rain mRain = new Rain();
    private Snow mSnow = new Snow();
    private Clouds mClouds = new Clouds();


    public  class CurrentCondition {
        private long mWeatherId;
        private String mCondition;
        private String mDescr;
        private String mIcon;

        private double mPressure;
        private double mHumidity;

        public long getWeatherId() {
            return mWeatherId;
        }
        public void setWeatherId(long weatherId) {
            mWeatherId = weatherId;
        }
        public String getCondition() {
            return mCondition;
        }
        public void setCondition(String condition) {
            mCondition = condition;
        }
        public String getDescr() {
            return mDescr;
        }
        public void setDescr(String descr) {
            mDescr = descr;
        }
        public String getIcon() {
            return mIcon;
        }
        public void setIcon(String icon) {
            mIcon = icon;
        }
        public double getPressure() {
            return mPressure;
        }
        public void setPressure(double pressure) {
            mPressure = pressure;
        }
        public double getHumidity() {
            return mHumidity;
        }
        public void setHumidity(double humidity) {
            mHumidity = humidity;
        }
    }

    public class Temperature {
        private double mTemp;
        private double mMinTemp;
        private double mMaxTemp;

        public double getTemp() {
            return mTemp;
        }
        public void setTemp(double temp) {
            mTemp = temp;
        }
        public double getMinTemp() {
            return mMinTemp;
        }
        public void setMinTemp(double minTemp) {
            mMinTemp = minTemp;
        }
        public double getMaxTemp() {
            return mMaxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            mMaxTemp = maxTemp;
        }

    }

    public class Wind {
        private double mSpeed;
        private double mDeg;
        public double getSpeed() {
            return mSpeed;
        }
        public void setSpeed(double speed) {
            mSpeed = speed;
        }
        public double getDeg() {
            return mDeg;
        }
        public void setDeg(double deg) {
            mDeg = deg;
        }
    }

    public class Rain {
        private String mTime;
        private double mAmount;
        public String getTime() {
            return mTime;
        }
        public void setTime(String time) {
            mTime = time;
        }
        public double getAmount() {
            return mAmount;
        }
        public void setAmount(double amount) {
            mAmount = amount;
        }
    }

    public class Snow {
        private String mTime;
        private double mAmount;
        public String getTime() {
            return mTime;
        }
        public void setTime(String time) {
            mTime = time;
        }
        public double getAmount() {
            return mAmount;
        }
        public void setAmount(double amount) {
            mAmount = amount;
        }
    }

    public class Clouds {
        private long mPerc;

        public long getPerc() {
            return mPerc;
        }

        public void setPerc(long perc) {
            mPerc = perc;
        }
    }

    //Setters and Getters

    public void setCurrentCondition(CurrentCondition currentCondition){
        mCurrentCondition = currentCondition;
    }
    public CurrentCondition getCurrentCondition(){
        return mCurrentCondition;
    }

    public void setTemperature(Temperature temperature){
        mTemperature = temperature;
    }
    public Temperature getTemperature(){
        return mTemperature;
    }

    public void setWind(Wind wind){
        mWind = wind;
    }
    public Wind getWind(){
        return mWind;
    }

    public void setRain(Rain rain){
        mRain = rain;
    }
    public Rain getRain(){
        return mRain;
    }

    public void setSnow(Snow snow){
        mSnow = snow;
    }
    public Snow getSnow(){
        return mSnow;
    }

    public void setClouds(Clouds clouds){
        mClouds = clouds;
    }
    public Clouds getClouds(){
        return mClouds;
    }
}

class NetworkUtils {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String APPIDQUERY = "&appid=";
    private static final String app_id="99ea8382701bd7481e5ea568772f739a";

    public static URL buildURLFromString(String location){
        URL myURL = null;
        try{
            myURL = new URL(BASE_URL + location + APPIDQUERY + app_id);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return myURL;
    }

    public static String getDataFromURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }
}

class JSONWeatherUtils {
    public static WeatherData getWeatherData(String data) throws JSONException{
        WeatherData weatherData = new WeatherData();

        //Start parsing JSON data
        JSONObject jsonObject = new JSONObject(data); //Must throw JSONException

        WeatherData.CurrentCondition currentCondition = weatherData.getCurrentCondition();
        JSONObject jsonMain = jsonObject.getJSONObject("main");
        currentCondition.setHumidity(jsonMain.getInt("humidity"));
        currentCondition.setPressure(jsonMain.getInt("pressure"));
        weatherData.setCurrentCondition(currentCondition);

        //Get the temperature, wind and cloud data.
        WeatherData.Temperature temperature = weatherData.getTemperature();
        WeatherData.Wind wind = weatherData.getWind();
        WeatherData.Clouds clouds = weatherData.getClouds();
        temperature.setMaxTemp(jsonMain.getDouble("temp_max"));
        temperature.setMinTemp(jsonMain.getDouble("temp_min"));
        temperature.setTemp(jsonMain.getDouble("temp"));
        weatherData.setTemperature(temperature);

        return weatherData;
    }
}