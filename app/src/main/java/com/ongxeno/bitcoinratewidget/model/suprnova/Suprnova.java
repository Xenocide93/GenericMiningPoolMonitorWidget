
package com.ongxeno.bitcoinratewidget.model.suprnova;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suprnova {

    @SerializedName("getuserstatus")
    @Expose
    private UserStatus userStatus;

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}
