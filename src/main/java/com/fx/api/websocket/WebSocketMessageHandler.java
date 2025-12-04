package com.fx.api.websocket;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Interface for handling WebSocket messages
 */
public interface WebSocketMessageHandler {

    /**
     * Called when connection is established
     */
    void onOpen(ServerHandshake handshakedata);

    /**
     * Called when a message is received
     * @param message The received message
     */
    void onMessage(String message);

    /**
     * Called when connection is closed
     * @param code Close code
     * @param reason Close reason
     * @param remote Whether closed by remote
     */
    void onClose(int code, String reason, boolean remote);

    /**
     * Called when an error occurs
     * @param ex The exception
     */
    void onError(Exception ex);
}
