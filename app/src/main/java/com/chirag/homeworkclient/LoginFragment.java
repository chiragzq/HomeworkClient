package com.chirag.homeworkclient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;


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

        GridView gridView = ((GridView) view.findViewById(R.id.gridview));
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

        private final String[] weekday = {"S", "M", "T", "W", "T", "F", "S"};

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_cell, parent, false);
            }
            if(position < 7) {
                ((TextView)convertView.findViewById(R.id.day_num)).setText(weekday[position]);
            } else {
                ((TextView)convertView.findViewById(R.id.day_num)).setText("hello" + position);
            }
            return convertView;
        }

    }
}
