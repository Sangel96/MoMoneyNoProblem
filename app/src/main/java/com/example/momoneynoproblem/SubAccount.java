package com.example.momoneynoproblem;

import java.io.Serializable;

public class SubAccount implements Serializable {
    public String subAccountName;
    public String reasonsSubAccountName;

    public SubAccount() {

    }
    public SubAccount(String subAccountName, String reasonsSubAccountName) {
        this.subAccountName = subAccountName;
        this.reasonsSubAccountName = reasonsSubAccountName;
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
