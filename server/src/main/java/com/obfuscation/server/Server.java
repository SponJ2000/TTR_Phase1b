package com.obfuscation.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.GameLobby;
import communication.Player;


/**
 * Server class that runs the server
 * Also starts the localhost server
 */


public class Server {
    //port number. Change it if you want
    private static final int portNumber = 8080;

    /**
     * the main function that runs the server
     * @param args
     * @throws IOException
     */
    private static final String SERVER_FACADE = "com.obfuscation.server.ServerFacade";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = "java.lang.Integer";
    //    TODO: helps to find the typeName
    private static final String ARRAYLISTCARD = "??";
    private static final String LIST = List.class.getName();
    public static void main(String[] args) throws IOException {

        //for testing
        System.out.println("server running at port : " + portNumber);

        HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 12);
        server.setExecutor(null);
//        String id = "333";
//        String password = "!!";
//        String game = "game";
//        String authToken = "masterKey";
//        GameLobby gameLobby = new GameLobby("Bob", 3);
//        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "CreateLobby", new String[]{"communication.Game",STRING}, new Object[]{gameLobby, authToken});
//        System.out.println(new Gson().toJson(genericCommand));
//        String x = "{\"className\":\"com.obfuscation.server.ServerFacade\",\"methodName\":\"CreateLobby\",\"parameterType\":[\"communication.Game\",\"java.lang.String\"],\"parameterValue\":[{\"gameID\":\"6317bc2e-a908-451f-9cad-c4b887620b3f\",\"host\":\"Bob\",\"maxPlayers\":3,\"playerListVerisonNum\":0,\"players\":[],\"started\":false},\"masterKey\"]}";
//        //the server context is exec
        server.createContext("/exec", new ExecCommandHandler());
        server.createContext("/", new DefaultHandler());

        server.start();


        ServerFacade facade = ServerFacade.getInstance();
//        facade.Register("Jerry", "jerry");
//        facade.Register("Tom", "tom");
//        facade.CreateGame(new Game("Jerry's game", "Jerry", new ArrayList<Player>(), 5), "masterKey");
//        facade.CreateGame(new Game("TOM", "Tom", new ArrayList<Player>(), 3), "masterKey");

    }
}
