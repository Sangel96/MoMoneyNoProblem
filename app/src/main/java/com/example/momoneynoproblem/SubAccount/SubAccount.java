package com.example.momoneynoproblem.SubAccount;

import com.example.momoneynoproblem.Singleton;

import java.io.Serializable;

public class SubAccount implements Serializable {
    public String subAccountName;
    public String reasonsSubAccountName;
    public String user_ID;
    public SubAccount() {

    }
    public SubAccount(String subAccountName, String reasonsSubAccountName) {
        this.subAccountName = subAccountName;
        this.reasonsSubAccountName = reasonsSubAccountName;
        this.user_ID = Singleton.getInstance().getUserID();
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
    }

    public void setReasonsSubAccountName(String reasonsSubAccountName) {
        this.reasonsSubAccountName = reasonsSubAccountName;
    }

    public String getSubAccountName() {
        return subAccountName;
    }

    public String getReasonsSubAccountName() {
        return reasonsSubAccountName;
    }
}
