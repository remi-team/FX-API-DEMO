package com.fx.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * General API endpoint tests
 */
@DisplayName("General API Tests")
public class GeneralApiTest extends BaseTest {

    @Test
    @DisplayName("Test /openapi/v1/ping - Test connectivity")
    public void testPing() {
        try {
            String response = apiClient.get("/openapi/v1/ping", null);
            assertNotNull(response);
            assertEquals("{}", response);
            System.out.println("Ping response: " + response);
        } catch (Exception e) {
            fail("Ping test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/time - Get server time")
    public void testServerTime() {
        try {
            String response = apiClient.get("/openapi/v1/time", null);
            assertNotNull(response);
            assertTrue(response.contains("serverTime"));
            System.out.println("Server time response: " + response);
        } catch (Exception e) {
            fail("Server time test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/brokerInfo - Get broker information")
    public void testBrokerInfo() {
        try {
            String response = apiClient.get("/openapi/v1/brokerInfo", null);
            assertNotNull(response);
            assertTrue(response.contains("timezone"));
            assertTrue(response.contains("symbols"));
            assertTrue(response.contains("rateLimits"));
            System.out.println("Broker info response: " + response);
        } catch (Exception e) {
            fail("Broker info test failed: " + e.getMessage());
        }
    }
}
