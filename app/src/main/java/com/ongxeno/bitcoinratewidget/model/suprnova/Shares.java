
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shares {

    @SerializedName("valid")
    @Expose
    private Double valid;
    @SerializedName("invalid")
    @Expose
    private Double invalid;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("donate_percent")
    @Expose
    private Integer donatePercent;
    @SerializedName("is_anonymous")
    @Expose
    private Integer isAnonymous;
    @SerializedName("username")
    @Expose
    private String username;

    public Double getValid() {
        return valid;
    }

    public void setValid(Double valid) {
        this.valid = valid;
    }

    public Double getInvalid() {
        return invalid;
    }

    public void setInvalid(Double invalid) {
        this.invalid = invalid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDonatePercent() {
        return donatePercent;
    }

    public void setDonatePercent(Integer donatePercent) {
        this.donatePercent = donatePercent;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
