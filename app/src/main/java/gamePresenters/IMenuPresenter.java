package gamePresenters;

import com.obfuscation.ttr_phase1b.activity.IPresenter;

public interface IMenuPresenter extends IPresenter {
    void goBack();
    void logout();
    void onGameSelection();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnBackListener {
        void onBack();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnLogoutListener {
        void onLogout();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnGameSelectListener {
        void onGameSelect();
    }

}
