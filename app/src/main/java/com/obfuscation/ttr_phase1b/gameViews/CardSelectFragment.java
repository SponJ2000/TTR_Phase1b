package com.obfuscation.ttr_phase1b.gameViews;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;
import com.obfuscation.ttr_phase1b.gameViews.dummy.CardDialog;

import java.util.ArrayList;
import java.util.List;

import communication.Card;
import communication.GameColor;
import gamePresenters.ICardSelectPresenter;

/**
 * Created by jalton on 11/19/18.
 */

public class CardSelectFragment extends Fragment implements ICardSelectView, CardDialog.CardDialogListener {

    private static final String TAG = "TicketFrag";

    private boolean mIsTurn;

    private ICardSelectPresenter mPresenter;

    private int cardsToSelect;

    private TextView mHeader;

    private ImageView[] cardViews;
    private TextView[] cardText;

    public static CardSelectFragment newInstance() {
        CardSelectFragment fragment = new CardSelectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPresenter.update();

        cardsToSelect = 0;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        mHeader = view.findViewById(R.id.ticket_header);

        cardViews = new ImageView[9];
        cardText = new TextView[9];

        int i = 0;

        cardViews[i] = view.findViewById(R.id.card_orange);
        cardText[i] = view.findViewById(R.id.orange_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(0);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_red);
        cardText[i] = view.findViewById(R.id.red_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(1);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_green);
        cardText[i] = view.findViewById(R.id.green_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(2);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_yellow);
        cardText[i] = view.findViewById(R.id.yellow_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(3);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_purple);
        cardText[i] = view.findViewById(R.id.purple_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(4);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_blue);
        cardText[i] = view.findViewById(R.id.blue_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(5);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_white);
        cardText[i] = view.findViewById(R.id.white_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(6);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_black);
        cardText[i] = view.findViewById(R.id.black_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(7);
            }
        });

        i++;

        cardViews[i] = view.findViewById(R.id.card_loc);
        cardText[i] = view.findViewById(R.id.loco_cards);
        cardViews[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelect(8);
            }
        });

        updateUI();

        return view;
    }


    @Override
    public void updateUI() {
        setHand(mPresenter.getHand());
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (ICardSelectPresenter) presenter;
    }

    @Override
    public void sendToast(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT);

    }

    @Override
    public void setHand(int[] hand) {
        for(int i = 0; i < 9; i++) {
            cardText[i].setText(Integer.toString(hand[i]));
        }
    }

    /**
     * Sets the number of cards to choose
     * @param cardsToSelect
     */
    public void setCardsToSelect(int cardsToSelect) {
        this.cardsToSelect = cardsToSelect;
    }

    /**
     * checks to see if the correct cards have been selected. If
     */
    private void onSelect(int i){
        if (cardsToSelect == 0) {
            sendToast("Hold your horses");
            return;
        }

        ArrayList<Integer> toUse = new ArrayList<>();

        //Special case for locomotive
        if(i == 8) {
            int remain = cardsToSelect - Integer.getInteger(cardText[i].toString());
            if (remain > 0) {
                sendToast("Not enough cards: need " + remain + " more");
            }
            else {
                toUse.add(0);
                toUse.add(cardsToSelect);
            }
        }
        else {
            int colorCards = Integer.getInteger(cardText[i].toString());
            int remain = cardsToSelect - colorCards;
            remain -= Integer.getInteger(cardText[8].toString());

            if (remain > 0) {
                sendToast("Not enough cards: need " + remain + " more");
            }
            else {


                //case where you don't need locomotive
                if (colorCards > cardsToSelect) {
                    toUse.add(cardsToSelect);
                    toUse.add(0);
                }
                //case where you need locomotives
                else {
                    toUse.add(colorCards);
                    toUse.add(cardsToSelect - colorCards);
                }
            }
        }

        Bundle args = new Bundle();
        args.putIntegerArrayList("toUse", toUse);
        args.putSerializable("color", indexToColor(i));

        CardDialog dialog = CardDialog.newInstance(args);
        FragmentManager fm = getFragmentManager();
        dialog.show(fm, "confirm");
    }

    private GameColor indexToColor(int i) {
        switch(i) {
            case 0:
                return GameColor.ORANGE;
            case 1:
                return GameColor.RED;
            case 2:
                return GameColor.GREEN;
            case 3:
                return GameColor.YELLOW;
            case 4:
                return GameColor.PURPLE;
            case 5:
                return GameColor.BLUE;
            case 6:
                return GameColor.WHITE;
            case 7:
                return GameColor.BLACK;
            case 8:
                return GameColor.LOCOMOTIVE;
                default: return GameColor.GREY;
        }
    }

    @Override
    public void onConfirmCards(CardDialog dialog) {
        List<Card> cards = new ArrayList<>();
        ArrayList<Integer> toUse = dialog.getToUse();
        GameColor color = dialog.getColor();

        for(int i = 0; i < toUse.get(0); i++) {
            cards.add(new Card(color));
        }

        for(int j = 0; j < toUse.get(1); j++) {
            cards.add(new Card(GameColor.LOCOMOTIVE));
        }
        mPresenter.playerChooseCards(cards);
        dialog.dismiss();
    }

    @Override
    public void onRejectCards(DialogFragment dialog) {
        dialog.dismiss();
    }
}
