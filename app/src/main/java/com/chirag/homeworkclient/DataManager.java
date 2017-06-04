package com.chirag.homeworkclient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;


/**
 * Created by spafindoople on 2/12/17.
 */
public class DataManager {
    NetworkManager mNetworkManager;
    JSONArray mAssignments;

    public DataManager(NetworkManager networkManager) {
        mNetworkManager = networkManager;
    }

    public void downloadAssignments() {
        String hi = mNetworkManager.getAssignmentPage();
        Log.i("Debug", hi);
        mAssignments = new JSONArray(
                AssignmentParser.getAssignments(hi)
        );

    }


    public List<Assignment> getAssignments(int year, int month, int day) {
        List<Assignment> assignments = new ArrayList<>();

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month-1,day);
            Date todayDate = new Date(calendar.getTimeInMillis());
            for(int i=0; i < mAssignments.length(); i++) {
                JSONObject assignJson = mAssignments.getJSONObject(i);
                Date startDate = getDate(assignJson.getString("start"));
                Date endDate = getDate(assignJson.getString("end"));
                Assignment assign = new Assignment(
                        assignJson.getString("title"),
                        assignJson.getString("desc"),
                        assignJson.getString("course"),
                        startDate,
                        endDate
                        );
                if(calendar.getTimeInMillis() >= startDate.getTime() &&
                        calendar.getTimeInMillis() <= endDate.getTime()) {
                    assignments.add(assign);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    private Date getDate(String s) {
        return Date.valueOf(s);
    }
}





























