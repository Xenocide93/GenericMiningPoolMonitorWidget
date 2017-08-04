
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceData {

    @SerializedName("confirmed")
    @Expose
    private Double confirmed;
    @SerializedName("unconfirmed")
    @Expose
    private Double unconfirmed;
    @SerializedName("orphaned")
    @Expose
    private Double orphaned;

    public Double getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Double confirmed) {
        this.confirmed = confirmed;
    }

    public Double getUnconfirmed() {
        return unconfirmed;
    }

    public void setUnconfirmed(Double unconfirmed) {
        this.unconfirmed = unconfirmed;
    }

    public Double getOrphaned() {
        return orphaned;
    }

    public void setOrphaned(Double orphaned) {
        this.orphaned = orphaned;
    }

}
