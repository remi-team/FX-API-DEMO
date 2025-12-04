# FX API Demo - REST API 和 WebSocket API 测试工程

本项目是基于 FX 交易平台 API 文档实现的 Java 测试工程，包含完整的 REST API 和 WebSocket API 测试用例。

## 项目简介

本项目提供：
- 完整的 REST API 测试（市场数据、账户管理、订单操作）
- 完整的 WebSocket API 测试（实时市场数据订阅）
- 代码示例和文档

## 项目结构

```
FX-API-DEMO/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── fx/
│   │               └── api/
│   │                   ├── client/
│   │                   │   ├── ApiClient.java          # REST API客户端核心类
│   │                   │   └── ApiConfig.java          # API配置类
│   │                   ├── util/
│   │                   │   └── SignatureUtil.java      # HMAC SHA256签名工具类
│   │                   └── websocket/
│   │                       ├── FxWebSocketClient.java  # WebSocket客户端
│   │                       ├── WebSocketMessageHandler.java  # 消息处理接口
│   │                       └── model/
│   │                           ├── TickerData.java     # Ticker数据模型
│   │                           ├── TradeData.java      # 交易数据模型
│   │                           ├── KlineData.java      # K线数据模型
│   │                           ├── DepthData.java      # 深度数据模型
│   │                           └── IndexData.java      # 指数数据模型
│   └── test/
│       └── java/
│           └── com/
│               └── fx/
│                   └── api/
│                       └── test/
│                           ├── BaseTest.java                # 测试基类
│                           ├── GeneralApiTest.java          # 通用API测试
│                           ├── MarketDataApiTest.java       # 市场数据API测试
│                           ├── AccountApiTest.java          # 账户API测试
│                           ├── OrderApiTest.java            # 订单API测试
│                           ├── UserDataStreamApiTest.java   # 用户数据流API测试
│                           └── WebSocketApiTest.java        # WebSocket API测试 (20个测试用例)
├── pom.xml
├── README.md
└── websocket.txt                                           # WebSocket API文档
```

## 技术栈

- **Java**: 11+
- **Maven**: 3.6+
- **JUnit 5**: 5.9.3
- **OkHttp**: 4.11.0 - REST API HTTP客户端
- **Java-WebSocket**: 1.5.3 - WebSocket客户端
- **Gson**: 2.10.1 - JSON序列化/反序列化
- **SLF4J**: 2.0.7 - 日志框架
- **Apache Commons Codec**: 1.15 - HMAC SHA256签名

## 快速开始

### 安装依赖

```bash
mvn clean install
```

### 配置

#### REST API 配置
在 `BaseTest.java` 中配置:

```java
protected static String BASE_URL = "https://api.example.com";
protected static String API_KEY = "your-api-key";
protected static String SECRET_KEY = "your-secret-key";
```

#### WebSocket API 配置
在 `WebSocketApiTest.java` 中配置:

```java
private static final String WS_URL = "wss://www.remifx-test.ai/openapi/quote/ws/v1";
```

## 运行测试

### 运行所有测试

```bash
mvn test
```

### 运行 REST API 测试

```bash
# 运行通用API测试
mvn test -Dtest=GeneralApiTest

# 运行市场数据API测试
mvn test -Dtest=MarketDataApiTest

# 运行账户API测试
mvn test -Dtest=AccountApiTest

# 运行订单API测试
mvn test -Dtest=OrderApiTest

# 运行用户数据流API测试
mvn test -Dtest=UserDataStreamApiTest
```

### 运行 WebSocket API 测试

```bash
# 运行所有 WebSocket 测试
mvn test -Dtest=WebSocketApiTest

# 运行特定 WebSocket 测试用例
mvn test -Dtest=WebSocketApiTest#testSubscribeTicker
mvn test -Dtest=WebSocketApiTest#testSubscribeKline1m
mvn test -Dtest=WebSocketApiTest#testSubscribeDepth
mvn test -Dtest=WebSocketApiTest#testHeartbeat
```

---

# WebSocket API 使用指南

## 功能特性

- **实时行情订阅（Ticker/Realtimes）** - 24小时完整ticker信息，逐秒刷新
- **逐笔交易订阅（Trade）** - 每笔成交推送
- **K线数据订阅（Kline）** - 支持多种时间周期（1m, 5m, 15m, 30m, 1h, 2h, 4h, 6h, 12h, 1d, 1w, 1M）
- **深度数据订阅（Depth）** - 订单簿快照（300档，每300ms更新）
- **增量深度订阅（DiffDepth）** - 订单簿变化推送
- **指数数据订阅（Index）** - 期权和期货指数数据
- **心跳机制（Ping/Pong）** - 保持连接活跃




# REST API 使用指南

## API接口覆盖

### 1. 通用API端点 (GeneralApiTest)
- ✅ `GET /openapi/v1/ping` - 测试连接
- ✅ `GET /openapi/v1/time` - 获取服务器时间
- ✅ `GET /openapi/v1/brokerInfo` - 获取Broker信息

### 2. 市场数据端点 (MarketDataApiTest)
- ✅ `GET /openapi/quote/v1/depth` - 获取订单簿
- ✅ `GET /openapi/quote/v1/trades` - 获取最近成交
- ✅ `GET /openapi/quote/v1/klines` - 获取K线数据
- ✅ `GET /openapi/quote/v1/ticker/24hr` - 获取24小时ticker
- ✅ `GET /openapi/quote/v1/ticker/price` - 获取价格
- ✅ `GET /openapi/quote/v1/ticker/bookTicker` - 获取最佳订单簿价格
- ✅ `GET /openapi/v1/pairs` - 获取现货币对

### 3. 账户端点 (AccountApiTest)
- ✅ `GET /openapi/v1/account` - 获取账户信息
- ✅ `GET /openapi/v1/depositOrders` - 获取存款记录
- ✅ `GET /openapi/v1/withdraw/detail` - 获取某个提币记录
- ✅ `GET /openapi/v1/withdrawalOrders` - 获取提币记录
- ✅ `POST /openapi/v1/balance_flow` - 查询流水
- ✅ `POST /openapi/v1/withdraw` - 提币

### 4. 订单端点 (OrderApiTest)
- ✅ `POST /openapi/v1/order/test` - 测试新订单
- ✅ `POST /openapi/v1/order` - 创建新订单
- ✅ `GET /openapi/v1/order` - 查询订单
- ✅ `DELETE /openapi/v1/order` - 取消订单
- ✅ `GET /openapi/v1/openOrders` - 获取当前订单
- ✅ `GET /openapi/v1/historyOrders` - 获取历史订单
- ✅ `GET /openapi/v1/myTrades` - 获取账户交易记录

### 5. 用户数据流端点 (UserDataStreamApiTest)
- ✅ `POST /openapi/v1/userDataStream` - 开始用户信息流
- ✅ `PUT /openapi/v1/userDataStream` - Keepalive用户信息流
- ✅ `DELETE /openapi/v1/userDataStream` - 关闭用户信息流

## REST API 示例代码
参考测试用例代码

## 签名机制

本项目实现了完整的HMAC SHA256签名机制，符合API文档要求:
- 自动添加 `timestamp` 参数
- 自动添加 `recvWindow` 参数
- 自动生成签名并添加到请求中
- 支持queryString和requestBody混合方式

## 注意事项

### 安全提示
以下测试默认被禁用，以防止意外操作:
- `testWithdraw()` - 提币测试
- `testCreateNewOrder()` - 创建订单测试
- `testCancelOrderByOrderId()` - 取消订单测试
- `testCancelOrderByClientOrderId()` - 取消订单测试

如需启用这些测试，请取消相关代码的注释，并确保在测试环境中运行。

### 测试环境要求
- 需要有效的API Key和Secret Key
- 建议在测试环境中运行测试
- 某些测试需要账户中有相应的数据才能完整验证

### 其他注意事项
1. **API密钥安全**: 不要将API Key和Secret Key提交到版本控制系统
2. **测试环境**: 建议在测试环境中运行测试，避免影响生产数据
3. **频率限制**: 注意API的频率限制，避免触发429错误
4. **时间同步**: 确保系统时间准确，避免签名验证失败
5. **WebSocket心跳**: 每30-60秒发送一次ping，避免连接被服务器断开

## 许可证

本项目仅用于学习和测试目的。

## 更新日志

### v1.0.1 (2025-12-04)
- 新增完整的 WebSocket API 支持
- 新增 20 个 WebSocket 测试用例
- 新增数据模型类（TickerData, TradeData, KlineData, DepthData, IndexData）
- 更新 README 文档，添加详细的 WebSocket API 使用指南

### v1.0.0
- 初始版本发布
- 完整的 REST API 支持
- REST API 测试用例


## WebSocket 测试用例

项目包含20个 WebSocket 测试用例（位于 `src/test/java/com/fx/api/test/WebSocketApiTest.java`）：

1. **testSubscribeTicker** - 订阅单个交易对ticker
2. **testSubscribeMultipleTickers** - 订阅多个交易对ticker
3. **testSubscribeTrade** - 订阅逐笔交易
4. **testSubscribeKline1m** - 订阅1分钟K线
5. **testSubscribeKlineDifferentIntervals** - 测试不同时间周期K线
6. **testSubscribeDepth** - 订阅深度数据
7. **testSubscribeDiffDepth** - 订阅增量深度
8. **testSubscribeIndex** - 订阅指数数据
9. **testUnsubscribe** - 取消特定topic订阅
10. **testUnsubscribeAll** - 取消所有订阅
11. **testHeartbeat** - 测试心跳机制
12. **testSubscribeWithLimit** - 测试limit参数
13. **testSubscribeWithBinary** - 测试二进制格式
14. **testMultipleSubscriptions** - 多个topic同时订阅
15. **testInvalidSymbol** - 无效交易对错误处理
16. **testInvalidTopic** - 无效topic错误处理
17. **testLongRunningConnection** - 长时间连接测试
18. **testReconnection** - 重连机制测试
19. **testHighFrequencyData** - 高频数据测试
20. **testCompleteWorkflow** - 完整工作流测试

## WebSocket 错误码

| 错误码 | 说明 |
|--------|------|
| -10000 | 无效的请求 |
| -10001 | 无效的JSON格式 |
| -10002 | 无效的event |
| -10003 | 缺少event参数 |
| -10004 | 无效的topic |
| -10005 | 缺少topic参数 |
| -10007 | 缺少params参数 |
| -10008 | 缺少period参数 |
| -10009 | 无效的period |
| -100010 | 无效的symbols |

---
