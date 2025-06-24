package com.cronoscouriers.app.repo;

import com.cronoscouriers.app.entity.Package;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PackageRepo {
    private final Map<String,Package> pcks;

    PackageRepo(){
        pcks = new HashMap<>();
    }

    public Package putPackage(Package pck){
        pcks.put(pck.getPackageId(),pck);
        return pcks.get(pck.getPackageId());
    }

    public Package getPackage(String id){
        Package pack= pcks.get(id);
        if (pack==null){
            throw new RuntimeException("No Package with given ID");
        }
        return pack;
    }

    public Package updatePackage(String id){

        boolean bool=false;
        bool = pcks.containsKey(id);
        if (bool){
            throw new RuntimeException("No Package with given ID");
        }
        return pcks.get(id);
    }
}
