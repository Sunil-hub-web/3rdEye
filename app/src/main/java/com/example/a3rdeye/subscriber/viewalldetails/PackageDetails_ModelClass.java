package com.example.a3rdeye.subscriber.viewalldetails;

public class PackageDetails_ModelClass {

    String id,user_id,package_type,package_price,services,frequency,duration,value;

    public PackageDetails_ModelClass(String id, String user_id, String package_type, String package_price, String services, String frequency, String duration,String value) {
        this.id = id;
        this.user_id = user_id;
        this.package_type = package_type;
        this.package_price = package_price;
        this.services = services;
        this.frequency = frequency;
        this.duration = duration;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPackage_type() {
        return package_type;
    }

    public void setPackage_type(String package_type) {
        this.package_type = package_type;
    }

    public String getPackage_price() {
        return package_price;
    }

    public void setPackage_price(String package_price) {
        this.package_price = package_price;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", package_type='" + package_type + '\'' +
                ", package_price='" + package_price + '\'' +
                ", services='" + services + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration='" + duration + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
