
package com.markmahovyk.misteram.model.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceiveAddressPlace {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("addressName")
    @Expose
    private String addressName;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("customCoordinates")
    @Expose
    private Boolean customCoordinates;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Boolean getCustomCoordinates() {
        return customCoordinates;
    }

    public void setCustomCoordinates(Boolean customCoordinates) {
        this.customCoordinates = customCoordinates;
    }

}
