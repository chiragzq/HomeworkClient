package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import java.util.List;


public class LoginFragment extends Fragment {
    View.OnClickListener mClickListener;
    DataManager mDataManager;
    public LoginFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static LoginFragment newInstance(View.OnClickListener listener, DataManager dataManager) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.login_button).setOnClickListener(mClickListener);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int topRowHeight = 40;
        int rowHeight = (metrics.heightPixels - topRowHeight) / 5;

        GridView gridView = ((GridView) view.findViewById(R.id.grid_view));
        gridView.setNumColumns(7);
        gridView.setAdapter(new GridAdapter(getContext(), topRowHeight, rowHeight));
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

    public class GridAdapter extends BaseAdapter {
        private Context mContext;
        private int mTopRowHeight;
        private int mRowHeight;

        public GridAdapter(Context c, int topRowHeight, int rowHeight) {
            mContext = c;
            mTopRowHeight = topRowHeight;
            mRowHeight = rowHeight;
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

        private final String[] weekday = {"S", "M", "T", "W", "T", "F", "S"};

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_cell, parent, false);
            }
            initCalendarCell(convertView, position);
            return convertView;
        }


        void initCalendarCell(View view, int position) {
            Date todayDate = new Date(System.currentTimeMillis());
            List<Assignment> assignments = mDataManager.getAssignments(
                    DateUtil.getYear(todayDate),
                    DateUtil.getMonth(todayDate),
                    DateUtil.getDay(todayDate)
            );
            if (position < 7) {
                ((TextView) view.findViewById(R.id.day_num)).setText(weekday[position]);
                view.setMinimumHeight(mTopRowHeight);
            } else {

                ((TextView) view.findViewById(R.id.day_num)).setText(Integer.toString(getDayNumForPosition(position)));
                view.setMinimumHeight(mRowHeight);
            }
        }

        int getDayNumForPosition(int position) {
            Date todayDate = new Date(System.currentTimeMillis());
            Calendar thisMonth = Calendar.getInstance();
            Calendar lastMonth = Calendar.getInstance();
            thisMonth.set(DateUtil.getYear(todayDate),  Integer.parseInt(todayDate.toString().substring(5,7))-1, 1);
            lastMonth.set(DateUtil.getYear(todayDate),  Integer.parseInt(todayDate.toString().substring(5,7))-2, 1);
            Date firstDate = new Date(thisMonth.getTimeInMillis());
            Date lastMonthDate = new Date(lastMonth.getTimeInMillis());
            int startDateOffset = DateUtil.dateOffset(firstDate);

            if(position < startDateOffset + 7) { // last month month
                return DateUtil.daysInMonth(lastMonthDate)
                        - startDateOffset
                        + position
                        - 6;
            }
            else if (position - 6 - startDateOffset > DateUtil.daysInMonth(todayDate)) { // next month
                return position - 6 - startDateOffset - DateUtil.daysInMonth(todayDate);
            } else { // this month
                return  position - 6 - startDateOffset;
            }
        }

    }




}
