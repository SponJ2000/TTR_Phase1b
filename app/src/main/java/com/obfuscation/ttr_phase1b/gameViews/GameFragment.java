package com.obfuscation.ttr_phase1b.gameViews;

import android.content.res.ColorStateList;
import android.content.res.Resources;
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

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.ArrayList;
import java.util.List;

import communication.Card;
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

    private String mUsername;
    private boolean mIsTurn;
    private List<Card> mCards;
    private List<Card> mFaceCards;
    private List<Ticket> mTickets;

    private ImageView[] mFaceCardViews;
    private ImageView mDeck;
    private TextView mDeckSize;

    private FloatingActionButton mPlayersButton;
    private FloatingActionButton mTicketsButton;
    private FloatingActionButton mChatButton;

    private LinearLayout mBoard;
    private TextView mTicketsView;
    private TextView mPointsView;
    private TextView mTrainsView;

    private Player mPlayer;
    private GameMap mMap;

    MapView mMapView;
    private GoogleMap googleMap;

    public GameFragment() {
        mUsername = null;
        mIsTurn = false;
        mCards = null;
        mFaceCardViews = null;
        mTickets = null;
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.game_fragment, container, false);

        mTickets = new ArrayList<>();

        mPlayersButton = rootView.findViewById(R.id.players_button);
        mPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "View players");
                mPresenter.showPlayerInfo();
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
        mTrainsView.setText("30");

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

        mDeckSize = rootView.findViewById(R.id.txt_deck);

        mPresenter.update();

        mPlayer = mPresenter.getPlayer();

        initUI();

        changeAccessibility();


        //Gets MapView from xml layout
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

//        mMapView.onResume();
//
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        mMapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                googleMap = mMap;
//
//                try {
//                    // Customise the styling of the base map using a JSON object defined
//                    // in a raw resource file.
//                    boolean success = googleMap.setMapStyle(
//                            MapStyleOptions.loadRawResourceStyle(
//                                    getActivity(), R.raw.map_style));
//
//                    if (!success) {
//                        Log.e(TAG, "Style parsing failed.");
//                    }
//                } catch (Resources.NotFoundException e) {
//                    Log.e(TAG, "Can't find style. Error: ", e);
//                }
//
//
//                // For dropping a marker at a point on the Map
//                LatLng ny = new LatLng(41, 74);
//                googleMap.addMarker(new MarkerOptions().position(ny).title("New York").snippet("Aka \"Not Old York\""));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            }
//        });



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

    private void initUI(){
        mDeck.setBackgroundResource(R.drawable.card_deck2);

        //Init face cards
        updateCards();

        //Set color elements
        setColor();

    }

    private void updateCards(){
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

            mDeckSize.setText(mPresenter.getDeckSize());
        }



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
        updateCards();
    }

    @Override
    public void setTickets(List<Ticket> tickets) {
        mTickets = tickets;

        mTicketsView.setText(new StringBuilder(tickets.size()).toString());
    }

    @Override
    public void setUsername(String username) {
        mUsername = username;
    }

    @Override
    public void setIsTurn(boolean isTurn) {
        mIsTurn = isTurn;
    }

    @Override
    public void updateUI() {
        if(mCards != null) {

        }if(mFaceCards != null) {
            updateCards();

        }if(mTickets != null) {

        }
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (IGamePresenter) presenter;
    }

    @Override
    public void setPoints(int points) {
        mPointsView.setText(new StringBuilder(points).toString());
    }

    @Override
    public void setTrains(int trains) {
        mTrainsView.setText(new StringBuilder(trains).toString());
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

