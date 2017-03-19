package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;


public class LoginFragment extends Fragment {
    View.OnClickListener mClickListener;

    public LoginFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static LoginFragment newInstance(View.OnClickListener listener) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mClickListener = listener;

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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.login_button).setOnClickListener(mClickListener);

        GridView gridView = ((GridView) view.findViewById(R.id.grid_view));
        gridView.setNumColumns(7);
        gridView.setAdapter(new GridAdapter(getContext()));
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        reset();
    }

    @Override
    public void onPause(){
        super.onPause();
    }


    public void setLoading(boolean loading) {
        getView().findViewById(R.id.login_button).setEnabled(!loading);
        getView().findViewById(R.id.password_et).setEnabled(!loading);
        getView().findViewById(R.id.username_et).setEnabled(!loading);
        getView().findViewById(R.id.login_progress).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    public void reset() {
        getView().findViewById(R.id.login_failed_message).setVisibility(View.INVISIBLE);

        ((EditText)getView().findViewById(R.id.username_et)).setText("");
        ((EditText)getView().findViewById(R.id.password_et)).setText("");
    }

    private static final String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    public class GridAdapter extends BaseAdapter {
        private Context mContext;

        public GridAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 42;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        private final String[] weekday = {"S", "Mlease change caller according to com.intellij.openapi.project.IndexNotReadyException documentation", "T", "W", "T", "F", "S"};

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_cell, parent, false);
            }
            if (position < 7) {
                ((TextView) convertView.findViewById(R.id.day_num)).setText(weekday[position]);
            } else {
                Date todayDate = new Date(System.currentTimeMillis());
                Calendar thisMonth = Calendar.getInstance();
                Calendar lastMonth = Calendar.getInstance();
                thisMonth.set(getYear(todayDate),  Integer.parseInt(todayDate.toString().substring(5,7))-1, 1);
                lastMonth.set(getYear(todayDate),  Integer.parseInt(todayDate.toString().substring(5,7))-2, 1);
                Date firstDate = new Date(thisMonth.getTimeInMillis());
                Date lastMonthDate = new Date(lastMonth.getTimeInMillis());
                int startDateOffset = dateOffset(firstDate);

                if(position < startDateOffset + 7) { // last month month
                    ((TextView) convertView.findViewById(R.id.day_num)).setText(""
                            + (daysInMonth(lastMonthDate)
                            - startDateOffset
                            + position
                            - 6
                    ));
                    Log.i("Debug", "first " + position);
                }
                else if (position - 6 - startDateOffset > daysInMonth(todayDate)) { // next month
                    ((TextView) convertView.findViewById(R.id.day_num)).setText("" + (position - 6 - startDateOffset - daysInMonth(todayDate)));
                    Log.i("Debug", "middle " + position);
                } else { // this month
                    ((TextView) convertView.findViewById(R.id.day_num)).setText("" + (position - 6 - startDateOffset));
                    Log.i("Debug", "last " + position);
                }
            }
            return convertView;
        }

    }
    public int getDay(Date date) {
        return Integer.parseInt(date.toString().substring(8,10));
    }

    public int dateOffset(Date date) {    //used to calculate the day of the week the date is on
        int year = getYear(date);
        int month = Integer.parseInt(date.toString().substring(5,7));
        int monthCode = 0;
        String weekday = "";
        String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        if(year % 100 > 0 && year % 400 > 0 && year % 4 == 0) {
            switch(month) {
                case 10: monthCode = 0; break;
                case 5: monthCode = 1; break;
                case 2: case 8: monthCode = 2; break;
                case 3: case 11: monthCode = 3; break;
                case 6: monthCode = 4; break;
                case 9: case 12 : monthCode = 5; break;
                case 1 :case 4: case 7: monthCode = 6;
            }
        } else {
            switch(month) {
                case 1: case 10: monthCode = 0; break;
                case 5: monthCode = 1; break;
                case 8: monthCode = 2; break;
                case 2: case 3: case 11: monthCode = 3; break;
                case 6: monthCode = 4; break;
                case 9: case 12 : monthCode = 5; break;
                case 4: case 7: monthCode = 6;
            }
        }
        int dayCode = (getDay(date) + monthCode +  (getYear(date) % 100) + ((getYear(date) % 100) / 4)) % 7;
        weekday = weekdayNames[dayCode];
        switch(weekday) {
            case "Sunday":
                return 0;
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
        }
        return 0;
    }


    public int daysInMonth(Date date) {
        int [] monthDays = {0,31,0,31,30,31,30,31,31,30,31,30,31};
        int month = Integer.parseInt(date.toString().substring(5,7));
        int year = getYear(date);

        if(month != 2) {
            return monthDays[month];
        } else {
            return (year % 100 > 0 && year % 400 > 0 && year % 4 == 0) ? 29 : 28;
        }

    }
    public int getYear(Date date) {
        return Integer.parseInt(date.toString().substring(0,4));
    }
}
