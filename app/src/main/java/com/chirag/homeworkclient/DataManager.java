package com.chirag.homeworkclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Created by spafindoople on 2/12/17.
 */

public class DataManager {

    private static final String str = "[ { \"title\": \"Lynette\", \"desc\": \"Magna duis cillum eiusmod dolor non Lorem incididunt duis amet.\", \"course\": \"Harris\", \"start\": \"2017-04-06\", \"end\": \"2017-04-10\" }, { \"title\": \"Terrell\", \"desc\": \"Non tempor exercitation proident aute laborum.\", \"course\": \"Holder\", \"start\": \"2017-04-07\", \"end\": \"2017-04-14\" }, { \"title\": \"Berta\", \"desc\": \"Cupidatat laboris irure aliqua amet dolore commodo id consequat incididunt occaecat in.\", \"course\": \"Miller\", \"start\": \"2017-04-08\", \"end\": \"2017-04-14\" }, { \"title\": \"Bradshaw\", \"desc\": \"Elit non aliqua excepteur tempor mollit commodo mollit labore consectetur sunt exercitation do incididunt occaecat.\", \"course\": \"Gross\", \"start\": \"2017-04-05\", \"end\": \"2017-04-10\" }, { \"title\": \"Lowe\", \"desc\": \"Eiusmod ex ut magna ullamco ut nostrud anim reprehenderit excepteur incididunt culpa aute reprehenderit.\", \"course\": \"Talley\", \"start\": \"2017-04-07\", \"end\": \"2017-04-13\" } ]";

    public List<Assignment> getAssignments(int year, int month, int day) {
        List<Assignment> assignments = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(str);
            for(int i=0; i < jsonArray.length(); i++) {
                JSONObject assignJson = jsonArray.getJSONObject(i);
                Assignment assign = new Assignment(
                        assignJson.getString("title"),
                        assignJson.getString("desc"),
                        assignJson.getString("course"),
                        getDate(assignJson.getString("start")),
                        getDate(assignJson.getString("end"))
                        );
                assignments.add(assign);
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
