package com.example.iguest.hermes;

import android.location.Location;

import com.parse.ParseGeoPoint;

import java.util.Date;

/**
 * Created by iguest on 3/6/16.
 */
public class Request {
    private String userId;
    private ParseGeoPoint deliveryLocation;
    private String restaurantId;

    private String restaurantName;
    private String description;
    private String status;
    private Date createTime;

    public Request(String userId, ParseGeoPoint deliveryLocation, String restaurantName, String description) {
        this.userId = userId;
        this.deliveryLocation = deliveryLocation;
        this.restaurantName = restaurantName;
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ParseGeoPoint getDeliveryLocation() {
        return deliveryLocation;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setDeliveryLocation(ParseGeoPoint deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setRestaurantName(String name) {
        this.restaurantName = name;
    }

    public String toString() {
        return this.userId + " " + this.description + " from " + this.restaurantName;
    }

}
