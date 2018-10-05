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

import model.ModelFacade;
import server.Game;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnGameSelectListener} interface
 * to handle interaction events.
 * Use the {@link GameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameListFragment extends Fragment implements IPresenter {

    private static final String TAG = "GameListFrag";

    List<String> mFakeGameIDs;
    int mSelectedGame;
    List<Game> mGameList;

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
        mGameList = new ArrayList<>();
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
        ModelFacade.getInstance().GetGameList();
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
                ModelFacade.getInstance().JoinGame("", mGameList.get(mSelectedGame));

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
        mGameAdapter = new GameAdapter(mGameList);
        mGameRecycler.setAdapter(mGameAdapter);
    }

    @Override
    public void onComplete(Object result) {
//      if join game worked right, go to game lobby
        if(true) {
            onGameSelect("join");
        }
    }

    @Override
    public void updateInfo(Object result) {
        mGameList = (List<Game>) result;
        updateUI();
    }


//  tells the activity to change fragments to either the create game frag if selection == "create"
//  or the lobby frag if selection == "join"
    public void onGameSelect(String gameSelection) {
        if (mListener != null) {
            mListener.onGameSelect(gameSelection);
        }
    }


    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Game mGame;
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

        public void bindGame(Game game, int gameNumber) {
            mGame = game;
            mGameNumber = gameNumber;
            mGameIDView.setText(game.getGameID());
            mHostView.setText(game.getUsername());
            mPlayersView.setText(game.getPlayers()+"/"+game.getMaxPlayers());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), "Selected " + mGame.getGameID(), Toast.LENGTH_SHORT).show();
            mSelectedGame = mGameNumber;
            changeAccessibility();
        }
    }

    private class GameAdapter extends RecyclerView.Adapter<GameHolder> {

        private List<Game> mGames;

        public GameAdapter(List<Game> games) {
            mGames = games;
        }

        public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gamelist_view, parent, false);
            return new GameHolder(view);
        }

        public void onBindViewHolder(GameHolder holder, int position) {
            holder.bindGame(mGames.get(position), position);
        }

        public int getItemCount() {
            return mGames.size();
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
