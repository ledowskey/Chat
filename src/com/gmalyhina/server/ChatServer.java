package com.gmalyhina.server;

import com.gmalyhina.network.TCPConnection;
import com.gmalyhina.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by DELL on 12.09.2017.
 */
public class ChatServer implements TCPConnectionListener {
    private final ArrayList<TCPConnection> connections = new ArrayList<>();

    public static void main(String[] args) {
        new ChatServer();


    }


    private ChatServer() {
        System.out.println("Server is running ...");
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept()); // waiting for a new incoming connection.

                } catch (IOException e) {
                    System.out.println("TCPConnection exception: " + e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sentToAllConnections("Client connected: " + tcpConnection.toString());

    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sentToAllConnections(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sentToAllConnections("Client disconnected: " + tcpConnection.toString());

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);

    }

    private void sentToAllConnections(String value) {
        System.out.println(value);
        for (TCPConnection connection : connections)
            connection.sendString(value);
    }
}
