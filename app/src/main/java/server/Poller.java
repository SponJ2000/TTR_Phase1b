package server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import communication.Result;
import model.ModelFacade;
import model.ModelRoot;
import model.State;

/**
 * Created by hao on 10/5/18.
 */

public class Poller {

    private static Integer gameListVersion = 0;
    private static Integer gameVersion = 0;
    private static boolean running = false;
    private static ScheduledExecutorService scheduledExecutorService;
    private static ScheduledFuture scheduledFuture;

    private static final Runnable CheckUpdates = new Runnable() {
        @Override
        public void run() {
            if (running) {

                switch (ModelRoot.getInstance().getState()) {
                    case GAMELIST:

                        CheckandUpdateGameList();
                        break;
                    case LOBBY:
                        CheckandUpdateGame();
                        break;
                    default:
                        break;
                }
            }
            else {
                scheduledExecutorService.shutdown();
            }
        }
    };

    public static void StartPoller () {
        running = true;
        System.out.println("Poller Starting");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(CheckUpdates,2,5, TimeUnit.SECONDS);

    }

    public static void EndPoller () {
        running = false;
    }

    private static void CheckandUpdateGameList(Integer versionNum) {
        ServerProxy serverProxy = new ServerProxy();


        if (!versionNum.equals(gameListVersion)) {
            ModelFacade.getInstance().UpdateGameList();
        }
    }

    private static void CheckandUpdateGame() {
        ServerProxy serverProxy = new ServerProxy();
        Result result = serverProxy.CheckGame(ModelRoot.getInstance().getAuthToken(),ModelRoot.getInstance().getGame().getGameID());
        if (result.isSuccess()) {
            Integer versionNum = (Integer) result.getData();
            if (!versionNum.equals(gameVersion)) {
                ModelFacade.getInstance().UpdateGame();
                gameVersion = versionNum;
                //what to do after game is updated
            }
        }
        else {
            System.out.println("poller failure");
        }
    }

    private static void CheckandUpdateGameList() {
        ServerProxy serverProxy = new ServerProxy();
        Result result = serverProxy.CheckGameList(ModelRoot.getInstance().getAuthToken());
        if (result.isSuccess()) {
            Integer versionNum = (Integer) result.getData();
            if (!versionNum.equals(gameListVersion)) {
                ModelFacade.getInstance().UpdateGame();
                gameListVersion = versionNum;
            }
        }

    }
}
