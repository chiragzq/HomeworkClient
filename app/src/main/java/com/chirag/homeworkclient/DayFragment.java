package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class DayFragment extends Fragment {
    View.OnClickListener mClickListener;
    DataManager mDataManager;

    public DayFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static DayFragment newInstance(View.OnClickListener listener, DataManager dataManager) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mClickListener = listener;
        fragment.mDataManager = dataManager;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        ListView leftColumn = (ListView) view.findViewById(R.id.left_column);
        ListView rightColumn = (ListView) view.findViewById(R.id.right_column);

        rightColumn.setAdapter(new AssignmentAdapter(mDataManager.getAssignments(0), 1));
        leftColumn.setAdapter(new AssignmentAdapter(mDataManager.getAssignments(0), 0));
        return view;
    }

    public List<Assignment> splitList(List<Assignment> assignments, int side) {
        List<Assignment> ret = new ArrayList<>();
        for (int i = 0; i < assignments.size(); i++) {
            if (i % 2 == side) {
                ret.add(assignments.get(i));
            }
        }
        return ret;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class AssignmentAdapter extends BaseAdapter {
        List<Assignment> mAssignments;

        public AssignmentAdapter(List<Assignment> assignments, int side) {
            mAssignments = splitList(assignments, side);
        }

        public int getCount() {
            return mAssignments.size();
        }

        public Object getItem(int position) {
            return mAssignments.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_assignment, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.class_name_text)).setText(
                    mAssignments.get(position).course);

            ((TextView) convertView.findViewById(R.id.assignment_name_text)).setText(
                    mAssignments.get(position).title);

            ((TextView) convertView.findViewById(R.id.assignment_day_text)).setText(
                    dateString(mAssignments.get(position).start) +
                            " - " +
                            dateString(mAssignments.get(position).end));

            ((TextView) convertView.findViewById(R.id.assignment_description_text)).setText(
                    mAssignments.get(position).des);

            return convertView;
        }
    }

    public String dateString(Date date) {
        Date todayDate = new Date(System.currentTimeMillis()); //get a sql date with today

        int diff = getDay(todayDate) - getDay(date);
        int dayNum = 0;
        String monthString = "";
        String weekday = "";
        switch(diff) {  //check if relative phrases should be used
            case -2:
                return "2 days ago";
            case -1:
                return "Yesterday";
            case 0:
                return" Today";
            case 1:
                return "Tomorrow";
        }

        dayNum = (getDay(date) +        //calculate the day of the week the date is
                getMonthCode(date) +
                (getYear(date) % 100) +
                ((getYear(date) % 100) / 4)) % 7;
        switch(dayNum) {
            case 0: weekday = "Saturday";
                    break;
            case 1: weekday = "Sunday";
                    break;
            case 2: weekday = "Monday";
                    break;
            case 3: weekday = "Tuesday";
                    break;
            case 4: weekday = "Wednesday";
                    break;
            case 5: weekday = "Thursday";
                    break;
            case 6: weekday = "Friday";
        }
        Log.i("Debug", getYear(todayDate) + " " + getDay(todayDate) + " " + date.toString().substring(5,7));
        if(Math.abs(getDay(todayDate) - getDay(date)) <= 6) {  //decide whether the date is close enough to not include the full format
            return weekday;
        } else {
            switch(Integer.parseInt(date.toString().substring(5,7))) {
                case 1: monthString = "January";
                    break;
                case 2: monthString = "February";
                    break;
                case 3: monthString = "March";
                    break;
                case 4: monthString = "April";
                    break;
                case 5: monthString = "May";
                    break;
                case 6: monthString = "June";
                    break;
                case 7: monthString = "July";
                    break;
                case 8: monthString = "August";
                    break;
                case 9: monthString = "September";
                    break;
                case 10: monthString = "October";
                    break;
                case 11: monthString = "November";
                    break;
                default: monthString = "December";
            }
            return weekday + ", " + monthString + " " +  getDay(date);   //eg. Friday, February 17
        }
    }

    public int getDay(Date date) {
        return Integer.parseInt(date.toString().substring(8,10));
    }

    public int getMonthCode(Date date) {    //used to calculate the day of the week the date is on
        int year = getYear(date);
        int month = Integer.parseInt(date.toString().substring(5,7));
        if(year % 100 > 0 && year % 400 > 0 && year % 4 == 0) {
            switch(month) {
                case 10: return 0;
                case 5: return 1;
                case 2: case 8: return 2;
                case 3: case 11: return 3;
                case 6: return 4;
                case 9: case 12 : return 5;
                case 1 :case 4: case 7: return 6;
            }
        } else {
            switch(month) {
                case 1: case 10: return 0;
                case 5: return 1;
                case 8: return 2;
                case 2: case 3: case 11: return 3;
                case 6: return 4;
                case 9: case 12 : return 5;
                case 4: case 7: return 6;
            }
        }
        return 0;
    }

    public int getYear(Date date) {
        return Integer.parseInt(date.toString().substring(0,4));
    }
}
