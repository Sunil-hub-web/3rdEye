package com.example.a3rdeye.subscriber.viewalldetails;

public class Property_ModelClass {

    String user_id;
    String property_Id;
    String latitude;
    String longitude;
    String countary_Nmae;
    String locality;
    String postal_Code;
    String address;

    public Property_ModelClass(String user_id, String property_Id, String latitude, String longitude, String countary_Nmae,
                               String locality, String postal_Code, String address) {
        this.user_id = user_id;
        this.property_Id = property_Id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.countary_Nmae = countary_Nmae;
        this.locality = locality;
        this.postal_Code = postal_Code;
        this.address = address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProperty_Id() {
        return property_Id;
    }

    public void setProperty_Id(String property_Id) {
        this.property_Id = property_Id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountary_Nmae() {
        return countary_Nmae;
    }

    public void setCountary_Nmae(String countary_Nmae) {
        this.countary_Nmae = countary_Nmae;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostal_Code() {
        return postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        this.postal_Code = postal_Code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
