
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBalanceData {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("runtime")
    @Expose
    private Double runtime;
    @SerializedName("data")
    @Expose
    private BalanceData balanceData;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getRuntime() {
        return runtime;
    }

    public void setRuntime(Double runtime) {
        this.runtime = runtime;
    }

    public BalanceData getBalanceData() {
        return balanceData;
    }

    public void setBalanceData(BalanceData balanceData) {
        this.balanceData = balanceData;
    }

}
