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
        cleanUpDones();
        loadDone();
    }

    public List<Assignment> getAssignments(int year, int month, int day) {
        List<Assignment> doneAssignments = new ArrayList<>();
        List<Assignment> notDoneAssignments = new ArrayList<>();

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day);
            for (int i = 0; i < mAssignments.length(); i++) {
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
                if (calendar.getTimeInMillis() >= startDate.getTime() &&
                        calendar.getTimeInMillis() <= endDate.getTime()) {
                    if(isAssignmentDone(assign)) {
                        doneAssignments.add(assign);
                    } else {
                        notDoneAssignments.add(assign);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notDoneAssignments.addAll(doneAssignments);
        return notDoneAssignments;
    }

    public boolean isAssignmentDone(Assignment assignment) {
        Boolean ret = mDoneMap.get(assignment.toString());
        return ret == null ? false : ret;
    }

    public void toggleDone(Assignment assignment) throws Exception { //onclick listener in mainactivity
        loadDone();
        mDoneMap.put(assignment.toString(), !isAssignmentDone(assignment));
        saveDones();
    }

    public void cleanUpDones() {
        try {
            ArrayList<String> ids = new ArrayList<>();
            for(int i = 0;i < mAssignments.length(); i++) {
                JSONObject assignJson = mAssignments.getJSONObject(i);
                ids.add(Assignment.generateKey(
                        assignJson.getString("title"),
                        assignJson.getString("start"),
                        assignJson.getString("end")
                        )
                );
            }
            loadDone();
            ArrayList<String> toDelete = new ArrayList<>();
            for(String id : mDoneMap.keySet()) {
                if(!ids.contains(id)) {
                    toDelete.add(id);
                }
            }
            mDoneMap.keySet().removeAll(toDelete);
            saveDones();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public void saveDones() throws Exception {
        //save map 2 file
        File file = new File(mContext.getFilesDir(), "done");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        StringBuilder builder = new StringBuilder();
        for (String id : mDoneMap.keySet()) {
            builder.append(id + "," + mDoneMap.get(id) + "\n");
        }
        fos.write(builder.toString().getBytes());
        fos.close();
    }

    public void loadDone() { //after file download
        try {
            File file = new File(mContext.getFilesDir(), "done");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] split;
            while ((line = br.readLine()) != null) {
                split = line.split(",");
                Log.d("DEBUG", line);
                mDoneMap.put(split[0], split[1].equals("true"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Date getDate(String s) {
        return Date.valueOf(s);
    }
}