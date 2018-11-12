package com.obfuscation.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import communication.Game;
import communication.Player;


/**
 * Server class that runs the server
 * Also starts the localhost server
 */


public class Server {
    //port number. Change it if you want
    private static final int portNumber = 8080;

    //test
    private static final String SERVER_FACADE = "com.obfuscation.server.ServerFacade";
    private static final String STRING = "java.lang.String";
    private static final String INTEGER = "java.lang.Integer";
    //    TODO: helps to find the typeName
    private static final String ARRAYLISTCARD = "??";
    private static final String LIST = List.class.getName();

    /**
     * the main function that runs the server
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        //for testing
        System.out.println("server running at port : " + portNumber);

        HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 12);
        server.setExecutor(null);

        //the server context is exec
        server.createContext("/exec", new ExecCommandHandler());
        server.createContext("/", new DefaultHandler());

        server.start();

        //test
        String id = "id";
        String gameID = "gameID";
        String authToken = "authBob";
        String x = "{\"className\":\"com.obfuscation.server.ServerFacade\",\"methodName\":\"JoinLobby\",\"parameterType\":[\"java.lang.String\",\"java.lang.String\",\"java.lang.String\"],\"parameterValue\":[\"id\",\"gameID\",\"authBob\"],\"commandID\":\"e21091e8-ef82-4859-a15d-1801c2d78eee\"}";
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "JoinLobby", new String[]{STRING,STRING,STRING}, new Object[]{id, gameID, authToken});
        System.out.println(new Gson().toJson(genericCommand));

        ServerFacade facade = ServerFacade.getInstance();
//        facade.Register("Jerry", "jerry");
//        facade.Register("Tom", "tom");
//        facade.CreateGame(new Game("Jerry's game", "Jerry", new ArrayList<Player>(), 5), "masterKey");
//        facade.CreateGame(new Game("TOM", "Tom", new ArrayList<Player>(), 3), "masterKey");

    }
}
