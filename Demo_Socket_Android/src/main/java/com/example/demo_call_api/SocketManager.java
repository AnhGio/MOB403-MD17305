package com.example.demo_call_api;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager {
    private static final SocketManager INSTANCE = new SocketManager();

    public static SocketManager getInstance() {
        return INSTANCE;
    }

    private Socket mSocket;

    public Socket getmSocket() { return mSocket; }
    private SocketManager() {
        try {
            mSocket = IO.socket("http://10.82.0.77:3001");
        } catch (URISyntaxException e) {

        }
    }

    public void connect() {
        mSocket.connect();
    }

    public void on(String event, Emitter.Listener fn) {
        mSocket.on(event, fn);
    }

    public void emit(String event, Object... args) {
        mSocket.emit(event, args);
    }
}