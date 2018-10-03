package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment implements IPresenter {

    private static final String TAG = "LoginFrag";

    private String mUser;
    private String mPass;

    private EditText mUsername;
    private EditText mPassword;

    private Button mLogIn;
    private Button mRegister;

    private OnLoginListener mListener;

    public LoginFragment() {
        mUser = "";
        mPass = "";
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        mUsername = (EditText) view.findViewById(R.id.username);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser = s.toString();
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mPassword = (EditText) view.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPass = s.toString();
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mLogIn = (Button) view.findViewById(R.id.login_button);
        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now loging in");
                Toast.makeText(getActivity(), "Attempting to log in", Toast.LENGTH_SHORT).show();
                try {
                    new loginServerTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mRegister = (Button) view.findViewById(R.id.register_button);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Now registering");
                try {
                    new registerInServerTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLogIn.setEnabled(false);
        mRegister.setEnabled(false);

        return view;
    }

    private void changeAccessibility() {
        if(!mUser.equals("") && !mPass.equals("")) {
            mLogIn.setEnabled(true);
            mRegister.setEnabled(true);
        }else {
            mLogIn.setEnabled(false);
            mRegister.setEnabled(false);
        }
    }

    @Override
    public void onComplete(Result result) {
        
    }

    //  Async task to register the user, update the model, and call the onLogin function
    private class registerInServerTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
            onLogin();
        }
    }

//  Async task to login the user, update the model, and call the onLogin function
    private class loginServerTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
			Toast.makeText(getActivity(), "Logged In", Toast.LENGTH_SHORT).show();
            onLogin();
        }
    }

//  sets up the activity as the listener so we can tell it when to change frags
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginListener) {
            mListener = (OnLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGameLeaveListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    tells the listener (the activity) to change the fragment to the game list
    public void onLogin() {
        if (mListener != null) {
            mListener.onLogin();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLoginListener {
        void onLogin();
    }

}
