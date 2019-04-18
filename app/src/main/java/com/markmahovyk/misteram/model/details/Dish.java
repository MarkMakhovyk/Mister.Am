
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dish {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("option")
    @Expose
    private String option;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("standardPrice")
    @Expose
    private Object standardPrice;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("fullPackagePrice")
    @Expose
    private Integer fullPackagePrice;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Object standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFullPackagePrice() {
        return fullPackagePrice;
    }

    public void setFullPackagePrice(Integer fullPackagePrice) {
        this.fullPackagePrice = fullPackagePrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}
