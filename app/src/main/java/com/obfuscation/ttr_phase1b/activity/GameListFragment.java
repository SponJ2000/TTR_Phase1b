package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGameSelectListener} interface
 * to handle interaction events.
 * Use the {@link GameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameListFragment extends Fragment {

    private static final String TAG = "GameListFrag";

    private Button mJoin;
    private Button mCreate;

    private OnGameSelectListener mListener;

    public GameListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GameListFragment.
     */
    public static GameListFragment newInstance() {
        GameListFragment fragment = new GameListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_list_fragment, container, false);

        mJoin = (Button) view.findViewById(R.id.join_game_button);
        mJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now joining");
                try {
                    new GameListFragment.joinGameTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Join Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCreate = (Button) view.findViewById(R.id.create_game_button);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Now creating");
                try {
                    new GameListFragment.createGameTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Create Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private class createGameTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "creating", Toast.LENGTH_SHORT).show();
            onGameSelect("create");
        }
    }

    private class joinGameTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "joined", Toast.LENGTH_SHORT).show();
            onGameSelect("join");
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameSelectListener) {
            mListener = (OnGameSelectListener) context;
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

    public void onGameSelect(String gameSelection) {
        if (mListener != null) {
            mListener.onGameSelect(gameSelection);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnGameSelectListener {
        void onGameSelect(String gameSelection);
    }
}
