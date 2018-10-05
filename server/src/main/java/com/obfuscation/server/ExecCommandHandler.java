package com.obfuscation.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import communication.ICommand;
import communication.Result;
import communication.Serializer;

/**
 * Created by jalton on 10/1/18.
 */

public class ExecCommandHandler implements HttpHandler {

    /**
     * Overrides HttpHandler's handle method
     *
     * @param httpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            InputStream reqBody = httpExchange.getRequestBody();
            String requestString = readString(reqBody);

            ICommand commandData = new Serializer().deserialize(requestString);

            //execute the command
            Result result = commandData.execute();

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = httpExchange.getResponseBody();
            writeString(new Serializer().serialize(result), respBody);
            respBody.close();
        }
        catch (Exception e) {
            //TODO: handle exception

            e.printStackTrace();
        }
    }

    private ICommand getCommand(ICommand commandData) {
        //TODO : commandData

        return null;
    }

    private String processString(String requestString) {
        //TODO : process string here


        return requestString;
    }

    /**
     * A function that writes to the output stream.
     * @param str serialized result object
     * @param os outputstream
     * @throws IOException
     */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /**
     * A function that reads from the inputstream
     * @param is inputstream
     * @return serialized command object
     * @throws IOException
     */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
