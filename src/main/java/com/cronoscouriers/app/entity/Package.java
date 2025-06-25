package com.cronoscouriers.app.entity;


import com.cronoscouriers.app.enums.PackageStatus;
import com.cronoscouriers.app.enums.DeliveryType;
import com.cronoscouriers.app.enums.PackageType;
import lombok.Data;

@Data
public class Package implements Comparable<Package>{
    private String packageId;

    private DeliveryType deliveryType;
    private PackageType packageType;
    private long orderedTime;
    private PackageStatus packageStatus;
    private long deliveredTime;
    private long deadLine;
    private String riderId;

    @Override
    public int compareTo(Package o) {

        if((!this.deliveryType.equals(o.deliveryType))){
            if(this.deliveryType.equals(DeliveryType.EXPRESS)){
                return -1;
            }
            else{
                return 1;
            }

        }
        else{
            if(this.deadLine!= o.deadLine){
                if(this.deadLine<o.deadLine){
                   return -1;
                }
                else{
                    return 1;
                }
            }else{
                if(this.orderedTime!=o.orderedTime){
                    if(this.orderedTime<o.orderedTime){
                        return -1;
                    }
                    else{
                        return 1;
                    }
                }else {
                    return 0;
                }
            }
        }



    }
}
