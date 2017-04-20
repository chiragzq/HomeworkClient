package com.chirag.homeworkclient;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment{
    View.OnClickListener mClickListener;
    DataManager mDataManager;
    private OnDayClickedListener mOnDayClickedListener;

    public CalendarFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static CalendarFragment newInstance(
            View.OnClickListener listener,
            DataManager dataManager) {
        CalendarFragment fragment = new CalendarFragment();
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
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        View logoutButton = view.findViewById(R.id.logout_button);
        TextView calendarMonthText = (TextView) view.findViewById(R.id.calendar_month_text);


        logoutButton.setOnClickListener(mClickListener);
        calendarMonthText.setText(
                DateUtil.monthNames[DateUtil.getMonth(new Date(System.currentTimeMillis()))] +
                " " +
                DateUtil.getYear(new Date(System.currentTimeMillis()))
        );

        GridView gridView = ((GridView) view.findViewById(R.id.grid_view));
        gridView.setNumColumns(7);

        gridView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int topRowHeight = 40;
                int rowHeight = (view.getHeight() - topRowHeight) / DateUtil.numRowsInMonth(DateUtil.today()) - 20;
                ((GridView) view).setAdapter(new GridAdapter(getContext(), topRowHeight, rowHeight));
            }
        });

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


    public void setOnDayClickedListener(OnDayClickedListener listener) {
        mOnDayClickedListener = listener;
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
            return (DateUtil.numRowsInMonth(DateUtil.today()) + 1) * 7;
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
                TextView text = ((TextView) view.findViewById(R.id.day_num));
                text.setText(weekday[position]);
                text.setTextAppearance(getContext(),R.style.TopRowText);
                text.setTextColor(Color.parseColor("#000000"));
                view.setMinimumHeight(mTopRowHeight);
            } else {
                TextView text = (TextView) view.findViewById(R.id.day_num);
                if(DateUtil.getMonth(DateUtil.today()) == getMonthForPosition(position)) {
                    text.setTextColor(Color.parseColor("#000000"));
                } else text.setTextColor(Color.parseColor("#aaaaaa"));
                text.setText(Integer.toString(getDayNumForPosition(position)));

                view.setMinimumHeight(mRowHeight);
                view.setOnClickListener(createClickListener(
                        getYearForPosition(position),
                        getMonthForPosition(position)-1,
                        getDayNumForPosition(position)
                ));
            }
        }

        View.OnClickListener createClickListener(final int year, final int month, final int day) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   onDayClicked(year, month, day);
                }
            };
        }


        public void onDayClicked(int year, int month, int day) {
            mOnDayClickedListener.onDayClicked(year, month, day);
        }

        int getMonthForPosition(int position) {
            Calendar thisMonth = Calendar.getInstance();
            thisMonth.set(DateUtil.getYear(DateUtil.today()),  DateUtil.getMonth(DateUtil.today())-1, 1);
            Date firstDate = new Date(thisMonth.getTimeInMillis());
            int startDateOffset = DateUtil.dateOffset(firstDate);

            if (position < startDateOffset+7) {
                return DateUtil.getMonth(DateUtil.today()) - 1;
            } else if (position - startDateOffset - 6 > DateUtil.daysInMonth(DateUtil.today())) {
                return DateUtil.getMonth(DateUtil.today()) + 1;
            } else return DateUtil.getMonth(DateUtil.today());
        }

        int getYearForPosition(int position) {
            int thisMonth = DateUtil.getMonth(DateUtil.today());
            if(thisMonth == 12 && getMonthForPosition(position) == 1) {
                return DateUtil.getYear(DateUtil.today()) + 1;
            } else if(thisMonth == 1 && getMonthForPosition(position) == 12) {
                return DateUtil.getYear(DateUtil.today()) - 1;
            } else return DateUtil.getYear(DateUtil.today());
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

    public interface OnDayClickedListener {
        void onDayClicked(int year, int month, int day);
    }
}
