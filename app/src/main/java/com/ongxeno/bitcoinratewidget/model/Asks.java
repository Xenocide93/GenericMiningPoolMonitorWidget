
package com.ongxeno.bitcoinratewidget.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Asks implements Serializable
{

    @SerializedName("total")
    @Expose
    public Double total;
    @SerializedName("volume")
    @Expose
    public Double volume;
    @SerializedName("highbid")
    @Expose
    public Double highbid;
    private final static long serialVersionUID = -4470718240401427320L;

}
