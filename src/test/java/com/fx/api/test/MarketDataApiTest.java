package com.fx.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Market Data API endpoint tests
 */
@DisplayName("Market Data API Tests")
public class MarketDataApiTest extends BaseTest {

    @Test
    @DisplayName("Test /openapi/quote/v1/depth - Get order book")
    public void testOrderBook() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");
            params.put("limit", "100");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/depth", params);
            System.out.println(response);
            assertNotNull(response);
            assertTrue(response.contains("bids"));
            assertTrue(response.contains("asks"));
            System.out.println("Order book response: " + response);
        } catch (Exception e) {
            fail("Order book test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/depth - Get order book with limit 0")
    public void testOrderBookWithLimitZero() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");
            params.put("limit", "0");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/depth", params);
            assertNotNull(response);
            System.out.println("Order book (limit=0) response: " + response);
        } catch (Exception e) {
            fail("Order book with limit 0 test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/trades - Get recent trades")
    public void testRecentTrades() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");
            params.put("limit", "60");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/trades", params);
            assertNotNull(response);
            System.out.println("Recent trades response: " + response);
        } catch (Exception e) {
            fail("Recent trades test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/klines - Get klines data")
    public void testKlines() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");
            params.put("interval", "1m");
            params.put("limit", "500");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/klines", params);
            assertNotNull(response);
            System.out.println("Klines response: " + response);
        } catch (Exception e) {
            fail("Klines test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/klines - Get klines with time range")
    public void testKlinesWithTimeRange() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");
            params.put("interval", "1h");
//            params.put("startTime", "1499040000000");
//            params.put("endTime", "1499644799999");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/klines", params);
            assertNotNull(response);
            System.out.println("Klines with time range response: " + response);
        } catch (Exception e) {
            fail("Klines with time range test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/24hr - Get 24hr ticker for single symbol")
    public void testTicker24hrSingleSymbol() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/24hr", params);
            assertNotNull(response);
            assertTrue(response.contains("symbol") || response.contains("time"));
            System.out.println("24hr ticker (single symbol) response: " + response);
        } catch (Exception e) {
            fail("24hr ticker single symbol test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/24hr - Get 24hr ticker for all symbols")
    public void testTicker24hrAllSymbols() {
        try {
            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/24hr", null);
            assertNotNull(response);
            System.out.println("24hr ticker (all symbols) response: " + response);
        } catch (Exception e) {
            fail("24hr ticker all symbols test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/price - Get price for single symbol")
    public void testPriceSingleSymbol() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/price", params);
            assertNotNull(response);
            assertTrue(response.contains("price"));
            System.out.println("Price (single symbol) response: " + response);
        } catch (Exception e) {
            fail("Price single symbol test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/price - Get price for all symbols")
    public void testPriceAllSymbols() {
        try {
            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/price", null);
            assertNotNull(response);
            System.out.println("Price (all symbols) response: " + response);
        } catch (Exception e) {
            fail("Price all symbols test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/bookTicker - Get best order book price for single symbol")
    public void testBookTickerSingleSymbol() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("symbol", "USTSITHKTSIT");

            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/bookTicker", params);
            assertNotNull(response);
            assertTrue(response.contains("bidPrice") || response.contains("askPrice"));
            System.out.println("Book ticker (single symbol) response: " + response);
        } catch (Exception e) {
            fail("Book ticker single symbol test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/quote/v1/ticker/bookTicker - Get best order book price for all symbols")
    public void testBookTickerAllSymbols() {
        try {
            String response = apiClient.getWithApiKey("/openapi/quote/v1/ticker/bookTicker", null);
            assertNotNull(response);
            System.out.println("Book ticker (all symbols) response: " + response);
        } catch (Exception e) {
            fail("Book ticker all symbols test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/pairs - Get all spot trading pairs")
    public void testPairs() {
        try {
            String response = apiClient.getWithApiKey("/openapi/v1/pairs", null);
            assertNotNull(response);
            System.out.println("Trading pairs response: " + response);
        } catch (Exception e) {
            fail("Trading pairs test failed: " + e.getMessage());
        }
    }
}
