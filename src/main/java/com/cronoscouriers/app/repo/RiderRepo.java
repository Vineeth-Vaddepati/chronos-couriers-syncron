package com.cronoscouriers.app.repo;

import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.enums.RiderType;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RiderRepo {
    private final Map<String, Rider> ridersMap;
    private List<Rider>  riders;

    RiderRepo(){
        ridersMap = new HashMap<>();
        riders = new ArrayList <>();
    }

    public Rider putRider(Rider rider){
        ridersMap.put(rider.getRiderId(),rider);
        riders.add(rider);
        return ridersMap.get(rider.getPackageId());
    }

    public Rider getRider(String id){
        Rider rider= ridersMap.get(id);
        if (rider==null){
            throw new RuntimeException("No Rider with given ID");
        }
        return rider;
    }

    public Rider updateRiderStatus(Rider rider){

        boolean bool=false;
        bool = ridersMap.containsKey(rider.getRiderId());
        if (!bool){
            throw new RuntimeException("No Rider with given ID");
        }
        var rdr=ridersMap.get(rider.getRiderId());
        rdr.setRiderStatus(rider.getRiderStatus());
        return rdr;
    }

    public List<Rider> getAvailableRiders(){
        return ridersMap.values().stream()
                .filter(x->x.getRiderStatus().equals(RiderStatus.AVAILABLE))
                .toList();
    }
    public List<Rider> getAvailableExperiencedRiders(){
        return ridersMap.values().stream()
                .filter(x->x.getRiderStatus().equals(RiderStatus.AVAILABLE)&&x.getRiderType().equals(RiderType.EXPERIENCED))
                .toList();
    }

    public List<Rider> getAvailableCloakedRiders(){
        return ridersMap.values().stream()
                .filter(x->x.getRiderStatus().equals(RiderStatus.AVAILABLE)&&x.getRiderType().equals(RiderType.CLOAKED))
                .toList();
    }



    public List<Rider> getALl(){
        return (List<Rider>) ridersMap.values();
    }
}
