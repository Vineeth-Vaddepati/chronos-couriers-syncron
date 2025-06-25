package com.cronoscouriers.app.service;

import com.cronoscouriers.app.entity.Package;
import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.PackageStatus;
import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.repo.AssignmentRepo;
import com.cronoscouriers.app.repo.PackageRepo;
import com.cronoscouriers.app.repo.RiderRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DispatchService {

    private RiderRepo riderRepo;
    private PackageRepo packageRepo;
    private AssignmentRepo assignmentRepo;

    private void updateRepos(){

    }

    public DispatchService(RiderRepo riderRepo, PackageRepo packageRepo, AssignmentRepo assignmentRepo) {
        this.riderRepo = riderRepo;
        this.packageRepo = packageRepo;
        this.assignmentRepo = assignmentRepo;
    }

    public void placeBulkOrder(List<Package> pkgs){
        List<Rider> riders = riderRepo.getAvailableRiders();

        if(riders.isEmpty()){
            throw new RuntimeException("No riders available. Please, try after sometime!!!");
        }

        for(int i=0;i<pkgs.size();i++)
        {

            if(i>riders.size()){
                throw new RuntimeException("No riders available. Please, try after sometime!!!");
            }
            Rider rider = riders.get(i);
            rider.setRiderStatus(RiderStatus.ONDELIVERY);

            Package pk = pkgs.get(i);
            pk.setRiderId(rider.getRiderId());
            pk.setOrderedTime(Instant.now().toEpochMilli());
            pk.setPackageStatus(PackageStatus.ASSIGNED);

            packageRepo.putPackage(pk);

        }

    }
    public void placeOrder(Package pk){
        pk.setOrderedTime(Instant.now().toEpochMilli());

        List<Rider> riders =
        switch(pk.getPackageType()){
            case NORMAL->riderRepo.getAvailableRiders();
            case SENSITIVE -> riderRepo.getAvailableExperiencedRiders();
            case CLOAKED -> riderRepo.getAvailableCloakedRiders();
        };

        if(riders.isEmpty()){
            throw new RuntimeException("No riders available. Please, try after sometime!!!");
        }
        Rider rider = riders.getFirst();
        rider.setRiderStatus(RiderStatus.ONDELIVERY);


        pk.setRiderId(rider.getRiderId());
        pk.setPackageStatus(PackageStatus.ASSIGNED);
        packageRepo.putPackage(pk);

    }

    public String getStatusOfPackage(String id){
        return packageRepo.getPackage(id).getPackageStatus().toString();
    }


}
