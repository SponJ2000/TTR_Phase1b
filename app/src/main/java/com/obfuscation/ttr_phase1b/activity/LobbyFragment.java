package com.obfuscation.ttr_phase1b.activity;

import android.content.Context;
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

import model.ModelFacade;
import server.Game;
import server.Player;

/**
 * A placeholder fragment containing a simple view.
 */
public class LobbyFragment extends Fragment implements IPresenter {

    private static final String TAG = "LobbyFrag";

    private Player mHost;
    private Game mGame;
    private boolean mLeave;
    private boolean mStart;

    private Button mLeaveButton;
    private Button mStartButton;

    private TextView mGameIDView;
    private TextView mHostnameView;
    private TextView mPlayerCount;

    private RecyclerView mLobbyRecycler;

    private LobbyAdapter mLobbyAdapter;

    private OnGameLeaveListener mListener;

    public LobbyFragment() {
        mLeave = false;
        mStart = false;
        mHost = new Player("Bob (the host)");
        List<Player> fakePlayers = new ArrayList<>();
        fakePlayers.add(mHost);
        fakePlayers.add( new Player("player 2") );
        fakePlayers.add( new Player("player 3") );
        fakePlayers.add( new Player("player 4") );
        mGame = new Game("new republic (the game id)", mHost.getmUsername(), fakePlayers, 5);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ModelFacade.getInstance().GetCurrentGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lobby_fragment, container, false);

        mLeaveButton = (Button) view.findViewById(R.id.leave_button);
        mLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now leaving");
                mLeave = true;
                mStart = false;
//                ModelFacade.getInstance().LeaveGame(mGame);
                onGameLeave();
            }
        });

        mStartButton = (Button) view.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now starting");
                mLeave = false;
                mStart = true;
                ModelFacade.getInstance().StartGame(mGame);
            }
        });


        mGameIDView = (TextView) view.findViewById(R.id.game_id);
//      set the TextView at the top to show the username of the person who created the lobby
        mGameIDView.setText(mGame.getGameID());

        mHostnameView = (TextView) view.findViewById(R.id.hostname_view);
//      set the TextView at the top to show the username of the person who created the lobby
        mHostnameView.setText(mHost.getmUsername());

        mPlayerCount = (TextView) view.findViewById(R.id.player_count);
//      set the TextView at the top to show the username of the person who created the lobby
        mPlayerCount.setText(mGame.getPlayerCount() + "/" + mGame.getMaxPlayers());


        mLobbyRecycler = (RecyclerView) view.findViewById(R.id.player_recycler_view);
        mLobbyRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        mLobbyAdapter = new LobbyAdapter(mGame.getPlayers());
        mLobbyRecycler.setAdapter(mLobbyAdapter);
    }

    @Override
    public void onComplete(Object result) {
        if(mLeave) {
            onGameLeave();
            return;
        }else if(mStart) {
            onGameStart();
            return;
        }
    }

    @Override
    public void updateInfo(Object result) {
        mGame = (Game) result;
        updateUI();
    }

//  tells the listener (the activity) to change the fragment back to the game list
    public void onGameLeave() {
        if (mListener != null) {
            mListener.onGameLeave();
        }
    }

    private void onGameStart() {
        Toast.makeText(getActivity(), "starting game", Toast.LENGTH_SHORT).show();
//        Intent intent = GameActivity.newIntent(getContext());
//        startActivity(intent);
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

        private List<Player> mPlayernames;

        public LobbyAdapter(List<Player> playerNames) {
            mPlayernames = playerNames;
        }

        public LobbyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.playerlist_view, parent, false);
            return new LobbyHolder(view);
        }

        public void onBindViewHolder(LobbyHolder holder, int position) {
            holder.bindGame(mPlayernames.get(position).getmUsername());
        }

        public int getItemCount() {
            return mPlayernames.size();
        }

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
