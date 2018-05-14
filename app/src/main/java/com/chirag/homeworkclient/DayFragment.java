package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    private AssignmentAdapter mAdapter;

    public DayFragment() {

    }

    public void updateList() {
        mAdapter.notifyDataSetChanged();
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
        mAdapter = new AssignmentAdapter(mDataManager.getAssignments(mYear, mMonth+1, mDay));
        listView.setAdapter(mAdapter);

        String day = (mDay < 10 ? "0" : "") + mDay;
        String month = ((mMonth + 1) < 10 ? "0" : "") + (mMonth + 1);
        String year = "" + mYear;

        ((TextView) view.findViewById(R.id.date_view)).setText(DateUtil.dateString(Date.valueOf(year + "-" + month + "-" + day)));
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

            convertView.setBackgroundResource(
                    mDataManager.isAssignmentDone(mAssignments.get(position)) ?
                            R.color.doneAssignmentBackground :
                            R.color.newAssignmentBackground
            );


            ((TextView) convertView.findViewById(R.id.class_name_text)).setText(
                    mAssignments.get(position).course);

            ((TextView) convertView.findViewById(R.id.assignment_name_text)).setText(
                    mAssignments.get(position).title);

            ((TextView) convertView.findViewById(R.id.assignment_day_text)).setText(
                    DateUtil.dateString(mAssignments.get(position).start) +
                            " - " +
                            DateUtil.dateString(mAssignments.get(position).end));

            ((TextView) convertView.findViewById(R.id.assignment_description_text)).setText(
                    Html.fromHtml(mAssignments.get(position).desc));

            Button doneButton = (Button)convertView.findViewById(R.id.done_button);
            doneButton.setText(
                    mDataManager.isAssignmentDone(mAssignments.get(position)) ?
                            R.string.button_incomplete :
                            R.string.button_done
            );

            doneButton.setOnClickListener(mClickListener);
            doneButton.setTag(mAssignments.get(position));

            return convertView;
        }
    }

    private static final String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    private static final String[] monthNames = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


}
