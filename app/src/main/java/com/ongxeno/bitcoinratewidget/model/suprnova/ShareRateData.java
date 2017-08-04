
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShareRateData {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("shares")
    @Expose
    private Shares shares;
    @SerializedName("hashrate")
    @Expose
    private Double hashrate;
    @SerializedName("sharerate")
    @Expose
    private Double sharerate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Shares getShares() {
        return shares;
    }

    public void setShares(Shares shares) {
        this.shares = shares;
    }

    public Double getHashrate() {
        return hashrate;
    }

    public void setHashrate(Double hashrate) {
        this.hashrate = hashrate;
    }

    public Double getSharerate() {
        return sharerate;
    }

    public void setSharerate(Double sharerate) {
        this.sharerate = sharerate;
    }

}
