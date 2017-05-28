package com.chirag.homeworkclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;consectetur. Suspendisse pulvinar elementum diam in facilisis. Suspendisse cursus, mag
import java.util.Calendar;
import java.util.List;
import java.sql.Date;


/**
 * Created by spafindoople on 2/12/17.
 */
public class DataManager {
    public List<Assignment> getAssignments(int year, int month, int day) {
        List<Assignment> assignments = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(str);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month-1,day);
            Date todayDate = new Date(calendar.getTimeInMillis());
            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject assignJson = jsonArray.getJSONObject(i);
                Date startDate = getDate(assignJson.getString("start"));
                Date endDate = getDate(assignJson.getString("end"));
                Assignment assign = new Assignment(
                        assignJson.getString("title"),
                        assignJson.getString("desc"),
                        assignJson.getString("course"),
                        startDate,
                        endDate
                        );
                if(DateUtil.getDay(todayDate) >= DateUtil.getDay(startDate) && DateUtil.getDay(todayDate) <= DateUtil.getDay(endDate)) {
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





























