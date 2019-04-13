
package com.markmahovyk.misteram.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveTasks {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("route")
    @Expose
    private Route route;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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
