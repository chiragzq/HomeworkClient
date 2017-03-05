package com.chirag.homeworkclient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


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
}
