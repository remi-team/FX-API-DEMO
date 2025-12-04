# FX API Demo - REST API and WebSocket API Testing Project

This project is a Java testing framework implemented based on the FX Trading Platform API documentation, including comprehensive REST API and WebSocket API test cases.

## Project Overview

This project provides:
- Complete REST API tests (market data, account management, order operations)
- Complete WebSocket API tests (real-time market data subscriptions)
- Code examples and documentation

## Project Structure

```
FX-API-DEMO/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── fx/
│   │               └── api/
│   │                   ├── client/
│   │                   │   ├── ApiClient.java          # REST API client core class
│   │                   │   └── ApiConfig.java          # API configuration class
│   │                   ├── util/
│   │                   │   └── SignatureUtil.java      # HMAC SHA256 signature utility
│   │                   └── websocket/
│   │                       ├── FxWebSocketClient.java  # WebSocket client
│   │                       ├── WebSocketMessageHandler.java  # Message handler interface
│   │                       └── model/
│   │                           ├── TickerData.java     # Ticker data model
│   │                           ├── TradeData.java      # Trade data model
│   │                           ├── KlineData.java      # Kline data model
│   │                           ├── DepthData.java      # Depth data model
│   │                           └── IndexData.java      # Index data model
│   └── test/
│       └── java/
│           └── com/
│               └── fx/
│                   └── api/
│                       └── test/
│                           ├── BaseTest.java                # Test base class
│                           ├── GeneralApiTest.java          # General API tests
│                           ├── MarketDataApiTest.java       # Market data API tests
│                           ├── AccountApiTest.java          # Account API tests
│                           ├── OrderApiTest.java            # Order API tests
│                           ├── UserDataStreamApiTest.java   # User data stream API tests
│                           └── WebSocketApiTest.java        # WebSocket API tests (20 test cases)
├── pom.xml
├── README.md
└── websocket.txt                                           # WebSocket API documentation
```

## Technology Stack

- **Java**: 11+
- **Maven**: 3.6+
- **JUnit 5**: 5.9.3
- **OkHttp**: 4.11.0 - REST API HTTP client
- **Java-WebSocket**: 1.5.3 - WebSocket client
- **Gson**: 2.10.1 - JSON serialization/deserialization
- **SLF4J**: 2.0.7 - Logging framework
- **Apache Commons Codec**: 1.15 - HMAC SHA256 signature

## Quick Start

### Install Dependencies

```bash
mvn clean install
```

### Configuration

#### REST API Configuration
Configure in `BaseTest.java`:

```java
protected static String BASE_URL = "https://api.example.com";
protected static String API_KEY = "your-api-key";
protected static String SECRET_KEY = "your-secret-key";
```

#### WebSocket API Configuration
Configure in `WebSocketApiTest.java`:

```java
private static final String WS_URL = "wss://fxapi.example.com/openapi/quote/ws/v1";
```

## Running Tests

### Run All Tests

```bash
mvn test
```

### Run REST API Tests

```bash
# Run general API tests
mvn test -Dtest=GeneralApiTest

# Run market data API tests
mvn test -Dtest=MarketDataApiTest

# Run account API tests
mvn test -Dtest=AccountApiTest

# Run order API tests
mvn test -Dtest=OrderApiTest

# Run user data stream API tests
mvn test -Dtest=UserDataStreamApiTest
```

### Run WebSocket API Tests

```bash
# Run all WebSocket tests
mvn test -Dtest=WebSocketApiTest

# Run specific WebSocket test cases
mvn test -Dtest=WebSocketApiTest#testSubscribeTicker
mvn test -Dtest=WebSocketApiTest#testSubscribeKline1m
mvn test -Dtest=WebSocketApiTest#testSubscribeDepth
mvn test -Dtest=WebSocketApiTest#testHeartbeat
```

---

# WebSocket API Usage Guide

## Features

- **Real-time Ticker Subscription (Ticker/Realtimes)** - 24-hour complete ticker information, refreshed every second
- **Trade Subscription (Trade)** - Push notification for each trade
- **Kline Data Subscription (Kline)** - Support multiple time intervals (1m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 12h, 1d, 1w, 1M)
- **Depth Data Subscription (Depth)** - Order book snapshot (300 levels, updated every 300ms)
- **Incremental Depth Subscription (DiffDepth)** - Order book change notifications
- **Index Data Subscription (Index)** - Option and futures index data
- **Heartbeat Mechanism (Ping/Pong)** - Keep connection alive




# REST API Usage Guide

## API Endpoint Coverage

### 1. General API Endpoints (GeneralApiTest)
- ✅ `GET /openapi/v1/ping` - Test connectivity
- ✅ `GET /openapi/v1/time` - Get server time
- ✅ `GET /openapi/v1/brokerInfo` - Get broker information

### 2. Market Data Endpoints (MarketDataApiTest)
- ✅ `GET /openapi/quote/v1/depth` - Get order book
- ✅ `GET /openapi/quote/v1/trades` - Get recent trades
- ✅ `GET /openapi/quote/v1/klines` - Get kline data
- ✅ `GET /openapi/quote/v1/ticker/24hr` - Get 24-hour ticker
- ✅ `GET /openapi/quote/v1/ticker/price` - Get price
- ✅ `GET /openapi/quote/v1/ticker/bookTicker` - Get best order book price
- ✅ `GET /openapi/v1/pairs` - Get spot trading pairs

### 3. Account Endpoints (AccountApiTest)
- ✅ `GET /openapi/v1/account` - Get account information
- ✅ `GET /openapi/v1/depositOrders` - Get deposit records
- ✅ `GET /openapi/v1/withdraw/detail` - Get specific withdrawal record
- ✅ `GET /openapi/v1/withdrawalOrders` - Get withdrawal records
- ✅ `POST /openapi/v1/balance_flow` - Query balance flow
- ✅ `POST /openapi/v1/withdraw` - Withdraw

### 4. Order Endpoints (OrderApiTest)
- ✅ `POST /openapi/v1/order/test` - Test new order
- ✅ `POST /openapi/v1/order` - Create new order
- ✅ `GET /openapi/v1/order` - Query order
- ✅ `DELETE /openapi/v1/order` - Cancel order
- ✅ `GET /openapi/v1/openOrders` - Get current orders
- ✅ `GET /openapi/v1/historyOrders` - Get historical orders
- ✅ `GET /openapi/v1/myTrades` - Get account trade history

### 5. User Data Stream Endpoints (UserDataStreamApiTest)
- ✅ `POST /openapi/v1/userDataStream` - Start user data stream
- ✅ `PUT /openapi/v1/userDataStream` - Keepalive user data stream
- ✅ `DELETE /openapi/v1/userDataStream` - Close user data stream

## REST API Sample Code
Refer to test case code

## Signature Mechanism

This project implements a complete HMAC SHA256 signature mechanism that complies with API documentation requirements:
- Automatically adds `timestamp` parameter
- Automatically adds `recvWindow` parameter
- Automatically generates signature and adds it to the request
- Supports mixed queryString and requestBody methods

## Important Notes

### Security Tips
The following tests are disabled by default to prevent accidental operations:
- `testWithdraw()` - Withdrawal test
- `testCreateNewOrder()` - Create order test
- `testCancelOrderByOrderId()` - Cancel order test
- `testCancelOrderByClientOrderId()` - Cancel order test

To enable these tests, uncomment the relevant code and ensure they run in a test environment.

### Test Environment Requirements
- Valid API Key and Secret Key required
- Recommended to run tests in test environment
- Some tests require corresponding data in the account for complete validation

### Other Considerations
1. **API Key Security**: Do not commit API Key and Secret Key to version control
2. **Test Environment**: Run tests in test environment to avoid affecting production data
3. **Rate Limits**: Be aware of API rate limits to avoid triggering 429 errors
4. **Time Synchronization**: Ensure system time is accurate to avoid signature verification failures
5. **WebSocket Heartbeat**: Send ping every 30-60 seconds to prevent server disconnection

## License

This project is for learning and testing purposes only.

## Changelog

### v1.0.1 (2025-12-04)
- Added complete WebSocket API support
- Added 20 WebSocket test cases
- Added data model classes (TickerData, TradeData, KlineData, DepthData, IndexData)
- Updated README documentation with detailed WebSocket API usage guide

### v1.0.0
- Initial release
- Complete REST API support
- REST API test cases


## WebSocket Test Cases

The project includes 20 WebSocket test cases (located in `src/test/java/com/fx/api/test/WebSocketApiTest.java`):

1. **testSubscribeTicker** - Subscribe to single trading pair ticker
2. **testSubscribeMultipleTickers** - Subscribe to multiple trading pair tickers
3. **testSubscribeTrade** - Subscribe to trade by trade
4. **testSubscribeKline1m** - Subscribe to 1-minute kline
5. **testSubscribeKlineDifferentIntervals** - Test different time interval klines
6. **testSubscribeDepth** - Subscribe to depth data
7. **testSubscribeDiffDepth** - Subscribe to incremental depth
8. **testSubscribeIndex** - Subscribe to index data
9. **testUnsubscribe** - Unsubscribe from specific topic
10. **testUnsubscribeAll** - Unsubscribe from all topics
11. **testHeartbeat** - Test heartbeat mechanism
12. **testSubscribeWithLimit** - Test limit parameter
13. **testSubscribeWithBinary** - Test binary format
14. **testMultipleSubscriptions** - Subscribe to multiple topics simultaneously
15. **testInvalidSymbol** - Invalid symbol error handling
16. **testInvalidTopic** - Invalid topic error handling
17. **testLongRunningConnection** - Long-running connection test
18. **testReconnection** - Reconnection mechanism test
19. **testHighFrequencyData** - High-frequency data test
20. **testCompleteWorkflow** - Complete workflow test

## WebSocket Error Codes

| Error Code | Description |
|------------|-------------|
| -10000 | Invalid request |
| -10001 | Invalid JSON format |
| -10002 | Invalid event |
| -10003 | Missing event parameter |
| -10004 | Invalid topic |
| -10005 | Missing topic parameter |
| -10007 | Missing params parameter |
| -10008 | Missing period parameter |
| -10009 | Invalid period |
| -100010 | Invalid symbols |

---
