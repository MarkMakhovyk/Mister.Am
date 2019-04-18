
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Details {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("statusData")
    @Expose
    private StatusData statusData;
    @SerializedName("receiveAddressPlace")
    @Expose
    private ReceiveAddressPlace receiveAddressPlace;
    @SerializedName("deliveryAddressPlace")
    @Expose
    private DeliveryAddressPlace deliveryAddressPlace;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("priceData")
    @Expose
    private PriceData priceData;
    @SerializedName("dishes")
    @Expose
    private Dishes dishes;
    @SerializedName("promotions")
    @Expose
    private List<Object> promotions = null;
    @SerializedName("callToClientPhoneCallAction")
    @Expose
    private Object callToClientPhoneCallAction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StatusData getStatusData() {
        return statusData;
    }

    public void setStatusData(StatusData statusData) {
        this.statusData = statusData;
    }

    public ReceiveAddressPlace getReceiveAddressPlace() {
        return receiveAddressPlace;
    }

    public void setReceiveAddressPlace(ReceiveAddressPlace receiveAddressPlace) {
        this.receiveAddressPlace = receiveAddressPlace;
    }

    public DeliveryAddressPlace getDeliveryAddressPlace() {
        return deliveryAddressPlace;
    }

    public void setDeliveryAddressPlace(DeliveryAddressPlace deliveryAddressPlace) {
        this.deliveryAddressPlace = deliveryAddressPlace;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PriceData getPriceData() {
        return priceData;
    }

    public void setPriceData(PriceData priceData) {
        this.priceData = priceData;
    }

    public Dishes getDishes() {
        return dishes;
    }

    public void setDishes(Dishes dishes) {
        this.dishes = dishes;
    }

    public List<Object> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Object> promotions) {
        this.promotions = promotions;
    }

    public Object getCallToClientPhoneCallAction() {
        return callToClientPhoneCallAction;
    }

    public void setCallToClientPhoneCallAction(Object callToClientPhoneCallAction) {
        this.callToClientPhoneCallAction = callToClientPhoneCallAction;
    }

}
