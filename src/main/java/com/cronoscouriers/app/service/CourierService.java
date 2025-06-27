package com.cronoscouriers.app.service;

import com.cronoscouriers.app.entity.Assignment;
import com.cronoscouriers.app.entity.Package;
import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.PackageStatus;
import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.repo.AssignmentRepo;
import com.cronoscouriers.app.repo.PackageRepo;
import com.cronoscouriers.app.repo.RiderRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;

@Service
public class CourierService {

    private RiderRepo riderRepo;
    private PackageRepo packageRepo;
    private AssignmentRepo assignmentRepo;

    public CourierService(RiderRepo riderRepo, PackageRepo packageRepo, AssignmentRepo assignmentRepo) {
        this.riderRepo = riderRepo;
        this.packageRepo = packageRepo;
        this.assignmentRepo = assignmentRepo;
    }

    @Scheduled(fixedRate = 5000)
    private void updateRepos(){
        Random random = new Random();
        int randomNumber = random.nextInt(100);
        Queue<Package> queue = packageRepo.getPcksQueue();
        if(queue==null||queue.isEmpty()){
            System.out.println("No Packages to deliver!!!");
            return;
        }
        if(randomNumber%2==0){
           Package pack =queue.peek();
           Package pck=packageRepo.getPackage(pack.getPackageId());
           assignmentRepo.addToOrder(pck);
           System.out.println("Package: "+pack.getPackageId()+" Status: "+pack.getPackageStatus());
        }
        else{
            Package pack =queue.peek();
            Package pck=packageRepo.getPackage(pack.getPackageId());
            pck.setPackageStatus(PackageStatus.DELIVERED);
            pck.setDeliveredTime(Instant.now().toEpochMilli());
            packageRepo.updatePackage(pck);
            String riderId = pck.getRiderId();
            Rider rider = riderRepo.getRider(riderId);
            rider.setRiderStatus(RiderStatus.AVAILABLE);
            rider.setPackageId("");
            riderRepo.updateRiderStatus(rider);
            Package pk=packageRepo.removePackage(pck);
            assignmentRepo.addToOrder(pk);
            System.out.println("Package: "+pack.getPackageId()+" Status: "+pack.getPackageStatus());
        }

    }

    public Rider placeOrder(Package pk){
        if (pk == null) {
            throw new IllegalArgumentException("Package cannot be nul");
        }
        if (pk.getPackageType() == null) {
            throw new IllegalArgumentException("Pakage type must be specified");
        }

        System.out.println("Started Order service");
        pk.setPackageId(UUID.randomUUID().toString());
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
        Rider rider = riders.get(0);
        rider.setRiderStatus(RiderStatus.ONDELIVERY);
        System.out.println("Rider Assigned "+rider.getRiderId());

        pk.setRiderId(rider.getRiderId());
        pk.setPackageStatus(PackageStatus.ASSIGNED);
        packageRepo.putPackage(pk);
        return rider;

    }

    public String getStatusOfPackage(String id){
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Package ID must not be null or empty");
        }

        Package pkg = packageRepo.getPackage(id);
        if (pkg == null) {
            throw new IllegalArgumentException("No package found for ID: " + id);
        }

        PackageStatus status = pkg.getPackageStatus();
        if (status == null) {
            throw new IllegalStateException("Package status is not set for ID: " + id);
        }

        return status.toString();
    }

    public List<Assignment> getAssignments(){
        return assignmentRepo.getAssignments();
    }


}
