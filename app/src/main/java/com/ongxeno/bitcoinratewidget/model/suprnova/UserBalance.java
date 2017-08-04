
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBalance {

    @SerializedName("getuserbalance")
    @Expose
    private UserBalanceData userBalanceData;

    public UserBalanceData getUserBalanceData() {
        return userBalanceData;
    }

    public void setUserBalanceData(UserBalanceData userBalanceData) {
        this.userBalanceData = userBalanceData;
    }

}
