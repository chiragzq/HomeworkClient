package com.chirag.homeworkclient;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;


/**
 * Created by spafindoople on 2/12/17.
 */
public class DataManager {
    NetworkManager mNetworkManager;
    JSONArray mAssignments;
    Map<String, Boolean> mDoneMap;
    Context mContext;

    public DataManager(NetworkManager networkManager, Context context) {
        mNetworkManager = networkManager;
        mDoneMap = new HashMap<>();
        mContext = context;
    }

    public void downloadAssignments() {
        String hi = mNetworkManager.getAssignmentPage();

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

    public void setAssignmentDone(String id, boolean done, Context context) throws Exception{ //onclick listener in mainactivity
        //on app load load done assingment files store in a map

        File file = new File(context.getFilesDir(), "done");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String[] split;
        while ((line = br.readLine()) != null) {
            split = line.split(",");
            mDoneMap.put(split[0], split[1].equals("true"));
        }

        mDoneMap.put(id, done);
        saveDones(mContext);
    }

    public void saveDones(Context context) throws Exception {
        //save map 2 file
        File file = new File(context.getFilesDir(), "done");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        StringBuilder builder = new StringBuilder();
        for(String id : mDoneMap.keySet()) {
            builder.append(id + "," + mDoneMap.get(id) + "\n");
        }
        fos.write(builder.toString().getBytes());
        fos.close();
    }

    public void loadDone(Context context) { //after file download
        try {
            File file = new File(context.getFilesDir(), "done");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] split;
            while ((line = br.readLine()) != null) {
                split = line.split(",");
                mDoneMap.put(split[0], split[1].equals("true"));
            }
        } catch(Exception e) {
                e.printStackTrace();
        }

    }

    private Date getDate(String s) {
        return Date.valueOf(s);
    }
}





























