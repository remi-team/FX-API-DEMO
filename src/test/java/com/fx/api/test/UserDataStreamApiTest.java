package com.fx.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * User Data Stream API endpoint tests
 * Tests should be run in order: start -> keepalive -> close
 */
@DisplayName("User Data Stream API Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDataStreamApiTest extends BaseTest {

    private static String listenKey;

    @Test
    @Order(1)
    @DisplayName("Test /openapi/v1/userDataStream - Start user data stream")
    public void testStartUserDataStream() {
        try {
            Map<String, String> params = new HashMap<>();

            String response = apiClient.postWithSignature("/openapi/v1/userDataStream", params);
            assertNotNull(response);
            assertTrue(response.contains("listenKey"));
            System.out.println("Start user data stream response: " + response);

            // Extract listenKey for subsequent tests (simple extraction)
            if (response.contains("listenKey")) {
                int start = response.indexOf("\"listenKey\"") + 13;
                int end = response.indexOf("\"", start);
                if (start > 12 && end > start) {
                    listenKey = response.substring(start, end);
                    System.out.println("Extracted listenKey: " + listenKey);
                }
            }
        } catch (Exception e) {
            fail("Start user data stream test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test /openapi/v1/userDataStream - Keepalive user data stream")
    public void testKeepaliveUserDataStream() {
        try {
            if (listenKey == null || listenKey.isEmpty()) {
                System.out.println("WARNING: listenKey not available, using test value");
                listenKey = "aormncNfMWXbpaHEZPCtYYLyyYenAImfGXnsqXFGjDSJrYBACAvZNfTLRkOXGqBQ";
            }

            Map<String, String> params = new HashMap<>();
            params.put("listenKey", listenKey);

            String response = apiClient.putWithSignature("/openapi/v1/userDataStream", params);
            assertNotNull(response);
            assertEquals("{}", response);
            System.out.println("Keepalive user data stream response: " + response);
        } catch (Exception e) {
            fail("Keepalive user data stream test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test /openapi/v1/userDataStream - Close user data stream")
    public void testCloseUserDataStream() {
        try {
            if (listenKey == null || listenKey.isEmpty()) {
                System.out.println("WARNING: listenKey not available, using test value");
                listenKey = "1A9LWJjuMwKWYP4QQPw34GRm8gz3x5AephXSuqcDef1RnzoBVhEeGE963CoS1Sgj";
            }

            Map<String, String> params = new HashMap<>();
            params.put("listenKey", listenKey);

            String response = apiClient.deleteWithSignature("/openapi/v1/userDataStream", params);
            assertNotNull(response);
            assertEquals("{}", response);
            System.out.println("Close user data stream response: " + response);
        } catch (Exception e) {
            fail("Close user data stream test failed: " + e.getMessage());
        }
    }
}
