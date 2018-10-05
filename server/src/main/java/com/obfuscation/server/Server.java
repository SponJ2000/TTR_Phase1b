package com.obfuscation.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Server class that runs the server
 * Also starts the localhost server
 */


public class Server {
    /**
     * the main function that runs the server
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //run the server
        new ServerCommunicator().run();
    }
}
