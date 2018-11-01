package server;

import com.obfuscation.server.GenericCommand;
import com.obfuscation.ttr_phase1b.activity.PresenterFacade;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import communication.ICommand;
import communication.Result;
import communication.Serializer;
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
                        System.out.println("in the state of game");
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
        ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(CheckUpdates,1,1, TimeUnit.SECONDS);

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
        if (result.isSuccess()) {
            Integer versionNum = (Integer) result.getData();
            System.out.println(versionNum + " : " + gameVersion);
            if (!versionNum.equals(gameVersion)) {
                ModelFacade.getInstance().UpdateGame();
                gameVersion = versionNum;
            }
        }
        else {
            System.out.println("poller failure");
        }
    }

    private static void CheckandUpdateGame() {
        ServerProxy serverProxy = new ServerProxy();
        System.out.println("check and update gamem is called");
        System.out.print("current gamestate is: ");
        System.out.print(ModelRoot.getInstance().getGame().getState());
        System.out.print('\n');

        Result result = serverProxy.CheckGame(ModelRoot.getInstance().getAuthToken(),ModelRoot.getInstance().getGame().getGameID(), ModelRoot.getInstance().getGame().getState());
        System.out.println(result.toString());
        if (result.isSuccess()) {
            System.out.println(result.toString());
            Serializer serializer = new Serializer();
            ArrayList<Object> commands = (ArrayList<Object>)result.getData();

            System.out.print("get a list of of command with size" );

            System.out.print(commands.size());
            System.out.print('\n');
            System.out.print("Command type: " + result.getData().getClass().toString());
            System.out.println(result.toString());

            for (int i = 0 ; i < commands.size(); i++) {
                System.out.println("get into this loop");
                try {
                    GenericCommand c = (GenericCommand) serializer.deserializeCommand(commands.get(i).toString());
                    System.out.println("Command Detail");
                    System.out.println("command class: " + c.className);
                    System.out.println("command method: " + c.methodName);
//                System.out.println("command method: " + c.methodName);

                    c.execute();
                    CommandTask commandTask = new CommandTask();
                    commandTask.execute();
                    ModelRoot.getInstance().getGame().stateIncreament();
                    System.out.print("New game state after : ");
                    System.out.print(ModelRoot.getInstance().getGame().getState());
                    System.out.print("\n");
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        else {
            System.out.println("poller failure");
            System.out.println(result.getErrorInfo());
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
