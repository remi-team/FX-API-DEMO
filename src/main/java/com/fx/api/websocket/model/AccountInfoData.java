package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Account Info data model for WebSocket user data stream
 * Event type: outboundAccountInfo
 */
public class AccountInfoData {

    @SerializedName("e")
    private String eventType; // "outboundAccountInfo"

    @SerializedName("E")
    private long eventTime;

    @SerializedName("T")
    private boolean canTrade;

    @SerializedName("W")
    private boolean canWithdraw;

    @SerializedName("D")
    private boolean canDeposit;

    @SerializedName("B")
    private List<Balance> balances;

    /**
     * Balance information
     */
    public static class Balance {
        @SerializedName("a")
        private String asset;

        @SerializedName("f")
        private String free; // Available balance

        @SerializedName("l")
        private String locked; // Locked balance

        // Getters and Setters
        public String getAsset() { return asset; }
        public void setAsset(String asset) { this.asset = asset; }

        public String getFree() { return free; }
        public void setFree(String free) { this.free = free; }

        public String getLocked() { return locked; }
        public void setLocked(String locked) { this.locked = locked; }

        @Override
        public String toString() {
            return "Balance{" +
                    "asset='" + asset + '\'' +
                    ", free='" + free + '\'' +
                    ", locked='" + locked + '\'' +
                    '}';
        }
    }

    // Getters and Setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public long getEventTime() { return eventTime; }
    public void setEventTime(long eventTime) { this.eventTime = eventTime; }

    public boolean isCanTrade() { return canTrade; }
    public void setCanTrade(boolean canTrade) { this.canTrade = canTrade; }

    public boolean isCanWithdraw() { return canWithdraw; }
    public void setCanWithdraw(boolean canWithdraw) { this.canWithdraw = canWithdraw; }

    public boolean isCanDeposit() { return canDeposit; }
    public void setCanDeposit(boolean canDeposit) { this.canDeposit = canDeposit; }

    public List<Balance> getBalances() { return balances; }
    public void setBalances(List<Balance> balances) { this.balances = balances; }

    @Override
    public String toString() {
        return "AccountInfoData{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", canTrade=" + canTrade +
                ", canWithdraw=" + canWithdraw +
                ", canDeposit=" + canDeposit +
                ", balances=" + balances +
                '}';
    }
}
