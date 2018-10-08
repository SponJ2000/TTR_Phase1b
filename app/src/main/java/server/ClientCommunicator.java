package server;

import com.obfuscation.server.GenericCommand;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import communication.Serializer;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by hao on 10/5/18.
 */

public class ClientCommunicator {

    String url;

    ClientCommunicator() {
<<<<<<< HEAD
        url = "http://10.0.2.2:8080/exec";
=======
        url = "http://localhost/127.0.0.1:8080/";
//        url = "http://127.0.0.1:8080/";
//        url = "http://localhost:8080/";
>>>>>>> 8cce36cfe7582e1b53a6740995c6caeb74c22d8f
    }


    public String post(GenericCommand genericCommand) throws Exception{

        System.out.println("CLIENT: POST: " + url);
        System.out.println();

        Serializer serializer = new Serializer();
        String request = serializer.serializeCommand(genericCommand);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        URL mUrl = new URL(url);

        System.out.println(mUrl.toString());
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();

        sendRequestBody(connection, request);

        getResponseHeaders(connection);

        String response = getResponseBody(connection);
        return response;
    }

    void sendRequestBody(HttpURLConnection connection, String request)
            throws Exception {

        PrintWriter out = new PrintWriter(connection.getOutputStream());
        out.print(request);
        out.close();

        System.out.println("request body:");
        System.out.println(request);
        System.out.println();

    }

    void getResponseHeaders(HttpURLConnection connection) throws Exception {

        System.out.println("response:");

        int status = connection.getResponseCode();
        System.out.println("status: " + status);

        String message = connection.getResponseMessage();
        System.out.println("message: " + message);

        System.out.println();

    }

    String getResponseBody(HttpURLConnection connection) throws Exception {

        String response = "";

        System.out.println("response body:");

        int status = connection.getResponseCode();
        if (status == HTTP_OK) {

            Scanner in = new Scanner(connection.getInputStream());
            while (in.hasNextLine()) {
                String line = in.nextLine();
                response += line + "\n";
            }
            in.close();

        }else{
            Scanner in = new Scanner(connection.getErrorStream());
            while (in.hasNextLine()) {
                String line = in.nextLine();
                response += line + "\n";
            }
            in.close();
        }

        System.out.println();

        return response;

    }

}
