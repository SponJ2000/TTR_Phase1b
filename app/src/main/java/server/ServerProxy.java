package server;


import com.obfuscation.server.GenericCommand;

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
        GenericCommand genericCommand = new GenericCommand("IServer", "Login", new String[]{"String","String"}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result Register(String id, String password) {
        GenericCommand genericCommand = new GenericCommand("IServer", "Register", new String[]{"String","String"}, new Object[]{id,password});
        return RunCommand(genericCommand);
    }

    @Override
    public Result JoinGame(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "JoinGame", new String[]{"String","String","String"}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CreateGame(communication.Game game, String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "CreateGame", new String[]{"communication.Game","String"}, new Object[]{game, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result StartGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "StartGame", new String[]{"String","String"}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "GetGameList", new String[]{"String"}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result GetGame(String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "GetGame", new String[]{"String", "String"}, new Object[]{gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGameList(String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "CheckGameList", new String[]{"String"}, new Object[]{authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result LeaveGame(String id, String gameID, String authToken) {
        GenericCommand genericCommand = new GenericCommand("IServer", "LeaveGame", new String[]{"String", "String", "String"}, new Object[]{id, gameID, authToken});
        return RunCommand(genericCommand);
    }

    @Override
    public Result CheckGame(String authToken, String gameID) {
        GenericCommand genericCommand = new GenericCommand("IServer", "CheckGame", new String[]{"String", "String"}, new Object[]{authToken, gameID});
        return RunCommand(genericCommand);
    }

    private Result RunCommand(GenericCommand genericCommand){
        try {
            String resultJson = clientCommunicator.post(genericCommand);
            Serializer serializer = new Serializer();
            return serializer.deserializeResult(resultJson);
        }
        catch (Exception e) {
            return new Result(false, null, "EXCEPTION: " + e.getMessage());
        }
    }
}
