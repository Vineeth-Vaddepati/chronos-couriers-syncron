package com.cronoscouriers.app.entity;


import com.cronoscouriers.app.enums.PackageStatus;
import com.cronoscouriers.app.enums.PackageType;

public class Package {
    private String packageId;
    private Location location;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(long orderedTime) {
        this.orderedTime = orderedTime;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public PackageStatus getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(PackageStatus packageStatus) {
        this.packageStatus = packageStatus;
    }

    private PackageType packageType;
    private long orderedTime;
    private PackageStatus packageStatus;
}
