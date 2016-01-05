package com.parrott.quinn.remote;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by quinn on 2015-12-21.
 */
public class Networker {
    public static Socket socket;
    public static DataOutputStream outputStream;

    public static final Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            connect();
            ConnectActivity.startSlider();
            sendMode();
            while (true) {
                sendLeftSpeed();
                sendRightSpeed();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Log.e("remote", "Making thread sleep", e);
                }
            }
        }
    });

    private static int leftSpeed = 128;
    private static int rightSpeed = 128;


    public synchronized static void setLeftSpeed(int leftSpeed) {
        Networker.leftSpeed = leftSpeed;
    }

    public synchronized static void setRightSpeed(int rightSpeed) {
        Networker.rightSpeed = rightSpeed;
    }

    private static void sendLeftSpeed(){
        byte[] bytes = {0x00, 0x31, (byte)(short) leftSpeed};
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            Log.e("remote", "Writing left speed", e);
        }
    }

    private static void sendRightSpeed(){
        byte[] bytes = {0x00, 0x32, (byte)(short) rightSpeed};
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            Log.e("remote", "Writing right speed", e);
        }
    }

    private static void sendMode(){
        byte[] bytes = {0x00, 0x2b, 0x00};
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            Log.e("remote", "Writing mode", e);
        }
    }

    public static String address;
    public static int port;

    public static void start(String address, int port){
        Networker.address = address;
        Networker.port = port;
        thread.start();
    }

    private static void connect(){
        boolean connected = true;
        do {
            try {
                socket = new Socket(address, port);
                outputStream = new DataOutputStream(socket.getOutputStream());
                connected = false;
            } catch (IOException e) {
                Log.v("remote", "Opening socket", e);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e("remote", "Sleep Exception", e);
            }

        } while (connected);
    }

}
