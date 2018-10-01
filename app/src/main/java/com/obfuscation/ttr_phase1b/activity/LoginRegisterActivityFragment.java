package com.obfuscation.ttr_phase1b.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.obfuscation.ttr_phase1b.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginRegisterActivityFragment extends Fragment {

    public LoginRegisterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_register, container, false);
    }
}
