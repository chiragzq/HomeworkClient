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
        String ret = "";
        String stringDate = date.toString();
        int year = Integer.parseInt(stringDate.substring(0,4));
        int month = Integer.parseInt(stringDate.substring(5,7));
        int day = Integer.parseInt(stringDate.substring(8,10));


        return ret;
    }
}
