package com.chirag.homeworkclient;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.sql.Date;
import java.util.Calendar;

public class CalenderFragment extends Fragment{
    View.OnClickListener mClickListener;
    CalendarView.OnDateChangeListener mDayListener;
    public CalenderFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static CalenderFragment newInstance(
            View.OnClickListener listener,
            CalendarView.OnDateChangeListener dayListener) {

        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mClickListener = listener;
        fragment.mDayListener = dayListener;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        view.findViewById(R.id.logout_button).setOnClickListener(mClickListener);
        CalendarView calenderView = ((CalendarView)view.findViewById(R.id.calendarView));
        calenderView.setOnDateChangeListener(mDayListener);
        lockDate(calenderView);
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

    public void setLoading(boolean loading) {
        getView().findViewById(R.id.login_button).setEnabled(!loading);
        getView().findViewById(R.id.password_et).setEnabled(!loading);
        getView().findViewById(R.id.username_et).setEnabled(!loading);

        getView().findViewById(R.id.login_progress).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }

    public void reset() {
        setLoading(false);
        getView().findViewById(R.id.login_failed_message).setVisibility(View.INVISIBLE);
    }


    public void lockDate(CalendarView calendarView) {
        Calendar calendar = Calendar.getInstance();
        Date todayDate = new Date(System.currentTimeMillis());
        calendar.set(getYear(todayDate), getMonth(todayDate)-1, 1);
        calendarView.setMinDate(calendar.getTimeInMillis()); //set min date
        calendar.set(getYear(todayDate), getMonth(todayDate), 0);
        calendarView.setMaxDate(calendar.getTimeInMillis()); //set max date
    }

    public int getYear(Date date) {
        return Integer.parseInt(date.toString().substring(0,4));
    }

    public int getMonth(Date date) {
        return Integer.parseInt(date.toString().substring(5,7));
    }
}
