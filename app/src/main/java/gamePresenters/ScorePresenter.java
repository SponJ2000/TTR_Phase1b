package gamePresenters;

import com.obfuscation.ttr_phase1b.gameViews.IScoreView;

import communication.Result;
import model.IGameModel;
import model.ModelFacade;

public class ScorePresenter implements IScorePresenter {

    private OnReturnListener listener;
    private IScoreView view;
    private IGameModel model;

    public ScorePresenter(IScoreView view, OnReturnListener listener) {
        this.listener = listener;
        this.view = view;
        this.model = ModelFacade.getInstance();
    }

    @Override
    public void onBack() {
        listener.onReturn();
    }

    @Override
    public void updateInfo(Object result) {
        if(result != null && (result instanceof Result)) {
            Result r = (Result) result;
            if(r.isSuccess()) {
                view.setInfo(model.getPlayerStats());
            }else {
                showToast(r.getErrorInfo());
            }
        }else {
            view.setInfo(model.getPlayerStats());
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void showToast(String toast) {
        view.sendToast(toast);
    }
}
