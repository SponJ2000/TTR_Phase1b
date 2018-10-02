package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
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
 * {@link GameCreationFragment.OnGameCreationLister} interface
 * to handle interaction events.
 * Use the {@link GameCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameCreationFragment extends Fragment {

    private static final String TAG = "LobbyFrag";

    private Button mCancel;
    private Button mCreate;

    private OnGameCreationLister mListener;

    public GameCreationFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GameCreationFragment.
     */
    public static GameCreationFragment newInstance() {
        GameCreationFragment fragment = new GameCreationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_creation_fragment, container, false);

        mCancel = (Button) view.findViewById(R.id.cancel_button);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now cancelling");
                onFinish("cancel");

            }
        });

        mCreate = (Button) view.findViewById(R.id.create_button);
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now creating");
                try {
                    new createGameTask().execute();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(getActivity(), "Start Failed", Toast.LENGTH_SHORT).show();
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
            onFinish("create");
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGameCreationLister) {
            mListener = (OnGameCreationLister) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFinish(String selection) {
        if (mListener != null) {
            mListener.onFinishCreating(selection);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnGameCreationLister {
        void onFinishCreating(String selection);
    }
}
