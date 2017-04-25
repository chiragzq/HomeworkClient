package com.chirag.homeworkclient;


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

    private static final String str = "[ { \"title\": \"Karina\", \"desc\": \"Nisi in qui tempor dolore eu exercitation ea.\", \"course\": \"Cannon\", \"start\": \"2017-04-03\", \"end\": \"2017-04-19\" }, { \"title\": \"Burns\", \"desc\": \"Nostrud ipsum non ex in excepteur reprehenderit deserunt mollit.\", \"course\": \"Obrien\", \"start\": \"2017-04-03\", \"end\": \"2017-04-26\" }, { \"title\": \"Knapp\", \"desc\": \"Laborum magna dolor excepteur dolor sunt aliqua voluptate exercitation irure adipisicing officia.\", \"course\": \"Bender\", \"start\": \"2017-04-10\", \"end\": \"2017-04-27\" }, { \"title\": \"Johns\", \"desc\": \"Voluptate exercitation id reprehenderit in id commodo ea incididunt veniam.\", \"course\": \"Solis\", \"start\": \"2017-04-08\", \"end\": \"2017-04-17\" }, { \"title\": \"Hess\", \"desc\": \"Quis cupidatat sint velit occaecat culpa ex culpa.\", \"course\": \"Parrish\", \"start\": \"2017-04-02\", \"end\": \"2017-04-29\" }, { \"title\": \"Yates\", \"desc\": \"Ad eiusmod aliqua tempor nostrud ut magna commodo ullamco officia eiusmod adipisicing.\", \"course\": \"Knox\", \"start\": \"2017-04-10\", \"end\": \"2017-04-27\" }, { \"title\": \"Millie\", \"desc\": \"Consectetur qui amet Lorem esse quis laborum cillum deserunt dolore reprehenderit do tempor.\", \"course\": \"Haynes\", \"start\": \"2017-04-10\", \"end\": \"2017-04-27\" }, { \"title\": \"Tyson\", \"desc\": \"Excepteur dolor aliqua consectetur aliqua.\", \"course\": \"Peterson\", \"start\": \"2017-04-03\", \"end\": \"2017-04-27\" }, { \"title\": \"Monroe\", \"desc\": \"Incididunt cillum cupidatat cillum aliquip officia deserunt.\", \"course\": \"Slater\", \"start\": \"2017-04-08\", \"end\": \"2017-04-26\" }, { \"title\": \"Katy\", \"desc\": \"Adipisicing nostrud commodo eu nisi ex est.\", \"course\": \"Blevins\", \"start\": \"2017-04-03\", \"end\": \"2017-04-17\" }, { \"title\": \"Katelyn\", \"desc\": \"Sunt consectetur excepteur excepteur nulla velit sit proident adipisicing dolor elit eiusmod sunt.\", \"course\": \"Steele\", \"start\": \"2017-04-07\", \"end\": \"2017-04-19\" }, { \"title\": \"Leta\", \"desc\": \"Nisi ea dolor occaecat Lorem reprehenderit.\", \"course\": \"Barnes\", \"start\": \"2017-04-06\", \"end\": \"2017-04-24\" }, { \"title\": \"Bridgett\", \"desc\": \"Duis fugiat et eu laborum duis nisi dolore.\", \"course\": \"Suarez\", \"start\": \"2017-04-08\", \"end\": \"2017-04-18\" }, { \"title\": \"Hayes\", \"desc\": \"Adipisicing anim eu labore non ullamco consequat.\", \"course\": \"Carrillo\", \"start\": \"2017-04-09\", \"end\": \"2017-04-22\" }, { \"title\": \"Hayden\", \"desc\": \"Laborum elit Lorem culpa adipisicing velit.\", \"course\": \"Kirby\", \"start\": \"2017-04-10\", \"end\": \"2017-04-21\" }, { \"title\": \"Lyons\", \"desc\": \"Et fugiat non nulla ullamco ea velit laboris reprehenderit.\", \"course\": \"Bullock\", \"start\": \"2017-04-11\", \"end\": \"2017-04-19\" }, { \"title\": \"Cook\", \"desc\": \"Dolore anim culpa minim cillum et consectetur ullamco.\", \"course\": \"Donaldson\", \"start\": \"2017-04-11\", \"end\": \"2017-04-19\" }, { \"title\": \"Hays\", \"desc\": \"Esse ipsum aliqua ea consectetur culpa aliquip sint voluptate.\", \"course\": \"Tucker\", \"start\": \"2017-04-03\", \"end\": \"2017-04-24\" }, { \"title\": \"Taylor\", \"desc\": \"Cillum nisi adipisicing proident nisi pariatur incididunt aliquip.\", \"course\": \"Saunders\", \"start\": \"2017-04-09\", \"end\": \"2017-04-21\" }, { \"title\": \"Holcomb\", \"desc\": \"Magna culpa ea in mollit culpa et quis qui mollit ea fugiat qui.\", \"course\": \"Lamb\", \"start\": \"2017-04-04\", \"end\": \"2017-04-26\" }, { \"title\": \"Stanley\", \"desc\": \"Velit aliqua ut proident ut id laborum ipsum ad adipisicing do est excepteur.\", \"course\": \"Stanton\", \"start\": \"2017-04-05\", \"end\": \"2017-04-19\" }, { \"title\": \"Stefanie\", \"desc\": \"Laboris ullamco excepteur Lorem culpa culpa adipisicing.\", \"course\": \"Dickerson\", \"start\": \"2017-04-07\", \"end\": \"2017-04-25\" }, { \"title\": \"Tillman\", \"desc\": \"Et deserunt esse ullamco labore deserunt et Lorem nulla ea sint ad incididunt aute.\", \"course\": \"Pennington\", \"start\": \"2017-04-03\", \"end\": \"2017-04-24\" }, { \"title\": \"Cleveland\", \"desc\": \"Proident commodo aute aliqua excepteur sit amet velit.\", \"course\": \"Snyder\", \"start\": \"2017-04-10\", \"end\": \"2017-04-26\" }, { \"title\": \"Reynolds\", \"desc\": \"Nulla fugiat aute nostrud nulla est reprehenderit sit.\", \"course\": \"Rasmussen\", \"start\": \"2017-04-07\", \"end\": \"2017-04-26\" }, { \"title\": \"Heidi\", \"desc\": \"Minim non deserunt dolore labore voluptate qui proident velit Lorem sunt.\", \"course\": \"Sampson\", \"start\": \"2017-04-06\", \"end\": \"2017-04-25\" }, { \"title\": \"Kaufman\", \"desc\": \"Esse minim ullamco sit elit elit id nostrud occaecat laborum dolor pariatur nisi dolore.\", \"course\": \"Hodges\", \"start\": \"2017-04-06\", \"end\": \"2017-04-27\" }, { \"title\": \"Lesa\", \"desc\": \"Reprehenderit sit magna cupidatat duis adipisicing et veniam excepteur eu aliqua excepteur eu voluptate.\", \"course\": \"Mcgowan\", \"start\": \"2017-04-11\", \"end\": \"2017-04-18\" }, { \"title\": \"Laura\", \"desc\": \"In irure cillum deserunt esse est laborum eu occaecat exercitation dolore sunt dolore aute.\", \"course\": \"Gamble\", \"start\": \"2017-04-13\", \"end\": \"2017-04-29\" }, { \"title\": \"Durham\", \"desc\": \"Lorem nostrud cillum et commodo anim mollit.\", \"course\": \"Bauer\", \"start\": \"2017-04-11\", \"end\": \"2017-04-26\" } ]";
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





























