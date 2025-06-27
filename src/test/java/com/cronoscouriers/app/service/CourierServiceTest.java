package com.cronoscouriers.app.service;

import com.cronoscouriers.app.entity.Assignment;
import com.cronoscouriers.app.entity.Package;
import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.*;
import com.cronoscouriers.app.repo.AssignmentRepo;
import com.cronoscouriers.app.repo.PackageRepo;
import com.cronoscouriers.app.repo.RiderRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class CourierServiceTest {

    @Mock
    private RiderRepo riderRepo;
    @Mock
    private PackageRepo packageRepo;
    @Mock
    private AssignmentRepo assignmentRepo;

    @InjectMocks
    private CourierService service;

    List<Rider> availableRiders;
    List<Rider> availableExperiencedRiders;
    List<Rider> availableCloakedRiders;
    Package pack;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        availableRiders = new ArrayList<>();
        availableCloakedRiders = new ArrayList<>();
        availableExperiencedRiders = new ArrayList<>();

        Rider rider1 = new Rider();
        rider1.setRiderId(UUID.randomUUID().toString());
        rider1.setRiderType(RiderType.NORMAL);
        rider1.setRiderStatus(RiderStatus.AVAILABLE);
        rider1.setAssignedTime(System.currentTimeMillis());

        Rider rider2 = new Rider();
        rider2.setRiderId(UUID.randomUUID().toString());
        rider2.setRiderType(RiderType.EXPERIENCED);
        rider2.setRiderStatus(RiderStatus.AVAILABLE);
        rider2.setAssignedTime(System.currentTimeMillis());

        Rider rider3 = new Rider();
        rider3.setRiderId(UUID.randomUUID().toString());
        rider3.setRiderType(RiderType.CLOAKED);
        rider3.setRiderStatus(RiderStatus.AVAILABLE);
        rider3.setAssignedTime(System.currentTimeMillis());

        availableRiders.add(rider1);
        availableRiders.add(rider2);
        availableRiders.add(rider3);

        availableCloakedRiders.add(rider3);
        availableExperiencedRiders.add(rider2);


    }

    @Test
    void placeOrder_success_normal_package() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.NORMAL);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);


        when(riderRepo.getAvailableRiders()).thenReturn(availableRiders);
        when(riderRepo.getAvailableExperiencedRiders()).thenReturn(availableExperiencedRiders);
        when(riderRepo.getAvailableCloakedRiders()).thenReturn(availableCloakedRiders);
        when(packageRepo.putPackage(Mockito.any())).thenReturn(pack);

        Rider rider = service.placeOrder(pack);

        Assertions.assertEquals(RiderType.NORMAL, rider.getRiderType());
        Assertions.assertEquals(RiderStatus.ONDELIVERY, rider.getRiderStatus());

    }

    @Test
    void placeOrder_success_sensitive_package() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.SENSITIVE);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);

        when(riderRepo.getAvailableRiders()).thenReturn(availableRiders);
        when(riderRepo.getAvailableExperiencedRiders()).thenReturn(availableExperiencedRiders);
        when(riderRepo.getAvailableCloakedRiders()).thenReturn(availableCloakedRiders);
        when(packageRepo.putPackage(Mockito.any())).thenReturn(pack);

        Rider rider = service.placeOrder(pack);

        Assertions.assertEquals(RiderType.EXPERIENCED, rider.getRiderType());
        Assertions.assertEquals(RiderStatus.ONDELIVERY, rider.getRiderStatus());

    }

    @Test
    void placeOrder_success_cloaked_package() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.CLOAKED);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);

        when(riderRepo.getAvailableRiders()).thenReturn(availableRiders);
        when(riderRepo.getAvailableExperiencedRiders()).thenReturn(availableExperiencedRiders);
        when(riderRepo.getAvailableCloakedRiders()).thenReturn(availableCloakedRiders);
        when(packageRepo.putPackage(Mockito.any())).thenReturn(pack);

        Rider rider = service.placeOrder(pack);

        Assertions.assertEquals(RiderType.CLOAKED, rider.getRiderType());
        Assertions.assertEquals(RiderStatus.ONDELIVERY, rider.getRiderStatus());
    }

    @Test
    void placeOrder_when_riders_not_available_throw_exception() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.CLOAKED);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);

        when(riderRepo.getAvailableRiders()).thenReturn(Collections.emptyList());
        when(riderRepo.getAvailableExperiencedRiders()).thenReturn(Collections.emptyList());
        when(riderRepo.getAvailableCloakedRiders()).thenReturn(Collections.emptyList());
        when(packageRepo.putPackage(Mockito.any())).thenReturn(pack);

        assertThrows(RuntimeException.class, () -> service.placeOrder(pack));
    }

    @Test
    void placeOrder_when_package_is_null_throw_exception() {

        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(pack));
    }

    @Test
    void placeOrder_when_package_type_is_null_throw_exception() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);
        assertThrows(IllegalArgumentException.class, () -> service.placeOrder(pack));
    }

    @Test
    void getStatusOfPackage_validId_returnsStatus() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.CLOAKED);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);
        pack.setPackageStatus(PackageStatus.ASSIGNED);
        when(packageRepo.getPackage(anyString())).thenReturn(pack);

        String status = service.getStatusOfPackage(pack.getPackageId());

        assertEquals(PackageStatus.ASSIGNED.toString(), status);
    }

    @Test
    void getStatusOfPackage_nullId_throw_exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.getStatusOfPackage(null));
        assertEquals("Package ID must not be null or empty", exception.getMessage());
    }

    @Test
    void gGetStatusOfPackage_empty_id_throw_exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.getStatusOfPackage(" "));
        assertEquals("Package ID must not be null or empty", exception.getMessage());
    }

    @Test
    void getStatusOfPackage_package_not_found_throw_exception() {
        when(packageRepo.getPackage("404")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.getStatusOfPackage("404"));
        assertEquals("No package found for ID: 404", exception.getMessage());
    }

    @Test
    void testGetStatusOfPackage_status_is_null_throw_exception() {
        pack = new Package();
        pack.setDeliveryType(DeliveryType.EXPRESS);
        pack.setPackageType(PackageType.CLOAKED);
        pack.setPackageId(UUID.randomUUID().toString());
        pack.setDeliveredTime(172206);

        when(packageRepo.getPackage("789")).thenReturn(pack);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                service.getStatusOfPackage(pack.getPackageId()));
        assertEquals("Package status is not set for ID: 789", exception.getMessage());
    }

    @Test
    void testGetAssignments_returnsAssignmentList() {
        Assignment assignment1 = Assignment.builder()
                .orderID("1")
                .riderId("A")
                .packageId("X")
                .packgeStatus(PackageStatus.ASSIGNED)
                .build();
        Assignment assignment2 = Assignment.builder()
                .orderID("2")
                .riderId("B")
                .packageId("Y")
                .packgeStatus(PackageStatus.DELIVERED)
                .build();
        List<Assignment> expectedAssignments = List.of(assignment1, assignment2);

        when(assignmentRepo.getAssignments()).thenReturn(expectedAssignments);

        List<Assignment> result = service.getAssignments();

        assertEquals(expectedAssignments, result);


    }

}