package com.cronoscouriers.app.entity;

import com.cronoscouriers.app.enums.PackageStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Assignment {
    private String orderID;
    private String packageId;
    private String riderId;
    private PackageStatus packgeStatus;

}
