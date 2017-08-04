
package com.ongxeno.bitcoinratewidget.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BxMarketData implements Serializable
{

    @SerializedName("pairing_id")
    @Expose
    public Integer pairingId;
    @SerializedName("primary_currency")
    @Expose
    public String primaryCurrency;
    @SerializedName("secondary_currency")
    @Expose
    public String secondaryCurrency;
    @SerializedName("change")
    @Expose
    public Double change;
    @SerializedName("last_price")
    @Expose
    public Double lastPrice;
    @SerializedName("volume_24hours")
    @Expose
    public Double volume24hours;
    @SerializedName("orderbook")
    @Expose
    public Orderbook orderbook;
    private final static long serialVersionUID = -4281087476472208264L;

}
