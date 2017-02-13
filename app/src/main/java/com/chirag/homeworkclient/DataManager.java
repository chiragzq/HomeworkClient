package com.chirag.homeworkclient;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 * Created by spafindoople on 2/12/17.
 */

public class DataManager {
    public List<Assignment> getAssignments(int day) {
        List<Assignment> assignments = new ArrayList<>();
        assignments.add(new Assignment("Title", "this is a short description", "Math",Date.valueOf("2017-2-12"),Date.valueOf("2017-2-13")));
        assignments.add(new Assignment("Title", "this is a short description", "Math",Date.valueOf("2017-2-12"),Date.valueOf("2017-2-13")));
        assignments.add(new Assignment("Title", "this is a short description", "Math",Date.valueOf("2017-2-12"),Date.valueOf("2017-2-13")));
        return assignments;
    }
}
