package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

/**
 * Kline (Candlestick) data model
 */
public class KlineData {

    @SerializedName("t")
    private long openTime;

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

    // Getters and Setters
    public long getOpenTime() { return openTime; }
    public void setOpenTime(long openTime) { this.openTime = openTime; }

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

    @Override
    public String toString() {
        return "KlineData{" +
                "openTime=" + openTime +
                ", symbol='" + symbol + '\'' +
                ", close='" + close + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", open='" + open + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
