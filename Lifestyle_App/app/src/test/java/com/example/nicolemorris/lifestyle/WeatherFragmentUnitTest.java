package com.example.nicolemorris.lifestyle;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import  com.example.nicolemorris.lifestyle.NetworkUtils;

public class WeatherFragmentUnitTest {

    @Test
    public void getDataFromUrl_defaultLocation_nn() {
        String s = null;
        try {
            s = NetworkUtils.getDataFromURL(NetworkUtils.buildURLFromString("salt lake city"));
            // testing our default location won't fail
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(s);
    }

}
