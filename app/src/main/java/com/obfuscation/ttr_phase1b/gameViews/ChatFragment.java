package com.obfuscation.ttr_phase1b.gameViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obfuscation.ttr_phase1b.R;

import java.util.List;

import communication.Message;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ChatFragment extends Fragment implements IChatView {

    private static final String TAG = "ChatFrag";

    private List<Message> mMessages;

    private RecyclerView mMessageRecycler;
    private MessageAdapter mMessageAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatFragment() {
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        mMessageRecycler = (RecyclerView) view.findViewById(R.id.game_recycler_view);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void setMessages(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public void updateUI() {
        Log.d(TAG, "getting updated");
        if(mMessageRecycler != null) {
            Log.d(TAG+"_updateUI", "gamelist: " + mMessages);
            mMessageAdapter = new MessageAdapter(mMessages);
            mMessageRecycler.setAdapter(mMessageAdapter);
        }
    }

    private class MessageHolder extends RecyclerView.ViewHolder {

        Message mMessage;

//        private TextView mGameIDView;
//        private TextView mHostView;
//        private TextView mPlayersView;

        public MessageHolder(View view) {
            super(view);

//            mGameIDView = view.findViewById(R.id.game_id_view);
//            mHostView = view.findViewById(R.id.hostname_view);
//            mPlayersView = view.findViewById(R.id.players_view);
        }

        public void bindGame(Message message) {
//            Log.d(TAG+"_holder", "game: " + game.toString());
            mMessage = message;
//            mGameNumber = gameNumber;
//            mGameIDView.setText(game.getGameID());
//            mHostView.setText(game.getHost());
//            mPlayersView.setText(game.getPlayerCount()+"/"+game.getMaxPlayers());
        }

    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        private List<Message> mMessages;

        public MessageAdapter(List<Message> messages) {
            mMessages = messages;
        }

        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gamelist_view, parent, false);
            return new MessageHolder(view);
        }

        public void onBindViewHolder(MessageHolder holder, int position) {
            Log.d(TAG+"_adapter", "game[" + position + "]: " + mMessages.get(position));
            holder.bindGame(mMessages.get(position));
        }

        public int getItemCount() {
            return mMessages.size();
        }

    }

}
