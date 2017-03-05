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

    private static final String str = "[{\"title\":\"Howell\",\"desc\":\"Ea consectetur labore nostrud ex qui.\",\"course\":\"Jennings\",\"start\":\"2017-02-21\",\"end\":\"2017-02-26\"},{\"title\":\"Hess\",\"desc\":\"Eiusmod deserunt velit irure qui ullamco cillum culpa voluptate et incididunt minim nostrud exercitation officia.\",\"course\":\"Salazar\",\"start\":\"2017-02-22\",\"end\":\"2017-02-26\"},{\"title\":\"Stuart\",\"desc\":\"Labore reprehenderit id aute irure eu dolore in in.\",\"course\":\"Barlow\",\"start\":\"2017-02-21\",\"end\":\"2017-03-01\"},{\"title\":\"Eula\",\"desc\":\"Eu sunt cupidatat dolor mollit aliquip officia cillum esse esse occaecat nisi duis nisi cillum.\",\"course\":\"Rowe\",\"start\":\"2017-02-23\",\"end\":\"2017-02-28\"},{\"title\":\"Juliana\",\"desc\":\"Sunt eu ea esse qui ea fugiat.\",\"course\":\"Dotson\",\"start\":\"2017-02-23\",\"end\":\"2017-02-26\"},{\"title\":\"Cabrera\",\"desc\":\"Eu sunt exercitation nulla commodo.\",\"course\":\"Carson\",\"start\":\"2017-02-20\",\"end\":\"2017-02-28\"},{\"title\":\"Abigail\",\"desc\":\"Nulla magna deserunt eiusmod sit dolor id in velit ut in consequat laboris et.\",\"course\":\"Mathis\",\"start\":\"2017-02-24\",\"end\":\"2017-02-27\"}]";

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
