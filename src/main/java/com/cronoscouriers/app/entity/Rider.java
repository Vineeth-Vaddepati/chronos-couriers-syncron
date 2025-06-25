package com.cronoscouriers.app.entity;


import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.enums.RiderType;
import lombok.Data;


@Data
public class Rider {
    private String riderId;
    private RiderType riderType;
    private Location location;
    private RiderStatus riderStatus;
    private int packageId;
    private long assignedTime;


}
