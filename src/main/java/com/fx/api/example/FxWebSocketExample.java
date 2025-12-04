package com.fx.api.example;

import com.fx.api.websocket.FxWebSocketClient;
import com.fx.api.websocket.WebSocketMessageHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * FX WebSocket API 完整使用示例
 *
 * 本示例演示如何：
 * 1. 连接到 WebSocket 服务器
 * 2. 订阅各种市场数据
 * 3. 处理接收到的消息
 * 4. 实现心跳保活机制
 * 5. 优雅地关闭连接
 */
public class FxWebSocketExample {

    private static final Logger logger = LoggerFactory.getLogger(FxWebSocketExample.class);
    private static final String WS_URL = "wss://fxapi.example.com/openapi/quote/ws/v1";

    private final Gson gson;
    private FxWebSocketClient client;
    private ScheduledExecutorService heartbeatScheduler;

    public FxWebSocketExample() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * 启动 WebSocket 客户端
     */
    public void start() throws Exception {
        logger.info("正在启动 FX WebSocket 客户端...");

        // 创建 WebSocket 客户端
        client = new FxWebSocketClient(
            new URI(WS_URL),
            new WebSocketMessageHandler() {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    handleOpen(handshakedata);
                }

                @Override
                public void onMessage(String message) {
                    handleMessage(message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    handleClose(code, reason, remote);
                }

                @Override
                public void onError(Exception ex) {
                    handleError(ex);
                }
            }
        );

        // 连接到服务器
        boolean connected = client.connectBlocking(10, TimeUnit.SECONDS);

        if (!connected) {
            throw new RuntimeException("无法连接到 WebSocket 服务器");
        }

        logger.info("WebSocket 客户端启动成功");
    }

    /**
     * 处理连接建立事件
     */
    private void handleOpen(ServerHandshake handshakedata) {
        logger.info("=== WebSocket 连接已建立 ===");
        logger.info("HTTP 状态码: {}", handshakedata.getHttpStatus());
        logger.info("HTTP 状态消息: {}", handshakedata.getHttpStatusMessage());

        try {
            // 等待连接稳定
            Thread.sleep(1000);

            // 订阅各种市场数据
            subscribeMarketData();

            // 启动心跳定时器
            startHeartbeat();

        } catch (Exception e) {
            logger.error("订阅失败", e);
        }
    }

    /**
     * 订阅市场数据
     */
    private void subscribeMarketData() {
        logger.info("=== 开始订阅市场数据 ===");

        // 1. 订阅实时行情（Ticker）
        logger.info("订阅 Ticker: BTCUSDT, ETHUSDT");
        client.subscribeTicker("BTCUSDT, ETHUSDT");

        // 等待一下
        sleep(500);

        // 2. 订阅逐笔交易
        logger.info("订阅 Trade: BTCUSDT");
        client.subscribeTrade("BTCUSDT");

        sleep(500);

        // 3. 订阅K线数据（1分钟）
        logger.info("订阅 Kline 1m: BTCUSDT");
        client.subscribeKline("BTCUSDT", "1m");

        sleep(500);

        // 4. 订阅K线数据（5分钟）
        logger.info("订阅 Kline 5m: ETHUSDT");
        client.subscribeKline("ETHUSDT", "5m");

        sleep(500);

        // 5. 订阅深度数据
        logger.info("订阅 Depth: BTCUSDT");
        client.subscribeDepth("BTCUSDT");

        sleep(500);

        // 6. 订阅增量深度
        logger.info("订阅 DiffDepth: BTCUSDT");
        client.subscribeDiffDepth("BTCUSDT");

        logger.info("=== 市场数据订阅完成 ===");
    }

    /**
     * 处理接收到的消息
     */
    private void handleMessage(String message) {
        try {
            JsonObject json = gson.fromJson(message, JsonObject.class);

            // 处理 pong 响应
            if (json.has("pong")) {
                long timestamp = json.get("pong").getAsLong();
                logger.debug("收到 Pong: {}", timestamp);
                return;
            }

            // 处理错误消息
            if (json.has("code")) {
                String code = json.get("code").getAsString();
                String msg = json.get("msg").getAsString();
                logger.error("收到错误消息 - 错误码: {}, 消息: {}", code, msg);
                return;
            }

            // 处理市场数据
            if (json.has("topic")) {
                String topic = json.get("topic").getAsString();
                String symbol = json.get("symbol").getAsString();
                boolean isFirst = json.get("f").getAsBoolean();

                switch (topic) {
                    case "realtimes":
                        handleTickerData(symbol, json, isFirst);
                        break;
                    case "trade":
                        handleTradeData(symbol, json, isFirst);
                        break;
                    case "kline":
                        handleKlineData(symbol, json, isFirst);
                        break;
                    case "depth":
                        handleDepthData(symbol, json, isFirst);
                        break;
                    case "diffDepth":
                        handleDiffDepthData(symbol, json, isFirst);
                        break;
                    case "index":
                        handleIndexData(symbol, json, isFirst);
                        break;
                    default:
                        logger.warn("未知的 topic: {}", topic);
                }
            } else {
                logger.debug("收到消息: {}", message);
            }

        } catch (Exception e) {
            logger.error("处理消息时出错: {}", message, e);
        }
    }

    /**
     * 处理 Ticker 数据
     */
    private void handleTickerData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 Ticker 数据: {} ===", symbol);
        }

        JsonObject data = json.getAsJsonArray("data").get(0).getAsJsonObject();

        logger.info("Ticker [{}] - 开: {}, 高: {}, 低: {}, 收: {}, 量: {}",
            symbol,
            data.get("o").getAsString(),
            data.get("h").getAsString(),
            data.get("l").getAsString(),
            data.get("c").getAsString(),
            data.get("v").getAsString()
        );
    }

    /**
     * 处理 Trade 数据
     */
    private void handleTradeData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 Trade 数据（最近60条）: {} ===", symbol);
        }

        json.getAsJsonArray("data").forEach(element -> {
            JsonObject trade = element.getAsJsonObject();
            String price = trade.get("p").getAsString();
            String quantity = trade.get("q").getAsString();
            boolean isBuy = !trade.get("m").getAsBoolean(); // m=false 表示买入

            logger.info("Trade [{}] - 价格: {}, 数量: {}, 方向: {}",
                symbol, price, quantity, isBuy ? "买" : "卖");
        });
    }

    /**
     * 处理 Kline 数据
     */
    private void handleKlineData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 Kline 数据: {} ===", symbol);
        }

        JsonObject params = json.getAsJsonObject("params");
        String interval = params.get("klineType").getAsString();

        JsonObject data = json.getAsJsonArray("data").get(0).getAsJsonObject();

        logger.info("Kline [{}] {} - 开: {}, 高: {}, 低: {}, 收: {}, 量: {}",
            symbol, interval,
            data.get("o").getAsString(),
            data.get("h").getAsString(),
            data.get("l").getAsString(),
            data.get("c").getAsString(),
            data.get("v").getAsString()
        );
    }

    /**
     * 处理 Depth 数据
     */
    private void handleDepthData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 Depth 数据: {} ===", symbol);
        }

        JsonObject data = json.getAsJsonArray("data").get(0).getAsJsonObject();

        int bidCount = data.getAsJsonArray("b").size();
        int askCount = data.getAsJsonArray("a").size();

        // 获取最佳买卖价
        String bestBid = data.getAsJsonArray("b").get(0).getAsJsonArray().get(0).getAsString();
        String bestAsk = data.getAsJsonArray("a").get(0).getAsJsonArray().get(0).getAsString();

        logger.info("Depth [{}] - 最佳买价: {}, 最佳卖价: {}, 买盘档数: {}, 卖盘档数: {}",
            symbol, bestBid, bestAsk, bidCount, askCount);
    }

    /**
     * 处理 DiffDepth 数据
     */
    private void handleDiffDepthData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 DiffDepth 数据: {} ===", symbol);
        }

        JsonObject data = json.getAsJsonArray("data").get(0).getAsJsonObject();

        int bidChanges = data.getAsJsonArray("b").size();
        int askChanges = data.getAsJsonArray("a").size();

        logger.debug("DiffDepth [{}] - 买盘变化: {} 档, 卖盘变化: {} 档",
            symbol, bidChanges, askChanges);
    }

    /**
     * 处理 Index 数据
     */
    private void handleIndexData(String symbol, JsonObject json, boolean isFirst) {
        if (isFirst) {
            logger.info("=== 收到首次 Index 数据: {} ===", symbol);
        }

        JsonObject data = json.getAsJsonArray("data").get(0).getAsJsonObject();

        String index = data.get("index").getAsString();
        String edp = data.get("edp").getAsString();
        String formula = data.get("formula").getAsString();

        logger.info("Index [{}] - 指数: {}, 预计交割价: {}, 来源: {}",
            symbol, index, edp, formula);
    }

    /**
     * 处理连接关闭事件
     */
    private void handleClose(int code, String reason, boolean remote) {
        logger.warn("=== WebSocket 连接已关闭 ===");
        logger.warn("关闭码: {}", code);
        logger.warn("关闭原因: {}", reason);
        logger.warn("是否为远程关闭: {}", remote);

        // 停止心跳
        stopHeartbeat();

        // 可以在这里实现重连逻辑
        if (remote && code != 1000) {
            logger.info("尝试重新连接...");
            tryReconnect();
        }
    }

    /**
     * 处理错误事件
     */
    private void handleError(Exception ex) {
        logger.error("=== WebSocket 发生错误 ===", ex);
    }

    /**
     * 启动心跳定时器
     */
    private void startHeartbeat() {
        logger.info("启动心跳定时器（每30秒）");

        heartbeatScheduler = Executors.newScheduledThreadPool(1);
        heartbeatScheduler.scheduleAtFixedRate(() -> {
            try {
                if (client != null && client.isOpen()) {
                    client.sendPing();
                    logger.debug("发送心跳 Ping");
                }
            } catch (Exception e) {
                logger.error("发送心跳失败", e);
            }
        }, 30, 30, TimeUnit.SECONDS);
    }

    /**
     * 停止心跳定时器
     */
    private void stopHeartbeat() {
        if (heartbeatScheduler != null && !heartbeatScheduler.isShutdown()) {
            logger.info("停止心跳定时器");
            heartbeatScheduler.shutdown();
            try {
                if (!heartbeatScheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    heartbeatScheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                heartbeatScheduler.shutdownNow();
            }
        }
    }

    /**
     * 尝试重新连接
     */
    private void tryReconnect() {
        try {
            logger.info("等待5秒后重连...");
            Thread.sleep(5000);

            if (client != null) {
                client.reconnect();
                logger.info("重连成功");
            }
        } catch (Exception e) {
            logger.error("重连失败", e);
        }
    }

    /**
     * 停止 WebSocket 客户端
     */
    public void stop() {
        logger.info("正在停止 FX WebSocket 客户端...");

        // 停止心跳
        stopHeartbeat();

        // 取消所有订阅
        if (client != null && client.isOpen()) {
            logger.info("取消所有订阅...");
            client.unsubscribeAll("BTCUSDT");
            client.unsubscribeAll("ETHUSDT");

            // 等待取消订阅完成
            sleep(1000);

            // 关闭连接
            client.close();
        }

        logger.info("WebSocket 客户端已停止");
    }

    /**
     * 辅助方法：休眠
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 主函数 - 运行示例
     */
    public static void main(String[] args) {
        FxWebSocketExample example = new FxWebSocketExample();

        try {
            // 启动客户端
            example.start();

            // 运行 5 分钟
            logger.info("=== 客户端将运行 5 分钟 ===");
            Thread.sleep(300000);

            // 停止客户端
            example.stop();

        } catch (Exception e) {
            logger.error("运行示例时出错", e);
        }

        logger.info("=== 示例程序结束 ===");
        System.exit(0);
    }
}
