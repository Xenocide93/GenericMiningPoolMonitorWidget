
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserStatus {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("runtime")
    @Expose
    private Double runtime;
    @SerializedName("data")
    @Expose
    private ShareRateData shareRateData;

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

    public ShareRateData getShareRateData() {
        return shareRateData;
    }

    public void setShareRateData(ShareRateData shareRateData) {
        this.shareRateData = shareRateData;
    }

}
