package com.cronoscouriers.app.repo;

import com.cronoscouriers.app.entity.Package;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PackageRepo {
    private final Map<String,Package> pcksMap;

    private Queue<Package> pcks;

    PackageRepo(){
        pcksMap = new HashMap<>();
        pcks = new PriorityQueue<>();
    }

    public Package putPackage(Package pck){
        pcksMap.put(pck.getPackageId(),pck);
        pcks.add(pck);
        return pcksMap.get(pck.getPackageId());
    }

    public Package getPackage(String id){
        Package pack= pcksMap.get(id);
        if (pack==null){
            throw new RuntimeException("No Package with given ID");
        }
        return pack;
    }

    public Package updatePackage(String id){

        boolean bool=false;
        bool = pcksMap.containsKey(id);
        if (bool){
            throw new RuntimeException("No Package with given ID");
        }
        return pcksMap.get(id);
    }

    public Queue<Package> getPcksQueue(){
        return pcks;
    }

    public void addBulkPackages(List<Package> packages){
        pcks.addAll(packages);
        for (Package pack:packages){
            pcksMap.put(pack.getPackageId(), pack);
        }
    }

}
