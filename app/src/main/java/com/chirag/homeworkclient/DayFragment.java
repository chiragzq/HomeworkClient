package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Date;
import java.util.List;

public class DayFragment extends Fragment {
    View.OnClickListener mClickListener;
    DataManager mDataManager;
    private int mYear;
    private int mMonth;
    private int mDay;

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
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(new AssignmentAdapter(mDataManager.getAssignments(mYear, mMonth, mDay)));

        String day = (mDay < 10 ? "0" : "") + mDay;
        String month = ((mMonth + 1) < 10 ? "0" : "") + (mMonth + 1);
        String year = "" + mYear;

        ((TextView) view.findViewById(R.id.date_view)).setText(dateString(Date.valueOf(year + "-" + month + "-" + day)));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setDate(int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
    }

    private class AssignmentAdapter extends BaseAdapter {
        List<Assignment> mAssignments;

        public AssignmentAdapter(List<Assignment> assignments) {
            mAssignments = assignments;
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
                    mAssignments.get(position).desc);

            return convertView;
        }
    }

    private static final String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    private static final String[] monthNames = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public String dateString(Date date) {
        Date todayDate = new Date(System.currentTimeMillis()); //get a sql date with today

        int diff = getDay(todayDate) - getDay(date);
        int dayNum = 0;
        switch(diff) {  //check if relative phrases should be used
            case 2:
                return "2 days ago";
            case 1:
                return "Yesterday";
            case 0:
                return" Today";
            case -1:
                return "Tomorrow";
        }

        dayNum = (getDay(date) +        //calculate the day of the week the date is
                getMonthCode(date) +
                (getYear(date) % 100) +
                ((getYear(date) % 100) / 4)) % 7;

        String weekday = weekdayNames[dayNum];

        if((getDay(todayDate) - getDay(date)) < 0 && (getDay(todayDate) - getDay(date)) > -7) {  //decide whether the date is close enough to not include the full format
            return weekday;
        } else {
            String monthString = monthNames[Integer.parseInt(date.toString().substring(5,7))];
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
