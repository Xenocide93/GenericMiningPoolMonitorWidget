
package com.ongxeno.bitcoinratewidget.model.dwarfpool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Worker {

    @SerializedName("alive")
    @Expose
    private Boolean alive;
    @SerializedName("hashrate")
    @Expose
    private Double hashrate;
    @SerializedName("hashrate_below_threshold")
    @Expose
    private Boolean hashrateBelowThreshold;
    @SerializedName("hashrate_calculated")
    @Expose
    private Double hashrateCalculated;
    @SerializedName("last_submit")
    @Expose
    private String lastSubmit;
    @SerializedName("second_since_submit")
    @Expose
    private Integer secondSinceSubmit;
    @SerializedName("worker")
    @Expose
    private String worker;

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Double getHashrate() {
        return hashrate;
    }

    public void setHashrate(Double hashrate) {
        this.hashrate = hashrate;
    }

    public Boolean getHashrateBelowThreshold() {
        return hashrateBelowThreshold;
    }

    public void setHashrateBelowThreshold(Boolean hashrateBelowThreshold) {
        this.hashrateBelowThreshold = hashrateBelowThreshold;
    }

    public Double getHashrateCalculated() {
        return hashrateCalculated;
    }

    public void setHashrateCalculated(Double hashrateCalculated) {
        this.hashrateCalculated = hashrateCalculated;
    }

    public String getLastSubmit() {
        return lastSubmit;
    }

    public void setLastSubmit(String lastSubmit) {
        this.lastSubmit = lastSubmit;
    }

    public Integer getSecondSinceSubmit() {
        return secondSinceSubmit;
    }

    public void setSecondSinceSubmit(Integer secondSinceSubmit) {
        this.secondSinceSubmit = secondSinceSubmit;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

}
