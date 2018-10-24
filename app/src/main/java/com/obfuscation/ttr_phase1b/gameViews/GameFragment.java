package com.obfuscation.ttr_phase1b.gameViews;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obfuscation.ttr_phase1b.R;

import java.util.List;

import communication.Card;
import communication.GameMap;
import communication.Player;

public class GameFragment extends Fragment implements IGameView {

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void UpdateUI() {

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
}
