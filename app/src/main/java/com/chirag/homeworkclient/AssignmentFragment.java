package com.chirag.homeworkclient;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AssignmentFragment extends Fragment {
    View.OnClickListener mClickListener;
    Assignment mAssignment;
    public AssignmentFragment() {
        // Required
    }

    // TODO: Rename and change type
    public static AssignmentFragment newInstance(
            View.OnClickListener listener,
            Assignment assignment) {
        AssignmentFragment fragment = new AssignmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.mClickListener = listener;
        fragment.mAssignment = assignment;
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
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);
        ((TextView) view.findViewById(R.id.class_name_text)).setText(mAssignment.course);
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
