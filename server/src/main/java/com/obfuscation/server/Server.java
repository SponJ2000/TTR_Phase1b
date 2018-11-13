package com.obfuscation.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import communication.Card;
import communication.Game;
import communication.GameColor;
import communication.Message;
import communication.Player;
import communication.PlayerUser;
import communication.Ticket;


/**
 * Server class that runs the server
 * Also starts the localhost server
 */


public class Server {
    //port number. Change it if you want
    private static final int portNumber = 8000;

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
        String gameID = "GAME";
        String authToken = "authBob";




        ArrayList<Ticket> tickets = new ArrayList<>();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(GameColor.BLACK));
        cards.add(new Card(GameColor.RED));

        String x = "{\"className\":\"com.obfuscation.server.ServerFacade\",\"methodName\":\"ClaimRoute\",\"parameterType\":[\"java.lang.String\",\"java.lang.String\",\"java.util.List\",\"java.lang.String\"],\"parameterValue\":[\"GAME\",\"ROUTE\",[{\"color\":\"BLACK\"},{\"color\":\"RED\"}],\"authBob\"]}";

        String routeID = "ROUTE";
        Message message = new Message("Bob", "HELLO WORLD");
        GenericCommand genericCommand = new GenericCommand(SERVER_FACADE, "ClaimRoute", new String[]{STRING, STRING, LIST, STRING}, new Object[]{gameID, routeID, cards, authToken});
        System.out.println(new Gson().toJson(genericCommand));

        ServerFacade facade = ServerFacade.getInstance();
//        facade.Register("Jerry", "jerry");
//        facade.Register("Tom", "tom");
//        facade.CreateGame(new Game("Jerry's game", "Jerry", new ArrayList<Player>(), 5), "masterKey");
//        facade.CreateGame(new Game("TOM", "Tom", new ArrayList<Player>(), 3), "masterKey");

    }
}
