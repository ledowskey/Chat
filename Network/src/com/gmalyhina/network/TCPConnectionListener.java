package com.gmalyhina.network;

/**
 * Created by DELL on 12.09.2017.
 */
public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);               // Connection is ready.
    void onReceiveString(TCPConnection tcpConnection, String value);   // Receive a string (message).
    void onDisconnect(TCPConnection tcpConnection);                    // Disconnect (interrupted connection).
    void onException(TCPConnection tcpConnection, Exception e);        // Exception (exception has been caught).
}
