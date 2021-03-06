package com.example.iguest.hermes;

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
    private String requestID;
    private String deliverName;

    public Request(String userId, ParseGeoPoint deliveryLocation, String restaurantName, String description, String delieverName) {
        this.userId = userId;
        this.deliveryLocation = deliveryLocation;
        this.restaurantName = restaurantName;
        this.description = description;
        this.deliverName = delieverName;
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

    public String getRequestID() {return requestID;}

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
        return this.userId + " would like food from " + this.restaurantName;
    }

    public void setRequestID(String ID) {
        this.requestID = ID;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }
}
