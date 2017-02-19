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

import java.util.List;

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
        leftColumn.setAdapter(new AssignmentAdapter(mDataManager.getAssignments(0)));
        ListView rightColumn = (ListView) view.findViewById(R.id.right_column);
        rightColumn.setAdapter(new AssignmentAdapter(mDataManager.getAssignments(0)));
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

            return convertView;
        }
    }
}
