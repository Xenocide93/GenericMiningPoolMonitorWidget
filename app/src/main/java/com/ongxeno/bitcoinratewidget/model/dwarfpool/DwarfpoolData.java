
package com.ongxeno.bitcoinratewidget.model.dwarfpool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class DwarfpoolData {

    @SerializedName("autopayout_from")
    @Expose
    private String autopayoutFrom;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("last_payment_amount")
    @Expose
    private Integer lastPaymentAmount;
    @SerializedName("last_payment_date")
    @Expose
    private String lastPaymentDate;
    @SerializedName("last_share_date")
    @Expose
    private String lastShareDate;
    @SerializedName("payout_daily")
    @Expose
    private Boolean payoutDaily;
    @SerializedName("payout_request")
    @Expose
    private Boolean payoutRequest;
    @SerializedName("total_hashrate")
    @Expose
    private Double totalHashrate;
    @SerializedName("total_hashrate_calculated")
    @Expose
    private Double totalHashrateCalculated;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("workers")
    @Expose
    private Map<String, Worker> workers;

    public String getAutopayoutFrom() {
        return autopayoutFrom;
    }

    public void setAutopayoutFrom(String autopayoutFrom) {
        this.autopayoutFrom = autopayoutFrom;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    public void setLastPaymentAmount(Integer lastPaymentAmount) {
        this.lastPaymentAmount = lastPaymentAmount;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public String getLastShareDate() {
        return lastShareDate;
    }

    public void setLastShareDate(String lastShareDate) {
        this.lastShareDate = lastShareDate;
    }

    public Boolean getPayoutDaily() {
        return payoutDaily;
    }

    public void setPayoutDaily(Boolean payoutDaily) {
        this.payoutDaily = payoutDaily;
    }

    public Boolean getPayoutRequest() {
        return payoutRequest;
    }

    public void setPayoutRequest(Boolean payoutRequest) {
        this.payoutRequest = payoutRequest;
    }

    public Double getTotalHashrate() {
        return totalHashrate;
    }

    public void setTotalHashrate(Double totalHashrate) {
        this.totalHashrate = totalHashrate;
    }

    public Double getTotalHashrateCalculated() {
        return totalHashrateCalculated;
    }

    public void setTotalHashrateCalculated(Double totalHashrateCalculated) {
        this.totalHashrateCalculated = totalHashrateCalculated;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Map<String, Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(Map<String, Worker> workers) {
        this.workers = workers;
    }

}
