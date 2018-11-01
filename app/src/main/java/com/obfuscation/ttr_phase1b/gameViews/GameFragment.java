package com.obfuscation.ttr_phase1b.gameViews;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.ArrayList;
import java.util.List;

import communication.Card;
import communication.CardColor;
import communication.GameMap;
import communication.Player;
import communication.PlayerColor;
import communication.Ticket;
import gamePresenters.IGamePresenter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;


public class GameFragment extends Fragment implements IGameView, OnMapReadyCallback {

    private static final String TAG = "ChatFrag";

    private IGamePresenter mPresenter;

    private int changeIndex;

    private boolean mIsTurn;
    private List<Card> mCards;
    private List<Card> mFaceCards;
    private List<Ticket> mTickets;

    private ImageView[] mFaceCardViews;
    private ImageView mDeck;
    private TextView mDeckSize;
    private TextView[] mCardViews;

    private FloatingActionButton mPlayersButton;
    private FloatingActionButton mTicketsButton;
    private FloatingActionButton mChatButton;

    private FloatingActionButton mChangeButton;

    private LinearLayout mBoard;
    private TextView mTicketsView;
    private TextView mPointsView;
    private TextView mTrainsView;

    private Player mPlayer;
    private GameMap mMap;

    MapView mMapView;
    private GoogleMap googleMap;

    public GameFragment() {
        mIsTurn = false;
        mCards = null;
        mFaceCardViews = null;
        mTickets = null;
        changeIndex = 0;
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.game_fragment, container, false);

        mChangeButton = rootView.findViewById(R.id.change_button);
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "make changes");
                mPresenter.onChange();
            }
        });

        mPlayersButton = rootView.findViewById(R.id.players_button);
        mPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "View players");
                PlayerInfoFragment playerInfoFrag = new PlayerInfoFragment();
                playerInfoFrag.show(getFragmentManager(), "PlayerinfoFragment");
                mPresenter.showPlayerInfo(playerInfoFrag);
            }
        });

        mTicketsButton = rootView.findViewById(R.id.tickets_button);
        mTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mIsTurn) {
                    Log.d(TAG, "view tickets");
                    mPresenter.showTickets();
                }
            }
        });

        mChatButton = rootView.findViewById(R.id.chat_button);
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "view chat");
                mPresenter.showChat();
            }
        });

        mBoard = rootView.findViewById(R.id.bottom_board);
        mTicketsView = rootView.findViewById(R.id.txt_tickets);
        mPointsView = rootView.findViewById(R.id.txt_points);
        mTrainsView = rootView.findViewById(R.id.txt_trains);

        mPointsView.setText("0");
        mTrainsView.setText("40");

        mFaceCardViews = new ImageView[5];
        mFaceCardViews[0] = rootView.findViewById(R.id.card1);
        mFaceCardViews[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(0);
            }
        });

        mFaceCardViews[1] = rootView.findViewById(R.id.card2);
        mFaceCardViews[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(1);
            }
        });

        mFaceCardViews[2] = rootView.findViewById(R.id.card3);
        mFaceCardViews[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(2);
            }
        });

        mFaceCardViews[3] = rootView.findViewById(R.id.card4);
        mFaceCardViews[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(3);
            }
        });

        mFaceCardViews[4] = rootView.findViewById(R.id.card5);
        mFaceCardViews[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(4);
            }
        });

        mDeck = rootView.findViewById(R.id.deck);
        mDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.chooseCard(-1);
            }
        });
        mDeck.setBackgroundResource(R.drawable.card_deck2);

        mDeckSize = rootView.findViewById(R.id.txt_deck);

        mCardViews = new TextView[9];
        mCardViews[0] = rootView.findViewById(R.id.txt_cards_orange);
        mCardViews[1] = rootView.findViewById(R.id.txt_cards_green);
        mCardViews[2] = rootView.findViewById(R.id.txt_cards_purple);
        mCardViews[3] = rootView.findViewById(R.id.txt_cards_white);
        mCardViews[4] = rootView.findViewById(R.id.txt_cards_locomotive);
        mCardViews[5] = rootView.findViewById(R.id.txt_cards_red);
        mCardViews[6] = rootView.findViewById(R.id.txt_cards_yellow);
        mCardViews[7] = rootView.findViewById(R.id.txt_cards_blue);
        mCardViews[8] = rootView.findViewById(R.id.txt_cards_black);


        //Gets MapView from xml layout
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = googleMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getActivity(), R.raw.map_style));

                    if (!success) {
                        Log.e(TAG, "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e(TAG, "Can't find style. Error: ", e);
                }


                // For dropping a marker at a point on the Map
                LatLng ny = new LatLng(41, 74);
                googleMap.addMarker(new MarkerOptions().position(ny).title("New York").snippet("Aka \"Not Old York\""));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        mPresenter.update();

        changeAccessibility();

        return rootView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //googleMap = mMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        // Drop markers on all the cities
        LatLng ny = new LatLng(41, 74);
        googleMap.addMarker(new MarkerOptions().position(ny).title("New York").snippet("Aka \"Not Old York\""));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Add routes here

        
    }

    private void changeAccessibility() {
        if(mIsTurn) {
            for(int i = 0; i < mFaceCardViews.length; i++) {
                mFaceCardViews[i].setEnabled(true);
            }
            mDeck.setEnabled(true);
        }else {
            for(int i = 0; i < mFaceCardViews.length; i++) {
                mFaceCardViews[i].setEnabled(false);
            }
            mDeck.setEnabled(false);
        }
    }

    private void updateCards() {
        int[] cardCnts = new int[mCardViews.length];
        for(int i = 0; i < mCards.size(); i++) {
            switch (mCards.get(i).getColor()) {
                case ORANGE:
                    cardCnts[0]++;
                    break;
                case GREEN:
                    cardCnts[1]++;
                    break;
                case PURPLE:
                    cardCnts[2]++;
                    break;
                case WHITE:
                    cardCnts[3]++;
                    break;
                case LOCOMOTIVE:
                    cardCnts[4]++;
                    break;
                case RED:
                    cardCnts[5]++;
                    break;
                case YELLOW:
                    cardCnts[6]++;
                    break;
                case BLUE:
                    cardCnts[7]++;
                    break;
                case BLACK:
                    cardCnts[8]++;
                    break;
            }
        }
        for(int i = 0; i < mCardViews.length; i++) {
            mCardViews[i].setText("" + cardCnts[i]);
        }
    }

    private void updateFaceCards() {
        int i = 0;
        while (i < mFaceCards.size()) {
            Card card = mFaceCards.get(i);
            ImageView faceCardView = mFaceCardViews[i];

            switch (card.getColor()) {
                case RED:
                    faceCardView.setImageResource(R.drawable.card_red);
                    break;
                case ORANGE:
                    faceCardView.setImageResource(R.drawable.card_ora);
                    break;
                case YELLOW:
                    faceCardView.setImageResource(R.drawable.card_yel);
                    break;
                case GREEN:
                    faceCardView.setImageResource(R.drawable.card_gre);
                    break;
                case BLUE:
                    faceCardView.setImageResource(R.drawable.card_blu);
                    break;
                case PURPLE:
                    faceCardView.setImageResource(R.drawable.card_pur);
                    break;
                case BLACK:
                    faceCardView.setImageResource(R.drawable.card_bla);
                    break;
                case WHITE:
                    faceCardView.setImageResource(R.drawable.card_whi);
                    break;
                case LOCOMOTIVE:
                    faceCardView.setImageResource(R.drawable.card_loc);
                    break;
                default:
                    faceCardView.setImageResource(R.drawable.card_blank);
            }
            i++;
        }

        while (i < 5) {
            ImageView faceCardView = mFaceCardViews[i];
            faceCardView.setImageResource(R.drawable.card_blank);

            ++i;
        }
    }

    private void updateTickets() {
        mTicketsView.setText("" + mTickets.size());
    }

    private void setColor() {
        PlayerColor color = mPlayer.getPlayerColor();

        ColorStateList stateList = null;
        int colorid = 0;
        int colorid2 = 0;
        int board = 0;

        switch(color) {
            case RED:
                colorid = R.color.playerRed;
                colorid2 = R.color.playerRedLight;
                board = R.drawable.board_r;
                break;
            case YELLOW:
                colorid = R.color.playerYellow;
                colorid2 = R.color.playerYellowLight;
                board = R.drawable.board_y;
                break;
            case PURPLE:
                colorid = R.color.playerPurple;
                colorid2 = R.color.playerPurpleLight;
                board = R.drawable.board_p;
                break;
            case BLACK:
                colorid = R.color.playerBlack;
                colorid2 = R.color.playerBlackLight;
                board = R.drawable.board_bla;
                break;
            case BLUE:
                colorid = R.color.playerBlue;
                colorid2 = R.color.playerBlueLight;
                board = R.drawable.board_blu;
                break;
        }

        mChatButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorid)));
        mPlayersButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorid)));
        mTicketsButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(colorid)));

        mBoard.setBackgroundResource(board);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void setMap(GameMap map) {
        mMap = map;
    }

    @Override
    public void setPlayer(Player player) {
        mPlayer = player;
    }

    @Override
    public void setCards(List<Card> cards) {
        mCards = cards;
    }

    @Override
    public void setFaceCards(List<Card> cards) {
        mFaceCards = cards;
    }

    @Override
    public void setTickets(List<Ticket> tickets) {
        mTickets = tickets;
    }

    @Override
    public void setIsTurn(boolean isTurn) {
        mIsTurn = isTurn;
    }

    @Override
    public void updateUI() {
        if(mPlayer != null) {
            Log.d(TAG, "updateUI: player: " + mPlayer);
            setColor();
            setPoints(mPlayer.getPoint());
            setTrains(mPlayer.getTrainCarNum());
        }if(mCards != null) {
            updateCards();
        }if(mFaceCards != null) {
            updateFaceCards();
        }if(mTickets != null) {
            updateTickets();
        }
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (IGamePresenter) presenter;
    }

    @Override
    public void setPoints(int points) {
        mPointsView.setText("" + points);
    }

    @Override
    public void setTrains(int trains) {
        mTrainsView.setText("" + trains);
    }

    @Override
    public void setDeckSize(int size) {
        mDeckSize.setText("" + size);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}

