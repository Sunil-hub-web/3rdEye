package com.example.a3rdeye.subscriber.packagedetails;

public class Package_ModelClass {

    public String name;
    public String desc;
    public String price;
    public String id,packagesid;

    public Package_ModelClass(String id, String packagesid) {
        this.id = id;
        this.packagesid = packagesid;
    }

    public Package_ModelClass(String name, String desc, String price, String packagesid) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.packagesid = packagesid;
    }

    public Package_ModelClass(String name, String desc, String price) {
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Package_ModelClass() {
    }

    public Package_ModelClass(String id) {
        this.id = id;
    }

    public String getPackagesid() {
        return packagesid;
    }

    public void setPackagesid(String packagesid) {
        this.packagesid = packagesid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", packagesid='" + packagesid + '\'' +
                '}';
    }
}
