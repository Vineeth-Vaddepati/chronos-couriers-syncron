package com.cronoscouriers.app.repo;

import com.cronoscouriers.app.entity.Assignment;
import com.cronoscouriers.app.entity.Package;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AssignmentRepo {

    List<Assignment> assignments;

    public AssignmentRepo() {
        this.assignments = new ArrayList<>();
    }

    public List<Assignment> getAllAssignments(){
        return assignments;
    }

    public void addToOrder(Package pack){

        Assignment.builder()
                .orderID(UUID.randomUUID().toString())
                .packageId(pack.getPackageId())
                .riderId(pack.getRiderId())
                .packgeStatus(pack.getPackageStatus())
                .build();
    }



}
