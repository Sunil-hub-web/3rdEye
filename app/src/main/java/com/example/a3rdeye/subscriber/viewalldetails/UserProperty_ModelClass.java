package com.example.a3rdeye.subscriber.viewalldetails;

public class UserProperty_ModelClass {

    String userId,propertyId,userAddress,coordinetes;

    public UserProperty_ModelClass(String userId, String propertyId, String userAddress, String coordinetes) {
        this.userId = userId;
        this.propertyId = propertyId;
        this.userAddress = userAddress;
        this.coordinetes = coordinetes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getCoordinetes() {
        return coordinetes;
    }

    public void setCoordinetes(String coordinetes) {
        this.coordinetes = coordinetes;
    }
}
