package com.obfuscation.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * ServerCommunicator class
 * Also starts the localhost server
 */
public class Server {
    //port number. Change it if you want
    private static final int portNumber = 8080;
    private HttpServer server;

    /**
     * the main function that runs the server
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //for testing
        System.out.println("server running");

        //run the server
        new Server().run();
    }

    /**
     * A function that runs the server and create contexts.
     * In our case, there is only one context.
     * @throws IOException
     */
    private void run() throws IOException {
        server = HttpServer.create(new InetSocketAddress(portNumber), 0);
        server.setExecutor(null);

        //the server context is exec
        server.createContext("/exec", new ExecCommandHandler());

        server.start();
    }
}
