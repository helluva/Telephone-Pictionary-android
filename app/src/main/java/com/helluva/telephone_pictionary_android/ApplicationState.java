package com.helluva.telephone_pictionary_android;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
    static boolean hasSpunUpSocket = false;

    HashMap<String, NodeCallback> callbacks = new HashMap<>();
    HashMap<String, HashMap<String, NodeCallback>> listeners = new HashMap<>();


    public void spinUpSocket() {

        if (ApplicationState.hasSpunUpSocket) return;
        ApplicationState.hasSpunUpSocket = true;

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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

                        String[] components = receivedLine.split("/");
                        String idOrMethodName = components[0];

                        String message = "";
                        if (components.length > 1) {
                            message = components[1];
                        }

                        NodeCallback responseHandler = callbacks.get(idOrMethodName);
                        if (responseHandler != null) {
                            callbacks.remove(idOrMethodName);
                            responseHandler.receivedString(message);
                        }

                        else { //if there was no request matching the ID, check if there are listeners for the method
                            HashMap<String, NodeCallback> listenersForMethod = listeners.get(idOrMethodName);
                            if (listenersForMethod != null) {
                                for (NodeCallback listener : listenersForMethod.values()) {
                                    listener.receivedString(message);
                                }
                            }

                            else {
                                System.out.println("Unhandled message.");
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


    //MARK: - Requests

    /**
     * Sends a message to the node server
     * @param message The string message to send
     * @return The timestamp/id of the message
     */
    public String sendMessage(String message) {
        if (outputStream == null) { return null; }

        long time = new java.util.Date().getTime();
        String messageId = "" + time;
        String messageWithId = messageId + "/" + message + "\r\n";

        System.out.println("Send message: " + messageWithId);

        try {
            outputStream.write(messageWithId.getBytes("UTF8"));
            return messageId;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a message to the node server
     * @param message The string message to send
     * @param callback a handler that will be called if/when the server response to the request
     */
    public void makeRequest(String message, NodeCallback callback) {
        String messageId = sendMessage(message);
        if (messageId == null) { return; }

        this.callbacks.put(messageId, callback);
    }


    //MARK: - Listeners

    public void registerListenerForNodeMethod(String methodName, String listenerName, NodeCallback callback) {
        HashMap<String, NodeCallback> listenersForMethod = this.listeners.get(methodName);
        if (listenersForMethod == null) {
            listenersForMethod = new HashMap<>();
        }

        listenersForMethod.put(listenerName, callback);
        this.listeners.put(methodName, listenersForMethod);
    }

    public void unregisterAllListenersForNodeMethod(String methodName) {
        this.listeners.remove(methodName);
    }

    public void unregisterListenerNamed(String listenerName) {
        for (HashMap<String, NodeCallback> listenersForMethod : listeners.values()) {
            listenersForMethod.remove(listenerName);
        }
    }


    //MARK: - Callback

    public interface NodeCallback {
        public void receivedString(String message);
    }

}
