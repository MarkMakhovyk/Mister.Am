
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dishes {

    @SerializedName("dishes")
    @Expose
    private List<Dish> dishes = null;
    @SerializedName("orderPackagePrice")
    @Expose
    private Integer orderPackagePrice;

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getOrderPackagePrice() {
        return orderPackagePrice;
    }

    public void setOrderPackagePrice(Integer orderPackagePrice) {
        this.orderPackagePrice = orderPackagePrice;
    }

}
