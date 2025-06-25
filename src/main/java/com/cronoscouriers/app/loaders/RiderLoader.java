package com.cronoscouriers.app.loaders;

import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.RiderStatus;
import com.cronoscouriers.app.enums.RiderType;
import com.cronoscouriers.app.repo.RiderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class RiderLoader implements CommandLineRunner {

    private final RiderRepo riderRepo;

    public RiderLoader(RiderRepo riderRepo) {
        this.riderRepo = riderRepo;
    }

    @Override
    public void run(String... args) {
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            Rider rider = new Rider();
            rider.setRiderId(UUID.randomUUID().toString());
            rider.setRiderType(i % 2 == 0 ? RiderType.NORMAL : RiderType.EXPERIENCED);
            rider.setRiderStatus(RiderStatus.AVAILABLE);
            rider.setAssignedTime(System.currentTimeMillis());

            riderRepo.putRider(rider); // Assuming this is your storage method
            System.out.println("Added Rider: " + rider.getRiderId());
        }
    }
}
