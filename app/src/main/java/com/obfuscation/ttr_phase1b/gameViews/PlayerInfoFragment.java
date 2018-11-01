package com.obfuscation.ttr_phase1b.gameViews;

//import android.app.AlertDialog;
//import android.app.Dialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.List;

import communication.Player;
import gamePresenters.IGamePresenter;

public class PlayerInfoFragment extends DialogFragment implements IPlayerInfoView {

    private static final String TAG = "PlayerInfo";

    private IGamePresenter mPresenter;

    private RecyclerView mPlayerRecycler;
    private PlayerAdapter mPlayerAdapter;

    private List<Player> mPlayers;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.player_fragment, null);
        mPlayerRecycler = view.findViewById(R.id.playerinfo_recycler_view);

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void setPlayers(List<Player> players) {
        mPlayers = players;
    }

    @Override
    public void updateUI() {
        Log.d(TAG, "getting updated");
        if(mPlayerRecycler != null && mPlayers != null) {
            Log.d(TAG+"_updateUI", "gamelist: " + mPlayers);
            mPlayerAdapter = new PlayerAdapter(mPlayers);
            mPlayerRecycler.setAdapter(mPlayerAdapter);
        }
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (IGamePresenter) presenter;
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {

        private TextView mPlayerNameView;
        private TextView mPlayerPointView;
        private TextView mPlayerTrainsView;
        private TextView mPlayerCardsView;

        public PlayerHolder(View view) {
            super(view);

            mPlayerNameView = view.findViewById(R.id.player_name);
            mPlayerPointView = view.findViewById(R.id.player_points);
            mPlayerCardsView = view.findViewById(R.id.player_cards);
            mPlayerTrainsView = view.findViewById(R.id.player_trains);
        }

        public void bind(Player player) {
            mPlayerNameView.setText(player.getPlayerName());
            mPlayerPointView.setText(player.getPoint());
            mPlayerCardsView.setText(player.getCardNum());
            mPlayerTrainsView.setText(player.getTrainCarNum());
        }

    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {

        private List<Player> mPlayers;

        public PlayerAdapter(List<Player> players) {
            mPlayers = players;
        }

        public PlayerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.playerinfo_view, parent, false);
            return new PlayerHolder(view);
        }

        public void onBindViewHolder(PlayerHolder holder, int position) {
            Log.d(TAG+"_adapter", "message[" + position + "]: " + mPlayers.get(position));
            holder.bind(mPlayers.get(position));
        }

        public int getItemCount() {
            return mPlayers.size();
        }

    }

}
