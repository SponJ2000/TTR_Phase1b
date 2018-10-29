package com.obfuscation.ttr_phase1b.gameViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.ArrayList;
import java.util.List;

import communication.Message;
import communication.Ticket;
import gamePresenters.ITicketPresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment implements ITicketView {

    private static final String TAG = "TicketFrag";

    private ITicketPresenter mPresenter;

    private List<Ticket> mTickets;
    private boolean[] mChosenTickets;

    private Button mDoneButton;
    private TicketAdapter mTicketAdapter;
    private RecyclerView mTicketRecycler;

    public TicketFragment() {
        mChosenTickets = new boolean[3];
    }


    public static TicketFragment newInstance() {
        TicketFragment fragment = new TicketFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

//      Check to see if we are at beginning of game or not;
//      if we are, then the player needs to select 2 tickets
        if(true) {
            TextView tv = (TextView) view.findViewById(R.id.ticket_header);
            tv.setText("Choose at least 2");
        }

        mDoneButton = (Button) view.findViewById(R.id.ticket_done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Now chosing tickets");
                onDone();
            }
        });

        mTicketRecycler = (RecyclerView) view.findViewById(R.id.ticket_recycler_view);
        mTicketRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        changeAccessibility();

        updateUI();

        return view;
    }

    private void onDone() {
        List<Ticket> tickets = new ArrayList<>();
        for(int i = 0; i < mChosenTickets.length; i++) {
            if(mChosenTickets[i]) {
                tickets.add(mTickets.get(i));
            }
        }
        mPresenter.onFinish(tickets);
    }

    private void changeAccessibility() {
        for(int i = 0; i < mChosenTickets.length; i++) {
            if(mChosenTickets[i]) {
                mDoneButton.setEnabled(true);
                return;
            }
        }
        mDoneButton.setEnabled(false);
    }

    @Override
    public void setTickets(List<Ticket> tickets) {
        mTickets = tickets;
    }

    @Override
    public void updateUI() {
        Log.d(TAG, "getting updated");
        if(mTicketRecycler != null) {
            Log.d(TAG+"_updateUI", "ticketlist: " + mTickets);
            mTicketAdapter = new TicketAdapter(mTickets);
            mTicketRecycler.setAdapter(mTicketAdapter);
        }
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (ITicketPresenter) presenter;
    }


    private class TicketHolder extends RecyclerView.ViewHolder {

        private int mTicket;
        private CheckBox mCheckbox;
        private TextView mDescription;
        private TextView mPoints;

        public TicketHolder(View view) {
            super(view);

            mCheckbox = view.findViewById(R.id.ticket_checkbox);
            mDescription = view.findViewById(R.id.ticket_description);
            mPoints = view.findViewById(R.id.ticket_points);
        }

        public void bind(Ticket ticket, int index) {
            mTicket = index;
            mDescription.setText(ticket.getValue());
            mPoints.setText(ticket.getValue());
            mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
                        mChosenTickets[mTicket] = true;
                    } else {
                        mChosenTickets[mTicket] = false;
                    }
                }

            });
        }

    }

    private class TicketAdapter extends RecyclerView.Adapter<TicketHolder> {

        private List<Ticket> mTickets;

        public TicketAdapter(List<Ticket> tickets) {
            mTickets = tickets;
        }

        public TicketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gamelist_view, parent, false);
            return new TicketHolder(view);
        }

        public void onBindViewHolder(TicketHolder holder, int position) {
            Log.d(TAG+"_adapter", "game[" + position + "]: " + mTickets.get(position));
            holder.bind(mTickets.get(position), position);
        }

        public int getItemCount() {
            return mTickets.size();
        }

    }

}
