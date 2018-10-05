package com.obfuscation.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * ServerCommunicator class that creates context
 */
public class ServerCommunicator {
    //port number. Change it if you want
    private static final int portNumber = 8080;

    /**
     * A function that runs the server and create contexts.
     * In our case, there is only one context.
     * @throws IOException
     */
    public void run() throws IOException {
        //for testing
        System.out.println("server running at port : " + portNumber);

        HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
        server.setExecutor(null);

        //the server context is exec
        server.createContext("/exec", new ExecCommandHandler());

        server.start();
    }
}
