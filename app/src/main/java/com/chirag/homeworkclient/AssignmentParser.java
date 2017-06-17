package com.chirag.homeworkclient;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by spafindoople on 5/20/17.
 */

public class AssignmentParser {
    static String getHtml(Context context) {
        InputStream stream = context.getResources().openRawResource(R.raw.out);
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder str = new StringBuilder();
        String line;
        try {
            while((line = r.readLine()) != null) {
                str.append(line);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    private static ArrayList<JSONObject> parseAssignments(Document doc, ArrayList<String> classes) {
        ArrayList<String> parsed = new ArrayList<String>();
        ArrayList<JSONObject> assignmentList = new ArrayList<JSONObject>();
        JSONObject assignment;

        ArrayList<Element> assignments = doc.getElementsByClass("rsApt rsAptSimple");
        for(int i = 0; i < assignments.size(); i++) {
            Element a = assignments.get(i);
            assignment = new JSONObject();

            //handle the start and end date
            //yyyy-mm-dd
            String[] range = getElement(a, "span", "StartingOn").text().split(" - ");
            String[] start = range[0].split("/");
            String[] end = range[(range.length == 2 ? 1 : 0)].split("/");

            for(int j = 0;j < 2;j ++) {
                start[j] = (start[j].length() == 1 ? "0" : "" )+ start[j];
                end[j] = (end[j].length() == 1 ? "0" : "" )+ end[j];
            }

            String begin = start[2].substring(0, 4) + "-" + start[0] + "-" + start[1];
            String finish = end[2].substring(0, 4) + "-" + end[0] + "-" + end[1];

            Element name = getElement(a, "span", "lblTitle");
            String title = name.text().split(": ")[1];

            Element desc = name.parent().parent();
            String des = "";

            desc.getElementsByTag("div").remove();
            des = desc.children().toString();

            des = des.replaceAll("\n", "").replaceAll("^(<br>)+|(<br>)+$", "");

            String course = "";
            for(int j = 0; j < classes.size();j ++) {
                if(title.contains(classes.get(j))) {
                    course = classes.get(j);
                }
            }
            assignment.put("start", begin);
            assignment.put("end", finish);
            assignment.put("desc", des);
            assignment.put("course", course);
            assignment.put("title", title);

            String key = Assignment.generateKey(title, begin, finish);

            if(!parsed.contains(key)) {
                parsed.add(key);
                assignmentList.add(assignment);
            }
        }
        return assignmentList;
    }

    public static ArrayList<JSONObject> getAssignments(String html) {
        Document document = Jsoup.parse(html);
        ArrayList<String> classes = getClasses(document);
        return parseAssignments(document, classes);
    }

    private static ArrayList<String> getClasses(Document doc) {
        ArrayList<String> classList = new ArrayList<String>();

        Elements classes = getElement(doc,"table", "cbClasses").getElementsByTag("label");
        for(int i = 0;i < classes.size(); i++) {
            classList.add(classes.get(i).text());
        }
        return classList;
    }


    private static Element getElement(Element doc, String tag, String id) {
        Elements elements = doc.getElementsByTag(tag);
        for(int i = 0;i < elements.size(); i++) {
            if(elements.get(i).id().contains(id)) {
                return elements.get(i);
            }
        }
        return null;
    }
}
