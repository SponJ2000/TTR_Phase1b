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
import dao.DAOFacade;
import dao.PlugInManager;
import sqldao.SQLFactory;
import tsvdao.TSVDaoFactory;


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
        //get the persistancy type and number of commands
        PlugInManager plugInManager = new PlugInManager();

//        if (args.length != 2) {
//            System.out.println("persistence_type and number_of_commands-between-checkpoints");
//            return;
//        }
        for (String s : args) {
            System.out.println(s);
        }

        String persistenceType = args[0];
        int commandNum = Integer.parseInt(args[1]);

        String wipe = null;
        if (args.length > 2) {
            wipe = args[2];
        }

        //TODO : add clear functionality

        String fileDirectory = System.getProperty("user.dir") + "/server/src/main/java/";

        switch (persistenceType) {
            case "sqlite":
                plugInManager.setFactory(fileDirectory, "RelationalDB2.jar", SQLFactory.class.getName());
                break;
            case "tsv":
                plugInManager.setFactory(fileDirectory, "FlatFileDB1.jar", TSVDaoFactory.class.getName());
                break;
        }
        if (wipe != null && wipe.equals("-wipe")) {
            System.out.println("WIPTING OUT");
            DAOFacade.getInstance().clear();
        }
        Database.getInstance().initializeDatabase(commandNum);

        //for testing
        System.out.println("server running at port : " + portNumber);

        HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 12);
        server.setExecutor(null);

        //the server context is exec
        server.createContext("/exec", new ExecCommandHandler());
        server.createContext("/", new DefaultHandler());

        server.start();
    }
}
