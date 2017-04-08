package com.example.rasave01.room354v2;

import com.indooratlas.android.sdk.IALocation;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Locale.ENGLISH;

/**
 * Created by Radu Asavei on 27/03/2017.
 */
public class LogMessage {
    public String timeStamp;
    public IALocation currentLocation;
    public Calendar c;
    public float[] dist;

    public LogMessage(IALocation currentLocation, float[] dist){

        this.currentLocation = currentLocation;

        this.dist = dist;
        this.c = Calendar.getInstance();
        this.timeStamp = String.valueOf(c.get(Calendar.YEAR)
                        + "/" + c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
                        + "/" + c.get(Calendar.DAY_OF_MONTH)
                        + " at " + c.get(Calendar.HOUR_OF_DAY)
                        + ":" + c.get(Calendar.MINUTE)
                        + ":" + c.get(Calendar.SECOND)
        );
    }
}
