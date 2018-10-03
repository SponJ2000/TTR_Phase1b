package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class LobbyFragment extends Fragment {

    private static final String TAG = "LobbyFrag";

    private String mHostname;
    private String mGameID;
    private int mMaxPlayers;
    private List<String> mFakePlayernames;

    private Button mLeave;
    private Button mStart;

    private TextView mGameIDView;
    private TextView mHostnameView;
    private TextView mPlayerCount;

    private RecyclerView mLobbyRecycler;

    private LobbyAdapter mLobbyAdapter;

    private OnGameLeaveListener mListener;

    public LobbyFragment() {
        mGameID = "new republic (the game id)";
        mHostname = "Bob (the host)";
        mFakePlayernames = new ArrayList<>();
        mFakePlayernames.add(mHostname);
        mFakePlayernames.add("player 2");
        mFakePlayernames.add("player 3");
        mFakePlayernames.add("player 4");
        mMaxPlayers = 5;
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
                onGameStart();
            }
        });


        mGameIDView = (TextView) view.findViewById(R.id.game_id);
//      set the TextView at the top to show the username of the person who created the lobby
        mGameIDView.setText(mGameID);

        mHostnameView = (TextView) view.findViewById(R.id.hostname_view);
//      set the TextView at the top to show the username of the person who created the lobby
        mHostnameView.setText(mHostname);

        mPlayerCount = (TextView) view.findViewById(R.id.player_count);
//      set the TextView at the top to show the username of the person who created the lobby
        mPlayerCount.setText(mFakePlayernames.size() + "/" + mMaxPlayers);


        mLobbyRecycler = (RecyclerView) view.findViewById(R.id.player_recycler_view);
        mLobbyRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        List<String> gameIDs = mFakePlayernames;
        mLobbyAdapter = new LobbyAdapter(gameIDs);
        mLobbyRecycler.setAdapter(mLobbyAdapter);
    }


    private class LobbyHolder extends RecyclerView.ViewHolder {

        String mPlayername;

        private TextView mPlayernameView;

        public LobbyHolder(View view) {
            super(view);

            mPlayernameView = view.findViewById(R.id.playername_view);
        }

        public void bindGame(String playername) {
            mPlayername = playername;
            mPlayernameView.setText(playername);
        }
    }

    private class LobbyAdapter extends RecyclerView.Adapter<LobbyHolder> {

        private List<String> mPlayernames;

        public LobbyAdapter(List<String> playerNames) {
            mPlayernames = playerNames;
        }

        public LobbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.playerlist_view, parent, false);
            return new LobbyHolder(view);
        }

        public void onBindViewHolder(LobbyHolder holder, int position) {
            holder.bindGame(mPlayernames.get(position));
        }

        public int getItemCount() {
            return mPlayernames.size();
        }

    }
    

//  tells the model to edit the game info to show that the user has left the game
//  and then calls the onGameLeave function asynchronously
    private class leaveGameTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "leaving", Toast.LENGTH_SHORT).show();
            onGameLeave();
        }
    }

    private void onGameStart() {
        Toast.makeText(getActivity(), "starting game", Toast.LENGTH_SHORT).show();
//        Intent intent = GameActivity.newIntent(getContext());
//        startActivity(intent);
    }

//  sets up the activity as the listener so we can tell it when to change frags
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

//  tells the listener (the activity) to change the fragment back to the game list
    public void onGameLeave() {
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
