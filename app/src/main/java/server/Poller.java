package server;

import com.obfuscation.server.GenericCommand;

import java.util.ArrayList;
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
    public static Integer gameVersion = 0;
    private static boolean running = false;
    private static ScheduledExecutorService scheduledExecutorService;
    private static ScheduledFuture scheduledFuture;
    private static final int checkTime = 1;

    private static final Runnable CheckUpdates = new Runnable() {
        @Override
        public void run() {
            if (running) {
                System.out.println("***********************************************" + ModelRoot.getInstance().getState());
                switch (ModelRoot.getInstance().getState()) {
                    case GAMELIST:
                        CheckandUpdateGameList();
                        break;
                    case LOBBY:
                        CheckandUpdateGameLobby();
                        break;
                    case GAME:
                        CheckandUpdateGame();
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
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(CheckUpdates,2,checkTime, TimeUnit.SECONDS);

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

//    Note: this is just a copy of last phase
    private static void CheckandUpdateGameLobby() {
        ServerProxy serverProxy = new ServerProxy();
        Result result = serverProxy.CheckGameLobby(ModelRoot.getInstance().getAuthToken(),ModelRoot.getInstance().getGame().getGameID());
        System.out.println("TEST : " + result.toString());
        if (result.isSuccess()) {
            Integer versionNum = (Integer) result.getData();
            System.out.println(versionNum + " : " + gameVersion);
            if (!versionNum.equals(gameVersion)) {
                System.out.println(")))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
                ModelFacade.getInstance().UpdateGame();
                gameVersion = versionNum;
                //what to do after game is updated
                ModelRoot.getInstance().getGame().increaseGameState();
            }
        }
        else {
            System.out.println("poller failure");
        }
    }

    private static void CheckandUpdateGame() {
        ServerProxy serverProxy = new ServerProxy();
        Result result = serverProxy.CheckGame(ModelRoot.getInstance().getAuthToken(),ModelRoot.getInstance().getGame().getGameID(), ModelRoot.getInstance().getGame().getState());
        if (result.isSuccess()) {
            ArrayList<GenericCommand> commands = (ArrayList<GenericCommand>)result.getData();
            for (GenericCommand c: commands) {
                c.execute();
            }
        }
        else {
            System.out.println("poller failure");
        }
    }

    private static void CheckandUpdateGameList() {
        System.out.println("Poller working");
        ServerProxy serverProxy = new ServerProxy();
        Result result = serverProxy.CheckGameList(ModelRoot.getInstance().getAuthToken());
        if (result.isSuccess()) {
            Integer versionNum = (Integer) result.getData();
            System.out.println(versionNum + " " + gameListVersion);
            if (!versionNum.equals(gameListVersion)) {
                ModelFacade.getInstance().UpdateGameList();
                gameListVersion = versionNum;
            }
        }

    }
}
