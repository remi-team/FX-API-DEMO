package com.fx.api.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CountDownLatch;

/**
 * FX WebSocket Client for market data and trading operations
 * Based on WebSocket API documentation
 */
public class FxWebSocketClient extends WebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(FxWebSocketClient.class);
    private final Gson gson;
    private CountDownLatch latch;
    private WebSocketMessageHandler messageHandler;

    public FxWebSocketClient(URI serverUri) {
        super(serverUri);
        this.gson = new Gson();
    }

    public FxWebSocketClient(URI serverUri, WebSocketMessageHandler handler) {
        super(serverUri);
        this.gson = new Gson();
        this.messageHandler = handler;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("WebSocket connection opened");
        if (messageHandler != null) {
            messageHandler.onOpen(handshakedata);
        }
    }

    @Override
    public void onMessage(String message) {
        logger.debug("Received message: {}", message);

        try {
            if (message.startsWith("{")){
                JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
                // Handle pong response
                if (jsonObject.has("pong")) {
                    logger.debug("Received pong: {}", jsonObject.get("pong").getAsLong());
                }
            }

            if (messageHandler != null) {
                messageHandler.onMessage(message);
            }
        } catch (Exception e) {
            logger.error("Error parsing message: {}", message, e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("WebSocket connection closed. Code: {}, Reason: {}, Remote: {}", code, reason, remote);
        if (messageHandler != null) {
            messageHandler.onClose(code, reason, remote);
        }
    }

    @Override
    public void onError(Exception ex) {
        logger.error("WebSocket error occurred", ex);
        if (messageHandler != null) {
            messageHandler.onError(ex);
        }
    }

    /**
     * Subscribe to market data topics
     * @param symbols Symbol list, e.g., "BTCUSDT, ETHUSDT"
     * @param topic Topic name: realtimes, trade, kline_1m, depth, diffDepth, index
     * @param binary Whether to use binary format
     */
    public void subscribe(String symbols, String topic, boolean binary) {
        JsonObject request = new JsonObject();
        request.addProperty("symbol", symbols);
        request.addProperty("topic", topic);
        request.addProperty("event", "sub");

        JsonObject params = new JsonObject();
        params.addProperty("binary", binary);
        request.add("params", params);

        String message = gson.toJson(request);
        logger.info("Subscribing: {}", message);
        send(message);
    }

    /**
     * Subscribe with limit parameter
     * @param symbols Symbol list
     * @param topic Topic name
     * @param binary Whether to use binary format
     * @param limit Result limit (for kline, max 2000)
     */
    public void subscribe(String symbols, String topic, boolean binary, int limit) {
        JsonObject request = new JsonObject();
        request.addProperty("symbol", symbols);
        request.addProperty("topic", topic);
        request.addProperty("event", "sub");

        JsonObject params = new JsonObject();
        params.addProperty("binary", binary);
        params.addProperty("limit", limit);
        request.add("params", params);

        String message = gson.toJson(request);
        logger.info("Subscribing: {}", message);
        send(message);
    }

    /**
     * Unsubscribe from topics
     * @param symbols Symbol list
     * @param topic Topic name
     */
    public void unsubscribe(String symbols, String topic) {
        JsonObject request = new JsonObject();
        request.addProperty("symbol", symbols);
        request.addProperty("topic", topic);
        request.addProperty("event", "cancel");

        String message = gson.toJson(request);
        logger.info("Unsubscribing: {}", message);
        send(message);
    }

    /**
     * Unsubscribe from all topics
     * @param symbols Symbol list
     */
    public void unsubscribeAll(String symbols) {
        JsonObject request = new JsonObject();
        request.addProperty("symbol", symbols);
        request.addProperty("event", "cancel_all");

        String message = gson.toJson(request);
        logger.info("Unsubscribing all: {}", message);
        send(message);
    }

    /**
     * Send ping to keep connection alive
     */
    public void sendPing() {
        JsonObject ping = new JsonObject();
        ping.addProperty("ping", System.currentTimeMillis());

        String message = gson.toJson(ping);
        logger.debug("Sending ping: {}", message);
        send(message);
    }

    /**
     * Subscribe to ticker (realtimes)
     * @param symbols Symbol list
     */
    public void subscribeTicker(String symbols) {
        subscribe(symbols, "realtimes", false);
    }

    /**
     * Subscribe to trades
     * @param symbols Symbol list
     */
    public void subscribeTrade(String symbols) {
        subscribe(symbols, "trade", false);
    }

    /**
     * Subscribe to kline
     * @param symbols Symbol list
     * @param interval Interval: 1m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 12h, 1d, 1w, 1M
     */
    public void subscribeKline(String symbols, String interval) {
        subscribe(symbols, "kline_" + interval, false);
    }

    /**
     * Subscribe to depth
     * @param symbols Symbol list
     */
    public void subscribeDepth(String symbols) {
        subscribe(symbols, "depth", false);
    }

    /**
     * Subscribe to incremental depth
     * @param symbols Symbol list
     */
    public void subscribeDiffDepth(String symbols) {
        subscribe(symbols, "diffDepth", false);
    }

    /**
     * Subscribe to index data
     * @param symbols Symbol list
     */
    public void subscribeIndex(String symbols) {
        subscribe(symbols, "index", false);
    }

    public void setMessageHandler(WebSocketMessageHandler handler) {
        this.messageHandler = handler;
    }
}
