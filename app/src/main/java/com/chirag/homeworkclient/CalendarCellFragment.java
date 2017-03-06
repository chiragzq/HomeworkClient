package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by spafindoople on 3/5/17.
 */

public class CalendarCellFragment extends Fragment {
    public CalendarCellFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static CalendarCellFragment newInstance(
            View.OnClickListener listener,
            Assignment assignment) {
        CalendarCellFragment fragment = new CalendarCellFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.calendar_cell, container, false);
        //((TextView) view.findViewById(R.id.class_name_text)).setText(mAssignment.course);
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
}
