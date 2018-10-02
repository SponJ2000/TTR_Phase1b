package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LobbyFragment extends Fragment {

    private static final String TAG = "LobbyFrag";

    private Button mLeave;
    private Button mStart;

    private OnGameLeaveListener mListener;

    public LobbyFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    public static LobbyFragment newInstance() {
        LobbyFragment fragment = new LobbyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lobby_fragment, container, false);

        mLeave = (Button) view.findViewById(R.id.leave_button);
        mLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now leaving");
                try {
                    new leaveGameTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Leave Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mStart = (Button) view.findViewById(R.id.start_button);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now starting");
                Toast.makeText(getActivity(), "Starting", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    private class leaveGameTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "leaving", Toast.LENGTH_SHORT).show();
            onGameStart();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameLeaveListener) {
            mListener = (OnGameLeaveListener) context;
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

    public void onGameStart() {
        if (mListener != null) {
            mListener.onGameLeave();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnGameLeaveListener {
        void onGameLeave();
    }

}
