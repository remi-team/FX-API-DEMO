package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

/**
 * Execution Report data model for WebSocket user data stream
 * Event type: executionReport
 */
public class ExecutionReportData {

    @SerializedName("e")
    private String eventType; // "executionReport"

    @SerializedName("E")
    private long eventTime;

    @SerializedName("s")
    private String symbol;

    @SerializedName("c")
    private long clientOrderId;

    @SerializedName("S")
    private String side; // BUY, SELL

    @SerializedName("o")
    private String orderType; // LIMIT, MARKET, etc.

    @SerializedName("f")
    private String timeInForce; // GTC, IOC, FOK

    @SerializedName("q")
    private String orderQuantity;

    @SerializedName("p")
    private String orderPrice;

    @SerializedName("X")
    private String orderStatus; // NEW, PARTIALLY_FILLED, FILLED, CANCELED, REJECTED

    @SerializedName("i")
    private long orderId;

    @SerializedName("l")
    private String lastExecutedQuantity;

    @SerializedName("z")
    private String cumulativeFilledQuantity;

    @SerializedName("L")
    private String lastExecutedPrice;

    @SerializedName("n")
    private String commission;

    @SerializedName("N")
    private String commissionAsset;

    @SerializedName("u")
    private boolean isNormalTrade;

    @SerializedName("w")
    private boolean isOrderWorking;

    @SerializedName("m")
    private boolean isMakerSide;

    @SerializedName("O")
    private long orderCreationTime;

    @SerializedName("Z")
    private String cumulativeQuoteQuantity;

    // Getters and Setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public long getClientOrderId() { return clientOrderId; }
    public void setClientOrderId(long clientOrderId) { this.clientOrderId = clientOrderId; }

    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public String getTimeInForce() { return timeInForce; }
    public void setTimeInForce(String timeInForce) { this.timeInForce = timeInForce; }

    public String getOrderQuantity() { return orderQuantity; }
    public void setOrderQuantity(String orderQuantity) { this.orderQuantity = orderQuantity; }

    public String getOrderPrice() { return orderPrice; }
    public void setOrderPrice(String orderPrice) { this.orderPrice = orderPrice; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public String getLastExecutedQuantity() { return lastExecutedQuantity; }
    public void setLastExecutedQuantity(String lastExecutedQuantity) { this.lastExecutedQuantity = lastExecutedQuantity; }

    public String getCumulativeFilledQuantity() { return cumulativeFilledQuantity; }
    public void setCumulativeFilledQuantity(String cumulativeFilledQuantity) { this.cumulativeFilledQuantity = cumulativeFilledQuantity; }

    public String getLastExecutedPrice() { return lastExecutedPrice; }
    public void setLastExecutedPrice(String lastExecutedPrice) { this.lastExecutedPrice = lastExecutedPrice; }

    public String getCommission() { return commission; }
    public void setCommission(String commission) { this.commission = commission; }

    public String getCommissionAsset() { return commissionAsset; }
    public void setCommissionAsset(String commissionAsset) { this.commissionAsset = commissionAsset; }

    public boolean isNormalTrade() { return isNormalTrade; }
    public void setNormalTrade(boolean normalTrade) { isNormalTrade = normalTrade; }

    public boolean isOrderWorking() { return isOrderWorking; }
    public void setOrderWorking(boolean orderWorking) { isOrderWorking = orderWorking; }

    public boolean isMakerSide() { return isMakerSide; }
    public void setMakerSide(boolean makerSide) { isMakerSide = makerSide; }

    public long getOrderCreationTime() { return orderCreationTime; }
    public void setOrderCreationTime(long orderCreationTime) { this.orderCreationTime = orderCreationTime; }

    public String getCumulativeQuoteQuantity() { return cumulativeQuoteQuantity; }
    public void setCumulativeQuoteQuantity(String cumulativeQuoteQuantity) { this.cumulativeQuoteQuantity = cumulativeQuoteQuantity; }

    @Override
    public String toString() {
        return "ExecutionReportData{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", symbol='" + symbol + '\'' +
                ", clientOrderId=" + clientOrderId +
                ", side='" + side + '\'' +
                ", orderType='" + orderType + '\'' +
                ", timeInForce='" + timeInForce + '\'' +
                ", orderQuantity='" + orderQuantity + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderId=" + orderId +
                ", lastExecutedQuantity='" + lastExecutedQuantity + '\'' +
                ", cumulativeFilledQuantity='" + cumulativeFilledQuantity + '\'' +
                ", lastExecutedPrice='" + lastExecutedPrice + '\'' +
                ", commission='" + commission + '\'' +
                ", commissionAsset='" + commissionAsset + '\'' +
                ", isNormalTrade=" + isNormalTrade +
                ", isOrderWorking=" + isOrderWorking +
                ", isMakerSide=" + isMakerSide +
                ", orderCreationTime=" + orderCreationTime +
                ", cumulativeQuoteQuantity='" + cumulativeQuoteQuantity + '\'' +
                '}';
    }
}
