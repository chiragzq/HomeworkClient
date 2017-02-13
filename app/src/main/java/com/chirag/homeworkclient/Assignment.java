package com.chirag.homeworkclient;

import java.sql.Date;

/**
 * Created by spafindoople on 2/12/17.
 */

public class Assignment {
    public final String title;
    public final String des;
    public final String course;
    public final Date start;
    public final Date end;

    public Assignment(String title, String des, String course, Date start, Date end) {
        this.title=title;
        this.des = des;
        this.course = course;
        this.start = start;
        this.end = end;
    }
}
