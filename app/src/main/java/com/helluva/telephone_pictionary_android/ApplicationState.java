package com.helluva.telephone_pictionary_android;

import android.app.Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by cal on 2/25/17.
 */

public class ApplicationState extends Application {

    OutputStream outputStream;
    HashMap<String, NodeCallback> callbacks = new HashMap<>();


    public void spinUpSocket() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Socket nodeSocket = new Socket("172.16.100.122", 1337);


                    System.out.println("Ready!");
                    ApplicationState.this.outputStream = nodeSocket.getOutputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(nodeSocket.getInputStream()));
                    while(true) {
                        String receivedLine = reader.readLine();

                        System.out.println("rec " + receivedLine);

                        String[] components = receivedLine.split(":");
                        if (components.length == 2) {
                            String id = components[0];
                            String message = components[1];

                            System.out.println(id);
                            System.out.println(callbacks.keySet());

                            NodeCallback callback = callbacks.get(id);
                            if (callback != null) {
                                callbacks.remove(id);
                                callback.receivedString(message);
                            }
                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public boolean sendMessage(String message, NodeCallback callback) {

        if (outputStream == null) { return false; }

        long time = new java.util.Date().getTime();
        String messageId = "" + time;
        String messageWithId = messageId + ":" + message;

        System.out.println("Send message: " + messageWithId);
        this.callbacks.put(messageId, callback);

        try {
            outputStream.write(messageWithId.getBytes("UTF8"));
            return true;
        } catch(IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public interface NodeCallback {
        public void receivedString(String message);
    }

}
