package com.fx.api.test;

import com.fx.api.websocket.FxWebSocketClient;
import com.fx.api.websocket.WebSocketMessageHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * WebSocket API Test Cases
 * Based on websocket.txt API documentation
 */
public class WebSocketApiTest {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketApiTest.class);
    private static final String WS_URL = "wss://www.remifx-test.ai/openapi/quote/ws/v1";
    private static final int WAIT_TIME_SECONDS = 10;

    private FxWebSocketClient client;
    private CountDownLatch messageLatch;
    private Gson gson;

    @BeforeEach
    public void setUp() throws Exception {
        gson = new Gson();
        messageLatch = new CountDownLatch(1);

        client = new FxWebSocketClient(new URI(WS_URL), new WebSocketMessageHandler() {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                logger.info("Connection established");
            }

            @Override
            public void onMessage(String message) {
                logger.info("Received: {}", message);
                messageLatch.countDown();
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                logger.info("Connection closed: {} - {}", code, reason);
            }

            @Override
            public void onError(Exception ex) {
                logger.error("Error occurred", ex);
            }
        });

        client.connectBlocking(5, TimeUnit.SECONDS);
        Thread.sleep(1000); // Wait for connection to stabilize
    }

    @AfterEach
    public void tearDown() {
        if (client != null && !client.isClosed()) {
            client.close();
        }
    }
//{"symbol":"USTSITHKTSIT","symbolName":"USTSITHKTSIT","topic":"realtimes","params":{"realtimeInterval":"24h","binary":"false"},"data":[{"t":1764848040083,"s":"USTSITHKTSIT","sn":"USTSITHKTSIT","c":"7.7991","h":"7.8003","l":"7.7962","o":"7.7989","v":"100556315.8","qv":"784200776.813788","m":"0","e":301}],"f":true,"sendTime":1764848076511,"shared":false}
    @Test
    @DisplayName("Test 1: Subscribe to Ticker (Realtimes)")
    public void testSubscribeTicker() throws Exception {
        logger.info("=== Test 1: Subscribe to Ticker ===");

        // Subscribe to BTCUSDT ticker
        client.subscribeTicker("USTSITHKTSIT");

        // Wait for response
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        logger.info("Ticker subscription test completed. Received data: {}", received);
    }

//    @Test
//    @DisplayName("Test 2: Subscribe to Multiple Tickers")
//    public void testSubscribeMultipleTickers() throws Exception {
//        logger.info("=== Test 2: Subscribe to Multiple Tickers ===");
//
//
//        // Subscribe to multiple symbols
//        client.subscribeTicker("USTSITHKTSIT, EUTSITUSTSIT");
//
//        // Wait for responses
//        Thread.sleep(WAIT_TIME_SECONDS * 1000);
//        logger.info("Multiple tickers subscription test completed");
//    }

    @Test
    @DisplayName("Test 3: Subscribe to Trade")
    public void testSubscribeTrade() throws Exception {
        logger.info("=== Test 3: Subscribe to Trade ===");

        // Subscribe to trade data
        client.subscribeTrade("USTSITHKTSIT");

        // Wait for response
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        logger.info("Trade subscription test completed. Received data: {}", received);
    }
//[WebSocketConnectReadThread-15] INFO com.fx.api.test.WebSocketApiTest - Received: {"symbol":"USTSITHKTSIT","symbolName":"USTSITHKTSIT","topic":"kline","params":{"realtimeInterval":"24h","klineType":"1m","binary":"false"},"data":[{"t":1764848520000,"s":"USTSITHKTSIT","sn":"USTSITHKTSIT","c":"7.7991","h":"7.7991","l":"7.7991","o":"7.7991","v":"0"}],"f":true,"sendTime":1764848561478,"shared":false}
    @Test
    @DisplayName("Test 4: Subscribe to Kline 1m")
    public void testSubscribeKline1m() throws Exception {
        logger.info("=== Test 4: Subscribe to Kline 1m ===");

        // Subscribe to 1-minute kline
        client.subscribeKline("USTSITHKTSIT", "1m");

        // Wait for response
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        logger.info("Kline 1m subscription test completed. Received data: {}", received);
    }
//[WebSocketConnectReadThread-15] INFO com.fx.api.test.WebSocketApiTest - Received: {"symbol":"USTSITHKTSIT","symbolName":"USTSITHKTSIT","topic":"kline","params":{"realtimeInterval":"24h","klineType":"1m","binary":"false"},"data":[{"t":1764848580000,"s":"USTSITHKTSIT","sn":"USTSITHKTSIT","c":"7.7991","h":"7.7991","l":"7.7991","o":"7.7991","v":"0"}],"f":true,"sendTime":1764848608274,"shared":false}
    @Test
    @DisplayName("Test 5: Subscribe to Kline with Different Intervals")
    public void testSubscribeKlineDifferentIntervals() throws Exception {
        logger.info("=== Test 5: Subscribe to Kline with Different Intervals ===");

        // Test different intervals
        String[] intervals = {"1m", "5m", "15m", "30m", "1h", "4h", "1d", "1w", "1M"};

        for (String interval : intervals) {
            logger.info("Testing interval: {}", interval);
            client.subscribeKline("USTSITHKTSIT", interval);
            Thread.sleep(2000);
        }

        logger.info("Multiple kline intervals test completed");
    }
//[WebSocketConnectReadThread-17] INFO com.fx.api.test.WebSocketApiTest - Received: {"symbol":"USTSITHKTSIT","symbolName":"USTSITHKTSIT","topic":"depth","params":{"realtimeInterval":"24h","binary":"false"},"data":[{"e":301,"s":"USTSITHKTSIT","t":1764848356460,"v":"181710_18","b":[["7.77","15793.65"],["7.768","27412.62"],["7.7605","20660.36"],["7.7536","50795.75"],["7.7473","21597.91"],["7.7469","16349.65"],["7.7458","40685.89"],["7.7409","25216.86"],["7.7393","19077.49"],["7.7344","16678.3"],["7.733","35120.64"],["7.7293","40185.65"],["7.7282","19603.4"],["7.723","31576.98"],["7.7203","31597.72"],["7.7182","12331.9"],["7.7169","10200.87"],["7.7118","9081.03"],["7.7109","20890.64"],["7.7072","26095.11"],["7.6986","23256.98"],["7.6889","24990.33"]],"a":[["7.7763","999987.1405"],["7.8048","12323.69"],["7.8101","32135.08"],["7.8161","24453.85"],["7.8166","19115.23"],["7.8192","38072.49"],["7.8264","28547.96"],["7.8325","13702.61"],["7.835","12838.57"],["7.8404","14364.17"],["7.844","35796.41"],["7.8469","7885.66"],["7.8523","28471.68"],["7.8577","14750.4"],["7.8647","38458.26"],["7.8675","36218.12"],["7.8761","23014.04"],["7.8819","26512.8"],["7.8868","18688.03"],["7.894","20507.67"],["7.8962","14151.87"],["7.9015","12222.2"],["7.9097","13114.25"]],"o":0}],"f":true,"sendTime":1764848646554,"shared":false}
    @Test
    @DisplayName("Test 6: Subscribe to Depth")
    public void testSubscribeDepth() throws Exception {
        logger.info("=== Test 6: Subscribe to Depth ===");

        // Subscribe to depth data
        client.subscribeDepth("USTSITHKTSIT");

        // Wait for response
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        logger.info("Depth subscription test completed. Received data: {}", received);
    }
//[WebSocketConnectReadThread-15] INFO com.fx.api.test.WebSocketApiTest - Received: {"symbol":"USTSITHKTSIT","symbolName":"USTSITHKTSIT","topic":"diffDepth","params":{"realtimeInterval":"24h","binary":"false"},"data":[{"e":301,"s":"USTSITHKTSIT","t":1764848656655,"v":"181715_18","b":[["7.77","15793.65"],["7.768","27412.62"],["7.7605","20660.36"],["7.7536","50795.75"],["7.7473","21597.91"],["7.7469","16349.65"],["7.7458","40685.89"],["7.7409","25216.86"],["7.7393","19077.49"],["7.7344","16678.3"],["7.733","35120.64"],["7.7293","40185.65"],["7.7282","19603.4"],["7.723","31576.98"],["7.7203","31597.72"],["7.7182","12331.9"],["7.7169","10200.87"],["7.7118","9081.03"],["7.7109","20890.64"],["7.7072","26095.11"],["7.6986","23256.98"],["7.6889","24990.33"]],"a":[["7.7763","999987.1405"],["7.8048","12323.69"],["7.8101","32135.08"],["7.8161","24453.85"],["7.8166","19115.23"],["7.8192","38072.49"],["7.8264","28547.96"],["7.8325","13702.61"],["7.835","12838.57"],["7.8404","14364.17"],["7.844","35796.41"],["7.8469","7885.66"],["7.8523","28471.68"],["7.8577","14750.4"],["7.8647","38458.26"],["7.8675","36218.12"],["7.8761","23014.04"],["7.8819","26512.8"],["7.8868","18688.03"],["7.894","20507.67"],["7.8962","14151.87"],["7.9015","12222.2"],["7.9097","13114.25"]],"o":0}],"f":true,"sendTime":1764848689836,"shared":false}
    @Test
    @DisplayName("Test 7: Subscribe to Incremental Depth")
    public void testSubscribeDiffDepth() throws Exception {
        logger.info("=== Test 7: Subscribe to Incremental Depth ===");

        // Subscribe to incremental depth data
        client.subscribeDiffDepth("USTSITHKTSIT");

        // Wait for responses
        Thread.sleep(WAIT_TIME_SECONDS * 1000);
        logger.info("Incremental depth subscription test completed");
    }

//    @Test
//    @DisplayName("Test 8: Subscribe to Index")
//    public void testSubscribeIndex() throws Exception {
//        logger.info("=== Test 8: Subscribe to Index ===");
//
//        // Subscribe to index data
//        client.subscribeIndex("HTUSDT");
//
//        // Wait for response
//        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
//        logger.info("Index subscription test completed. Received data: {}", received);
//    }

    @Test
    @DisplayName("Test 9: Unsubscribe from Topic")
    public void testUnsubscribe() throws Exception {
        logger.info("=== Test 9: Unsubscribe from Topic ===");

        // First subscribe
        client.subscribeTicker("USTSITHKTSIT");
        Thread.sleep(3000);

        // Then unsubscribe
        client.unsubscribe("USTSITHKTSIT", "realtimes");
        Thread.sleep(2000);

        logger.info("Unsubscribe test completed");
    }

    @Test
    @DisplayName("Test 10: Unsubscribe All")
    public void testUnsubscribeAll() throws Exception {
        logger.info("=== Test 10: Unsubscribe All ===");

        // Subscribe to multiple topics
        client.subscribeTicker("USTSITHKTSIT");
        client.subscribeTrade("USTSITHKTSIT");
        client.subscribeKline("USTSITHKTSIT", "1m");
        Thread.sleep(3000);

        // Unsubscribe all
        client.unsubscribeAll("USTSITHKTSIT");
        Thread.sleep(2000);

        logger.info("Unsubscribe all test completed");
    }

    @Test
    @DisplayName("Test 11: Ping-Pong Heartbeat")
    public void testHeartbeat() throws Exception {
        logger.info("=== Test 11: Ping-Pong Heartbeat ===");

        // Send ping
        client.sendPing();

        // Wait for pong response
        Thread.sleep(2000);

        logger.info("Heartbeat test completed");
    }

    @Test
    @DisplayName("Test 12: Subscribe with Limit Parameter")
    public void testSubscribeWithLimit() throws Exception {
        logger.info("=== Test 12: Subscribe with Limit Parameter ===");

        // Subscribe to kline with limit
        client.subscribe("USTSITHKTSIT", "kline_1m", false, 100);

        // Wait for response
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);
        logger.info("Subscribe with limit test completed. Received data: {}", received);
    }

    @Test
    @DisplayName("Test 13: Subscribe with Binary Format")
    public void testSubscribeWithBinary() throws Exception {
        logger.info("=== Test 13: Subscribe with Binary Format ===");

        // Subscribe with binary format
        client.subscribe("USTSITHKTSIT", "realtimes", true);

        // Wait for response
        Thread.sleep(WAIT_TIME_SECONDS * 1000);
        logger.info("Binary format subscription test completed");
    }

//    @Test
//    @DisplayName("Test 14: Multiple Subscriptions")
//    public void testMultipleSubscriptions() throws Exception {
//        logger.info("=== Test 14: Multiple Subscriptions ===");
//
//        // Subscribe to multiple topics simultaneously
//        client.subscribeTicker("BTCUSDT, ETHUSDT");
//        Thread.sleep(1000);
//
//        client.subscribeTrade("BTCUSDT, ETHUSDT");
//        Thread.sleep(1000);
//
//        client.subscribeKline("BTCUSDT, ETHUSDT", "1m");
//        Thread.sleep(1000);
//
//        client.subscribeDepth("BTCUSDT, ETHUSDT");
//        Thread.sleep(WAIT_TIME_SECONDS * 1000);
//
//        logger.info("Multiple subscriptions test completed");
//    }

//    @Test
//    @DisplayName("Test 15: Error Handling - Invalid Symbol")
//    public void testInvalidSymbol() throws Exception {
//        logger.info("=== Test 15: Error Handling - Invalid Symbol ===");
//
//        // Subscribe with invalid symbol
//        client.subscribeTicker("USTSITHKTSIT");
//
//        // Wait for error response
//        Thread.sleep(WAIT_TIME_SECONDS * 1000);
//        logger.info("Invalid symbol error handling test completed");
//    }

//    @Test
//    @DisplayName("Test 16: Error Handling - Invalid Topic")
//    public void testInvalidTopic() throws Exception {
//        logger.info("=== Test 16: Error Handling - Invalid Topic ===");
//
//        // Subscribe with invalid topic
//        client.subscribe("USTSITHKTSIT", "invalid_topic", false);
//
//        // Wait for error response
//        Thread.sleep(WAIT_TIME_SECONDS * 1000);
//        logger.info("Invalid topic error handling test completed");
//    }
//
//    @Test
//    @DisplayName("Test 17: Long Running Connection")
//    public void testLongRunningConnection() throws Exception {
//        logger.info("=== Test 17: Long Running Connection ===");
//
//        // Subscribe to ticker
//        client.subscribeTicker("USTSITHKTSIT");
//
//        // Send periodic pings to keep connection alive
//        for (int i = 0; i < 3; i++) {
//            Thread.sleep(30000); // Wait 30 seconds
//            client.sendPing();
//            logger.info("Sent ping #{}", i + 1);
//        }
//
//        logger.info("Long running connection test completed");
//    }
//
//    @Test
//    @DisplayName("Test 18: Reconnection Handling")
//    public void testReconnection() throws Exception {
//        logger.info("=== Test 18: Reconnection Handling ===");
//
//        // Subscribe to ticker
//        client.subscribeTicker("USTSITHKTSIT");
//        Thread.sleep(3000);
//
//        // Close connection
//        client.close();
//        Thread.sleep(2000);
//
//        // Reconnect
//        client = new FxWebSocketClient(new URI(WS_URL));
//        client.connectBlocking(5, TimeUnit.SECONDS);
//        Thread.sleep(1000);
//
//        // Subscribe again
//        client.subscribeTicker("USTSITHKTSIT");
//        Thread.sleep(3000);
//
//        logger.info("Reconnection handling test completed");
//    }

    @Test
    @DisplayName("Test 19: High Frequency Data")
    public void testHighFrequencyData() throws Exception {
        logger.info("=== Test 19: High Frequency Data ===");

        // Subscribe to high-frequency data sources
        client.subscribeTrade("USTSITHKTSIT");
        client.subscribeDiffDepth("USTSITHKTSIT");

        // Receive data for a period
        Thread.sleep(WAIT_TIME_SECONDS * 1000);

        logger.info("High frequency data test completed");
    }

    @Test
    @DisplayName("Test 20: Complete Workflow")
    public void testCompleteWorkflow() throws Exception {
        logger.info("=== Test 20: Complete Workflow ===");

        // Step 1: Connect and send heartbeat
        client.sendPing();
        Thread.sleep(2000);

        // Step 2: Subscribe to ticker
        logger.info("Step 2: Subscribing to ticker...");
        client.subscribeTicker("USTSITHKTSIT");
        Thread.sleep(3000);

        // Step 3: Subscribe to kline
        logger.info("Step 3: Subscribing to kline...");
        client.subscribeKline("USTSITHKTSIT", "1m");
        Thread.sleep(3000);

        // Step 4: Subscribe to depth
        logger.info("Step 4: Subscribing to depth...");
        client.subscribeDepth("USTSITHKTSIT");
        Thread.sleep(3000);

        // Step 5: Subscribe to trades
        logger.info("Step 5: Subscribing to trades...");
        client.subscribeTrade("USTSITHKTSIT");
        Thread.sleep(3000);

        // Step 6: Receive data for some time
        logger.info("Step 6: Receiving data...");
        for (int i = 0; i < 3; i++) {
            Thread.sleep(10000);
            client.sendPing();
            logger.info("Heartbeat sent");
        }

        // Step 7: Unsubscribe from specific topic
        logger.info("Step 7: Unsubscribing from ticker...");
        client.unsubscribe("USTSITHKTSIT", "realtimes");
        Thread.sleep(2000);

        // Step 8: Unsubscribe all
        logger.info("Step 8: Unsubscribing all...");
        client.unsubscribeAll("BTCUSDT");
        Thread.sleep(2000);

        logger.info("Complete workflow test completed");
    }
}
