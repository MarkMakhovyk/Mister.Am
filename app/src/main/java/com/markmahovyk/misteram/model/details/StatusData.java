
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusData {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("confirmAction")
    @Expose
    private ConfirmAction confirmAction;
    @SerializedName("declineAction")
    @Expose
    private Object declineAction;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ConfirmAction getConfirmAction() {
        return confirmAction;
    }

    public void setConfirmAction(ConfirmAction confirmAction) {
        this.confirmAction = confirmAction;
    }

    public Object getDeclineAction() {
        return declineAction;
    }

    public void setDeclineAction(Object declineAction) {
        this.declineAction = declineAction;
    }

}
