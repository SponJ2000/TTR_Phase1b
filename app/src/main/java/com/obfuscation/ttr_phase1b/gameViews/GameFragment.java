package com.obfuscation.ttr_phase1b.gameViews;

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

import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Ticket;
import gamePresenters.IGamePresenter;

//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MapStyleOptions;
//import com.google.android.gms.maps.model.MarkerOptions;


public class GameFragment extends Fragment implements IGameView {

    private static final String TAG = "ChatFrag";

    private IGamePresenter mPresenter;

    private String mUsername;
    private boolean mIsTurn;
    private List<Card> mCards;
    private List<Card> mFaceCards;
    private List<Ticket> mTickets;

    private ImageView[] mFaceCardViews;
    private ImageView mDeck;

    private FloatingActionButton mPlayersButton;
    private FloatingActionButton mTicketsButton;
    private FloatingActionButton mChatButton;

    //    MapView mMapView;
//    private GoogleMap googleMap;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mPresenter.update();

        View rootView = inflater.inflate(R.layout.game_fragment, container, false);

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
                Log.d(TAG, "view tickets");
                mPresenter.showTickets();
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

        changeAccessibility();

//        mMapView = (MapView) rootView.findViewById(R.id.mapView);
//        mMapView.onCreate(savedInstanceState);
//
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
//                                    this, R.raw.map_style.json));
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

    private void changeAccessibility() {
        if(mIsTurn) {
            for(int i = 0; i < mFaceCards.size(); i++) {
                mFaceCardViews[i].setEnabled(true);
            }
            mDeck.setEnabled(true);
        }else {
            for(int i = 0; i < mFaceCards.size(); i++) {
                mFaceCardViews[i].setEnabled(false);
            }
            mDeck.setEnabled(false);
        }
    }

//    public void onMapReady(GoogleMap mMap) {
//        googleMap = mMap;
//
//        try {
//            // Customise the styling of the base map using a JSON object defined
//            // in a raw resource file.
//            boolean success = googleMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(
//                            getActivity(), R.raw.map_style));
//
//            if (!success) {
//                Log.e(TAG, "Style parsing failed.");
//            }
//        } catch (Resources.NotFoundException e) {
//            Log.e(TAG, "Can't find style. Error: ", e);
//        }
//
//
//        // For dropping a marker at a point on the Map
//        LatLng ny = new LatLng(41, 74);
//        googleMap.addMarker(new MarkerOptions().position(ny).title("New York").snippet("Aka \"Not Old York\""));
//
//        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(ny).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setMap(GameMap map) {

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
            for(int i = 0; i < mFaceCards.size(); i++) {
//                mFaceCardViews[i].setImageDrawable();
            }
        }if(mTickets != null) {

        }
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (IGamePresenter) presenter;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }

}

