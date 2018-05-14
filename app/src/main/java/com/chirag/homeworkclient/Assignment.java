package com.chirag.homeworkclient;

import android.util.Log;

import java.sql.Date;

/**
 * Created by spafindoople on 2/12/17.
 */


public class Assignment {
    public final String title;
    public final String desc;
    public final String course;
    public final Date start;
    public final Date end;

    public Assignment(String title, String desc, String course, Date start, Date end) {
        this.title=title;
        this.desc = desc;
        this.course = course;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return generateKey(title, start, end);
    }

    public static String generateKey(String title, String start, String end) {
        return title.replaceAll("[^\\w]", "") + start.toString() + end.toString();
    }

    public static String generateKey(String title, Date start, Date end) {
        return title.replaceAll("[^\\w]", "") + start.toString() + end.toString();
    }
}
