package gamePresenters;

import android.os.Bundle;

import com.obfuscation.ttr_phase1b.activity.IPresenter;

public interface IPTicketsPresenter extends IPresenter {

    void onBack();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnBackListener {
        void onBack();
    }

}
