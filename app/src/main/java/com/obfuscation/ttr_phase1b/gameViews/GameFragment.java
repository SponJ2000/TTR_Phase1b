package com.obfuscation.ttr_phase1b.gameViews;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Card;
import communication.City;
import communication.Game;
import communication.GameColor;
import communication.GameMap;
import communication.Player;
import communication.Route;
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

import static communication.GameColor.*;


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

    private Map<Route, Polyline> mRouteLines;
    private Map<LatLng, Route> mRoutes;
    private Map<Route, Marker> mRoutes2;

    MapView mMapView;
    private GoogleMap googleMap;

    private LatLng selected;

    private Map<GameColor, Integer> cardMap;

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

        initCardMap();

        selected = null;

        mChangeButton = rootView.findViewById(R.id.change_button);
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "make changes");
                mPresenter.onChange(getActivity());
            }
        });

        mPlayersButton = rootView.findViewById(R.id.players_button);
        mPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "View players");
//                PlayerInfoDialogFragment playerInfoFrag = PlayerInfoDialogFragment.newInstance();
//                playerInfoFrag.show(getFragmentManager(), "PlayerinfoDialogFragment");
//                mPresenter.showPlayerInfo(playerInfoFrag);
                mPresenter.showPlayerInfo(null);
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

        mMapView.getMapAsync(this);

        mPresenter.update();

        changeAccessibility();

        return rootView;

    }

    private void initCardMap(){
        cardMap = new HashMap<>();
        cardMap.put(PURPLE, R.drawable.card_pur);
        cardMap.put(BLUE, R.drawable.card_blu);
        cardMap.put(ORANGE, R.drawable.card_ora);
        cardMap.put(WHITE, R.drawable.card_whi);
        cardMap.put(GREEN, R.drawable.card_gre);
        cardMap.put(YELLOW, R.drawable.card_yel);
        cardMap.put(BLACK, R.drawable.card_bla);
        cardMap.put(RED, R.drawable.card_red);
        cardMap.put(LOCOMOTIVE, R.drawable.card_loc);
        cardMap.put(GREY, R.drawable.card_blank);
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

        mRouteLines = new HashMap<>();
        mRoutes = new HashMap<>();
        mRoutes2 = new HashMap<>();

        initCities(googleMap);

        initRoutes(googleMap);

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (mRoutes.containsKey(marker.getPosition())) {
                    if (marker.getPosition().equals(selected)) {
                        selectRoute(mRoutes.get(marker.getPosition()));

                    }
                }

                selected = marker.getPosition();

                return false;
            }
        });


        // For zooming automatically to the location of the marker
        LatLng ny = new LatLng(40, -73);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(5).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        //Add routes here

        
    }

    private void initCities(GoogleMap googleMap) {
        mMap = mPresenter.getMap();

        List<City> cities = mMap.getCities();
        LatLng latLng = null;

        BitmapDrawable bmdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.location);
        Bitmap smallMarker = Bitmap.createScaledBitmap(bmdraw.getBitmap(), 100, 100, false);

        for(int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);

            latLng = new LatLng(city.getLat(), city.getLng());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(city.getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_city2)));

        }

//        LatLng ny = new LatLng(41, -74);
//        googleMap.addMarker(new MarkerOptions().position(ny).title("New York").snippet("Aka \"Not Old York\""));
    }

    private void initRoutes(GoogleMap googleMap) {

        List<Route> routes = mMap.getRoutes();
        Route r = null;

        boolean claimed = false;

        for (int i = 0; i < routes.size(); i++) {
            r = routes.get(i);

            int color;
            claimed = false;

            switch (r.getCardColor()) {
                case RED:
                    color = getResources().getColor(R.color.trainRed);
                    break;
                case PURPLE:
                    color = getResources().getColor(R.color.trainPurple);
                    break;
                case BLUE:
                    color = getResources().getColor(R.color.trainBlue);
                    break;
                case GREEN:
                    color = getResources().getColor(R.color.trainGreen);
                    break;
                case YELLOW:
                    color = getResources().getColor(R.color.trainYellow);
                    break;
                case ORANGE:
                    color = getResources().getColor(R.color.trainOrange);
                    break;
                case BLACK:
                    color = getResources().getColor(R.color.trainBlack);
                    break;
                case WHITE:
                    color = getResources().getColor(R.color.trainWhite);
                    break;
                case PLAYER_RED:
                    color = getResources().getColor(R.color.playerRed);
                    claimed = true;
                    break;
                case PLAYER_PURPLE:
                    color = getResources().getColor(R.color.playerPurple);
                    claimed = true;
                    break;
                case PLAYER_BLUE:
                    color = getResources().getColor(R.color.playerBlue);
                    claimed = true;
                    break;
                case PLAYER_YELLOW:
                    color = getResources().getColor(R.color.playerYellow);
                    claimed = true;
                    break;
                case PLAYER_BLACK:
                    color = getResources().getColor(R.color.playerBlack);
                    claimed = true;
                    break;
                default:
                    color = getResources().getColor(R.color.trainGrey);
            }

            LatLng mid = new LatLng(r.getMidPoint()[0], r.getMidPoint()[1]);

            PolylineOptions p = new PolylineOptions()
                    .add(new LatLng(r.getCity1().getLat(), r.getCity1().getLng()),
                            mid,
                            new LatLng(r.getCity2().getLat(), r.getCity2().getLng()))
                    .color(color)
                    .clickable(true);

            mRouteLines.put(r, googleMap.addPolyline(p));

            if(claimed) {
                mRoutes2.put(r,
                        googleMap.addMarker(new MarkerOptions()
                                .position(mid)
                                .title(r.getClaimedBy().getPlayerName())
                                .snippet(new StringBuilder(r.getLength() + " points").toString())
                        ));
            } else {
                mRoutes2.put(r,
                        googleMap.addMarker(new MarkerOptions()
                                .position(mid)
                                .title(r.getLength().toString())
                                .snippet("unclaimed")
                        ));
            }

            mRoutes.put(mid, r);
        }
    }

    private void selectRoute(Route route) {
        if (mIsTurn) {
            mPresenter.selectRoute(route, mPlayer);
        }
        else Toast.makeText(getContext(), "Not your turn", Toast.LENGTH_SHORT);
    }

    private void onChangeButton() {

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
        GameColor color = mPlayer.getPlayerColor();

        ColorStateList stateList = null;
        int colorid = 0;
        int colorid2 = 0;
        int board = 0;

        switch(color) {
            case PLAYER_RED:
                colorid = R.color.playerRed;
                colorid2 = R.color.playerRedLight;
                board = R.drawable.board_r;
                break;
            case PLAYER_YELLOW:
                colorid = R.color.playerYellow;
                colorid2 = R.color.playerYellowLight;
                board = R.drawable.board_y;
                break;
            case PLAYER_PURPLE:
                colorid = R.color.playerPurple;
                colorid2 = R.color.playerPurpleLight;
                board = R.drawable.board_p;
                break;
            case PLAYER_BLACK:
                colorid = R.color.playerBlack;
                colorid2 = R.color.playerBlackLight;
                board = R.drawable.board_bla;
                break;
            case PLAYER_BLUE:
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


    @Override
    public void updateRoute(Route route) {
        String name = route.getClaimedBy().getPlayerName();
        int color = 0;

        switch(route.getClaimedBy().getPlayerColor()) {
            case PLAYER_RED:
                color = getResources().getColor(R.color.playerRed);
                break;
            case PLAYER_BLUE:
                color = getResources().getColor(R.color.playerBlue);
                break;
            case PLAYER_YELLOW:
                color = getResources().getColor(R.color.playerYellow);
                break;
            case PLAYER_PURPLE:
                color = getResources().getColor(R.color.playerPurple);
                break;
            case PLAYER_BLACK:
                color = getResources().getColor(R.color.playerBlack);
                break;
        }

        Polyline p = mRouteLines.get(route);

        p.setColor(color);
        Marker m = mRoutes2.get(route);
        m.setTitle(name);
        m.setSnippet(new StringBuilder(route.getLength() + " points").toString());
    }

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
            setTrains(mPlayer.getCarNum());
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

