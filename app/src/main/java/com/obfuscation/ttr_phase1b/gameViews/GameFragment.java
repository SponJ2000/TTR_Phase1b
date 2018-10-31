package com.obfuscation.ttr_phase1b.gameViews;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.obfuscation.ttr_phase1b.R;
import com.obfuscation.ttr_phase1b.activity.IPresenter;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Player;
import gamePresenters.IGamePresenter;

import static android.support.constraint.Constraints.TAG;


public class GameFragment extends Fragment implements IGameView, OnMapReadyCallback {

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    MapView mMapView;
    private GoogleMap googleMap;

    private IGamePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.game_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

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

        return rootView;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setMap(GameMap map) {

    }

    @Override
    public void setCards(List<Card> cards) {

    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void setPresenter(IPresenter presenter) {
        mPresenter = (IGamePresenter) presenter;
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

