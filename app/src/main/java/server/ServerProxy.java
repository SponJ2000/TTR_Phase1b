package server;


import com.obfuscation.server.GenericCommand;


import java.util.ArrayList;
import java.util.List;

import communication.*;

/**
 * Created by hao on 10/5/18.
 */


public class ServerProxy implements communication.IServer {

    ClientCommunicator clientCommunicator;

    public ServerProxy() {
        clientCommunicator = new ClientCommunicator();
    }

    @Override
    public Result Login(String id, String password) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "Login", new String[]{"java.lang.String","java.lang.String"}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result Register(String id, String password) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "Register", new String[]{"java.lang.String","java.lang.String"}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "JoinGame", new String[]{"java.lang.String","java.lang.String","java.lang.String"}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CreateGame(communication.Game game, String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "CreateGame", new String[]{"communication.Game","java.lang.String"}, new Object[]{game, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "StartGame", new String[]{"java.lang.String","java.lang.String"}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "GetGameList", new String[]{"java.lang.String"}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "GetGame", new String[]{"java.lang.String", "java.lang.String"}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "CheckGameList", new String[]{"java.lang.String"}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "LeaveGame", new String[]{"java.lang.String", "java.lang.String", "java.lang.String"}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGame(String authToken, String gameID) {
        GenericCommand genericCommand = new GenericCommand("com.obfuscation.server.ServerFacade", "CheckGame", new String[]{"java.lang.String", "java.lang.String"}, new Object[]{authToken, gameID});
        return RunCommand(genericCommand);
    }

    private Result RunCommand(GenericCommand genericCommand){
        try {
            String resultJson = clientCommunicator.post(genericCommand);
            System.out.println(resultJson);
            Serializer serializer = new Serializer();
            return serializer.deserializeResult(resultJson);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, "EXCEPTION: " + e.getMessage());
        }
    }

    //Phase 2 stuff


    @Override
    public Result GetUpdates(String authToken, String gameID, int state) {
        return null;
    }

    @Override
    public Result ClaimRoute(String routeID, ArrayList<Card> cards, String authToken) {
        return null;
    }

    @Override
    public Result DrawTrainCard(Integer index, String authToken) {
        return null;
    }

    @Override
    public Result GetTickets(String authToken) {
        return null;
    }

    @Override
    public Result ReturnTickets(List<Ticket> tickets, String authToken) {
        return null;
    }

    @Override
    public Result SendMessage(String message, String gameID , String authToken) {
        return null;
    }
}
