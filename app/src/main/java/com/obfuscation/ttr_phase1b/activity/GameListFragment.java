package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGameSelectListener} interface
 * to handle interaction events.
 * Use the {@link GameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameListFragment extends Fragment {

    private static final String TAG = "GameListFrag";

    List<String> mFakeGameIDs;
    int mSelectedGame;

    private Button mJoin;
    private Button mCreate;
    private RecyclerView mGameRecycler;

    private GameAdapter mGameAdapter;

    private OnGameSelectListener mListener;

    public GameListFragment() {
        mFakeGameIDs = new ArrayList<>();
        mFakeGameIDs.add("myGame");
        mFakeGameIDs.add("the other game");
        mFakeGameIDs.add("ticket to lose");
        mFakeGameIDs.add("i love pesto");
        mSelectedGame = -1;
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
                onGameSelect("create");
            }
        });

        mGameRecycler = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        mGameRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        mJoin.setEnabled(false);

        return view;
    }

    private void changeAccessibility() {
        if(mSelectedGame == -1) {
            mJoin.setEnabled(false);
        }else {
            mJoin.setEnabled(true);
        }
    }

    private void updateUI() {
        List<String> gameIDs = mFakeGameIDs;
        mGameAdapter = new GameAdapter(gameIDs);
        mGameRecycler.setAdapter(mGameAdapter);
    }


    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String mGameID;
        int mGameNumber;

        private TextView mGameIDView;
        private TextView mHostView;
        private TextView mPlayersView;

        public GameHolder(View view) {
            super(view);
            view.setOnClickListener(this);

            mGameIDView = view.findViewById(R.id.game_id_view);
            mHostView = view.findViewById(R.id.hostname_view);
            mPlayersView = view.findViewById(R.id.players_view);
        }

        public void bindGame(String gameID, int gameNumber) {
            mGameID = gameID;
            mGameNumber = gameNumber;
            mGameIDView.setText(gameID);
            mHostView.setText(gameID);
            mPlayersView.setText("1/4");
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), "Selected " + mGameID, Toast.LENGTH_SHORT).show();
            mSelectedGame = mGameNumber;
            changeAccessibility();
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        private List<String> mGameIDs;

        public GameAdapter(List<String> gameIDs) {
            mGameIDs = gameIDs;
        }

        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gamelist_view, parent, false);
            return new GameHolder(view);
        }

        public void onBindViewHolder(GameHolder holder, int position) {
            holder.bindGame(mGameIDs.get(position), position);
        }

        public int getItemCount() {
            return mGameIDs.size();
        }

    }



//  tells the model to edit the players info to add the game info and edit the game info to add the player
//  then calls the onGameSelect asynchronously
    private class joinGameTask extends AsyncTask<Void, Void, Object> {

        @Override
        protected Object doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Toast.makeText(getActivity(), "joined " + mFakeGameIDs.get(mSelectedGame), Toast.LENGTH_SHORT).show();
            onGameSelect("join");
        }
    }

//  sets up the activity as the listener so we can tell it when to change frags
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

//  tells the activity to change fragments to either the create game frag if selection == "create"
//  or the lobby frag if selection == "join"
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
