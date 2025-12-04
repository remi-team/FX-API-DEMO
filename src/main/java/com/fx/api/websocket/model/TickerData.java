package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Ticker (realtimes) data model
 */
public class TickerData {

    @SerializedName("t")
    private long timestamp;

    @SerializedName("s")
    private String symbol;

    @SerializedName("c")
    private String close;

    @SerializedName("h")
    private String high;

    @SerializedName("l")
    private String low;

    @SerializedName("o")
    private String open;

    @SerializedName("v")
    private String volume;

    @SerializedName("qv")
    private String quoteVolume;

    // Getters and Setters
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getClose() { return close; }
    public void setClose(String close) { this.close = close; }

    public String getHigh() { return high; }
    public void setHigh(String high) { this.high = high; }

    public String getLow() { return low; }
    public void setLow(String low) { this.low = low; }

    public String getOpen() { return open; }
    public void setOpen(String open) { this.open = open; }

    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }

    public String getQuoteVolume() { return quoteVolume; }
    public void setQuoteVolume(String quoteVolume) { this.quoteVolume = quoteVolume; }

    @Override
    public String toString() {
        return "TickerData{" +
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", close='" + close + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                ", volume='" + volume + '\'' +
                ", quoteVolume='" + quoteVolume + '\'' +
                '}';
    }
}
