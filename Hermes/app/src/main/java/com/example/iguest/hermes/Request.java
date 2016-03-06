package com.example.iguest.hermes;

import android.location.Location;

import java.util.Date;

/**
 * Created by iguest on 3/6/16.
 */
public class Request {
    private User user;
    private Location deliveryLocation;
    private Location restaurantLocation;
    private String description;
    private String status;
    private Date createTime;


    public Request(User user, Location deliveryLocation, Location restaurantLocation, String description) {
        this.user = user;
        this.deliveryLocation = deliveryLocation;
        this.restaurantLocation = restaurantLocation;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Location getDeliveryLocation() {
        return deliveryLocation;
    }

    public Location getRestaurantLocation() {
        return restaurantLocation;
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

    public void setDeliveryLocation(Location deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
