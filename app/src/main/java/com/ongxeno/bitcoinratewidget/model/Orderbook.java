
package com.ongxeno.bitcoinratewidget.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderbook implements Serializable
{

    @SerializedName("bids")
    @Expose
    public Bids bids;
    @SerializedName("asks")
    @Expose
    public Asks asks;
    private final static long serialVersionUID = 2839399049355281383L;

}
