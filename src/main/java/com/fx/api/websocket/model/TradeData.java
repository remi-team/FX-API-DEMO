package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

/**
 * Trade data model
 */
public class TradeData {

    @SerializedName("v")
    private String tradeId;

    @SerializedName("t")
    private long timestamp;

    @SerializedName("p")
    private String price;

    @SerializedName("q")
    private String quantity;

    @SerializedName("m")
    private boolean isBuyerMaker;

    // Getters and Setters
    public String getTradeId() { return tradeId; }
    public void setTradeId(String tradeId) { this.tradeId = tradeId; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }

    public boolean isBuyerMaker() { return isBuyerMaker; }
    public void setBuyerMaker(boolean buyerMaker) { isBuyerMaker = buyerMaker; }

    @Override
    public String toString() {
        return "TradeData{" +
                "tradeId='" + tradeId + '\'' +
                ", timestamp=" + timestamp +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", isBuyerMaker=" + isBuyerMaker +
                '}';
    }
}
