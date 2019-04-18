
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmAction {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("popupData")
    @Expose
    private Object popupData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Object getPopupData() {
        return popupData;
    }

    public void setPopupData(Object popupData) {
        this.popupData = popupData;
    }

}
