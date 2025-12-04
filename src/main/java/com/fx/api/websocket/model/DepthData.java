package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Depth (Order Book) data model
 */
public class DepthData {

    @SerializedName("s")
    private String symbol;

    @SerializedName("t")
    private long timestamp;

    @SerializedName("v")
    private String version;

    @SerializedName("b")
    private List<List<String>> bids;

    @SerializedName("a")
    private List<List<String>> asks;

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public List<List<String>> getBids() { return bids; }
    public void setBids(List<List<String>> bids) { this.bids = bids; }

    public List<List<String>> getAsks() { return asks; }
    public void setAsks(List<List<String>> asks) { this.asks = asks; }

    @Override
    public String toString() {
        return "DepthData{" +
                "symbol='" + symbol + '\'' +
                ", timestamp=" + timestamp +
                ", version='" + version + '\'' +
                ", bids=" + bids +
                ", asks=" + asks +
                '}';
    }
}
