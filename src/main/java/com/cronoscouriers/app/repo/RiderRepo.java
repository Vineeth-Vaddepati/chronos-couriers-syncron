package com.cronoscouriers.app.repo;

import com.cronoscouriers.app.entity.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderRepo {
    private final Map<String, Rider> riders;

    RiderRepo(){
        riders = new HashMap<>();
    }

    public Rider putPackage(Rider rider){
        riders.put(rider.getRiderId(),rider);
        return riders.get(rider.getPackageId());
    }

    public Rider getPackage(String id){
        Rider rider= riders.get(id);
        if (rider==null){
            throw new RuntimeException("No Rider with given ID");
        }
        return rider;
    }

    public Rider updatePackage(String id){

        boolean bool=false;
        bool = riders.containsKey(id);
        if (bool){
            throw new RuntimeException("No Rider with given ID");
        }
        return riders.get(id);
    }
}
