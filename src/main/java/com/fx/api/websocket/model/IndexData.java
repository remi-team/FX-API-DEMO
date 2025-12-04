package com.fx.api.websocket.model;

import com.google.gson.annotations.SerializedName;

/**
 * Index data model for futures and options
 */
public class IndexData {

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("index")
    private String index;

    @SerializedName("edp")
    private String estimatedDeliveryPrice;

    @SerializedName("formula")
    private String formula;

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getIndex() { return index; }
    public void setIndex(String index) { this.index = index; }

    public String getEstimatedDeliveryPrice() { return estimatedDeliveryPrice; }
    public void setEstimatedDeliveryPrice(String estimatedDeliveryPrice) {
        this.estimatedDeliveryPrice = estimatedDeliveryPrice;
    }

    public String getFormula() { return formula; }
    public void setFormula(String formula) { this.formula = formula; }

    @Override
    public String toString() {
        return "IndexData{" +
                "symbol='" + symbol + '\'' +
                ", index='" + index + '\'' +
                ", estimatedDeliveryPrice='" + estimatedDeliveryPrice + '\'' +
                ", formula='" + formula + '\'' +
                '}';
    }
}
