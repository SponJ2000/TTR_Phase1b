package server;


import com.obfuscation.server.GenericCommand;


import java.util.ArrayList;
import java.util.List;

import communication.*;
import model.ModelRoot;

/**
 * Created by hao on 10/5/18.
 */


public class ServerProxy implements communication.IServer {

    ClientCommunicator clientCommunicator;
    private static final String SERVER_FACADE = "com.obfuscation.server.ServerFacade";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = "java.lang.Integer";
//    TODO: helps to find the typeName
    private static final String ARRAYLISTCARD = "??";
    private static final String LIST = List.class.getName();

    public ServerProxy() {
        clientCommunicator = new ClientCommunicator();
    }

    @Override
    public Result Login(String id, String password) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "Login", new String[]{STRING,STRING}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result Register(String id, String password) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "Register", new String[]{STRING,STRING}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result JoinGameLobby(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "JoinGameLobby", new String[]{STRING,STRING,STRING}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CreateLobby(LobbyGame lobbyGame, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "CreateLobby", new String[]{"communication.LobbyGame",STRING}, new Object[]{lobbyGame, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "StartGame", new String[]{STRING,STRING}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "GetGameList", new String[]{STRING}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "GetGame", new String[]{STRING, STRING}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "CheckGameList", new String[]{STRING}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result ChooseTicket(String authToken, String gameID, List<Ticket> chosenTickets) {
        System.out.println("CHOOOSE TICKEEEEEEEEET");
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "ChooseTicket", new String[]{STRING, STRING, LIST}, new Object[]{authToken, gameID, chosenTickets});
        System.out.println("GGG");
        return RunCommand(genericCommand);
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "LeaveGame", new String[]{STRING, STRING, STRING}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGame(String authToken, String gameID, Integer state) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "CheckGame", new String[]{STRING, STRING, INTEGER}, new Object[]{authToken, gameID, state});
        System.out.println("from poller checking the game");
        return RunCommand(genericCommand);
    }

    private Result RunCommand(GenericCommand genericCommand){
        try {
            System.out.println("RUNNING COMMAND " + genericCommand.methodName);
            String resultJson = clientCommunicator.post(genericCommand);
            System.out.println(resultJson);
            Serializer serializer = new Serializer();
            Result result = serializer.deserializeResult(resultJson);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, "EXCEPTION: " + e.getMessage());
        }
    }

    //Phase 2 stuff
    @Override
    public Result GetUpdates(String authToken, String gameID, Integer state) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "GetUpdates", new String[]{STRING, STRING, INTEGER}, new Object[]{authToken, gameID, state});
        return RunCommand(genericCommand);
    }

    @Override
    public Result ClaimRoute(String routeID, ArrayList<Card> cards, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "ClaimRoute", new String[]{STRING, LIST, STRING}, new Object[]{routeID, cards, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result DrawTrainCard(Integer index, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "DrawTrainCard", new String[]{INTEGER, STRING}, new Object[]{index, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetTickets(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "GetTickets", new String[]{STRING, STRING}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result ReturnTickets(String gameID, String authToken,  List<Ticket> ticketsToKeep) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "ReturnTickets", new String[]{STRING,STRING,LIST}, new Object[]{ModelRoot.getInstance().getGame().getGameID(), authToken,ticketsToKeep});
        return RunCommand(genericCommand);
    }

    @Override
    public Result SendMessage(String authToken, String gameID, Message message) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "SendMessage", new String[]{STRING,STRING,Message.class.getName()}, new Object[]{authToken, gameID, message});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGameLobby(String authToken, String gameID) {
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "CheckGameLobby", new String[]{STRING,STRING}, new Object[]{authToken, gameID});
        return RunCommand(genericCommand);
    }
}
