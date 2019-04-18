
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceData {

    @SerializedName("summaryPriceData")
    @Expose
    private SummaryPriceData summaryPriceData;
    @SerializedName("companyPriceData")
    @Expose
    private CompanyPriceData companyPriceData;
    @SerializedName("userPriceData")
    @Expose
    private UserPriceData userPriceData;
    @SerializedName("onlinePriceData")
    @Expose
    private OnlinePriceData onlinePriceData;

    public SummaryPriceData getSummaryPriceData() {
        return summaryPriceData;
    }

    public void setSummaryPriceData(SummaryPriceData summaryPriceData) {
        this.summaryPriceData = summaryPriceData;
    }

    public CompanyPriceData getCompanyPriceData() {
        return companyPriceData;
    }

    public void setCompanyPriceData(CompanyPriceData companyPriceData) {
        this.companyPriceData = companyPriceData;
    }

    public UserPriceData getUserPriceData() {
        return userPriceData;
    }

    public void setUserPriceData(UserPriceData userPriceData) {
        this.userPriceData = userPriceData;
    }

    public OnlinePriceData getOnlinePriceData() {
        return onlinePriceData;
    }

    public void setOnlinePriceData(OnlinePriceData onlinePriceData) {
        this.onlinePriceData = onlinePriceData;
    }

}
