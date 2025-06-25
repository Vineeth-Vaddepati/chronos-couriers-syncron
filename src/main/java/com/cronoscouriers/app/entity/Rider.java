package com.cronoscouriers.app.entity;


import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.enums.RiderType;
import lombok.Data;


@Data
public class Rider {
    private String riderId;
    private RiderType riderType;
    private RiderStatus riderStatus;
    private String packageId;
    private long assignedTime;


}
