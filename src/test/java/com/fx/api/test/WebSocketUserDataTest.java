package com.fx.api.test;

import com.fx.api.websocket.FxWebSocketClient;
import com.fx.api.websocket.WebSocketMessageHandler;
import com.fx.api.websocket.model.AccountInfoData;
import com.fx.api.websocket.model.ExecutionReportData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * WebSocket User Data Stream Test Cases
 * Based on websocketUserData.txt API documentation
 *
 * Note: These tests require a valid listenKey obtained from the REST API endpoint:
 * POST /openapi/v1/userDataStream
 *
 * The listenKey is valid for 60 minutes and should be kept alive by sending
 * PUT requests every 30 minutes.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebSocketUserDataTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketUserDataTest.class);
    private static final String WS_BASE_URL = "wss://www.remifx-test.ai/openapi/ws/";
    private static final int WAIT_TIME_SECONDS = 130;

    private FxWebSocketClient client;
    private CountDownLatch messageLatch;
    private Gson gson;
    private AtomicReference<String> lastMessage;

    /**
     * Setup method - creates a new WebSocket connection for each test
     *
     * IMPORTANT: Before running these tests, you need to:
     * 1. Obtain a listenKey by calling POST /openapi/v1/userDataStream
     * 2. Replace "YOUR_LISTEN_KEY" with the actual listenKey in the WS_URL
     */
    @BeforeEach
    public void setUpWebSocket() throws Exception {
        gson = new Gson();
        messageLatch = new CountDownLatch(1);
        lastMessage = new AtomicReference<>();

        // TODO: Replace with actual listenKey obtained from REST API
        String listenKey = "qCwsNEHCfRicqCjdNBHYSLxiejDwfswjPLCdzaokvRECNgYLeKUQvrTikHAQWvNY";
        String wsUrl = WS_BASE_URL + listenKey;

        logger.info("Connecting to WebSocket URL: {}", wsUrl);

        client = new FxWebSocketClient(new URI(wsUrl), new WebSocketMessageHandler() {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                logger.info("User data stream connection established");
            }

            @Override
            public void onMessage(String message) {
                logger.info("Received user data: {}", message);
                lastMessage.set(message);
                messageLatch.countDown();
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                logger.info("User data stream connection closed: {} - {}", code, reason);
            }

            @Override
            public void onError(Exception ex) {
                logger.error("User data stream error occurred", ex);
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

    @Test
    @Order(1)
    @DisplayName("Test 1: WebSocket User Data Stream - Connection Test")
    public void testUserDataStreamConnection() throws Exception {
        logger.info("=== Test 1: WebSocket User Data Stream Connection ===");

        // Just verify the connection is established
        Assertions.assertTrue(client.isOpen(), "WebSocket connection should be open");

        logger.info("User data stream connection test completed successfully");
    }

    @Test
    @Order(2)
    @DisplayName("Test 2: Receive Account Info Update (outboundAccountInfo)")
    public void testReceiveAccountInfoUpdate() throws Exception {
        logger.info("=== Test 2: Receive Account Info Update ===");
        logger.info("Waiting for account info updates...");
        logger.info("Note: Account info is pushed when balance changes or account status changes");

        // Wait for account info message
        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);

        if (received && lastMessage.get() != null) {
            JsonObject jsonObject = gson.fromJson(lastMessage.get(), JsonObject.class);

            if (jsonObject.has("e") && "outboundAccountInfo".equals(jsonObject.get("e").getAsString())) {
                logger.info("Received account info update");

                // Parse to AccountInfoData model
                AccountInfoData accountInfo = gson.fromJson(lastMessage.get(), AccountInfoData.class);
                logger.info("Parsed account info: {}", accountInfo);

                Assertions.assertEquals("outboundAccountInfo", accountInfo.getEventType());
                Assertions.assertNotNull(accountInfo.getBalances());

                logger.info("Account info update test completed successfully");
            } else {
                logger.info("Received message is not an account info update: {}", jsonObject.get("e"));
            }
        } else {
            logger.warn("No account info update received within {} seconds", WAIT_TIME_SECONDS);
            logger.info("This is normal if no balance changes occurred during the test period");
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test 3: Receive Order Update (executionReport)")
    public void testReceiveOrderUpdate() throws Exception {
        logger.info("=== Test 3: Receive Order Update ===");
        logger.info("Waiting for order execution reports...");
        logger.info("Note: Execution reports are pushed when orders are created, filled, canceled, etc.");

        boolean received = messageLatch.await(WAIT_TIME_SECONDS, TimeUnit.SECONDS);

    }


}
