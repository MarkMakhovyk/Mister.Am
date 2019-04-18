
package com.markmahovyk.misteram.model.task;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveTasks {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("action")
    @Expose
    private Action action;
    @SerializedName("callToClientPhoneCallAction")
    @Expose
    private Object callToClientPhoneCallAction;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Object getCallToClientPhoneCallAction() {
        return callToClientPhoneCallAction;
    }

    public void setCallToClientPhoneCallAction(Object callToClientPhoneCallAction) {
        this.callToClientPhoneCallAction = callToClientPhoneCallAction;
    }

}
