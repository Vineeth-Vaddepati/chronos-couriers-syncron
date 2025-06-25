package com.cronoscouriers.app.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Assignment {
    private String orderID;
    private String packageId;
    private String riderId;

}
