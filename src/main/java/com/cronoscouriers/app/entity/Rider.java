package com.cronoscouriers.app.entity;


import com.cronoscouriers.app.enums.RiderStatus;

public class Rider {
    private String riderId;
    private Location location;

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public RiderStatus getRiderStatus() {
        return riderStatus;
    }

    public void setRiderStatus(RiderStatus riderStatus) {
        this.riderStatus = riderStatus;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public long getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(long assignedTime) {
        this.assignedTime = assignedTime;
    }

    private RiderStatus riderStatus;
    private int packageId;
    private long assignedTime;
}
