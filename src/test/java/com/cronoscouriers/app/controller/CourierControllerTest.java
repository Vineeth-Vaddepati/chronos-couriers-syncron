package com.cronoscouriers.app.controller;
import com.cronoscouriers.app.entity.Assignment;
import com.cronoscouriers.app.entity.Package;
import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.enums.PackageStatus;
import com.cronoscouriers.app.enums.PackageType;
import com.cronoscouriers.app.enums.RiderType;
import com.cronoscouriers.app.service.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourierController.class)
@ExtendWith(SpringExtension.class)
class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourierService courierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void placeOrder_returns_successM_mssage() throws Exception {
        Package pack = new Package();
        pack.setPackageType(PackageType.NORMAL);

        Rider rider = new Rider();
        rider.setRiderId("John");
        rider.setRiderType(RiderType.NORMAL);

        when(courierService.placeOrder(any(Package.class))).thenReturn(rider);

        mockMvc.perform(post("/courier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pack)))
                .andExpect(status().isOk())
                .andExpect(content().string("Package Ordered and assigned to NORMAL rider :John"));
    }

    @Test
    void getStatus_of_package_returns_status() throws Exception {
        when(courierService.getStatusOfPackage("abc123")).thenReturn("DELIVERED");

        mockMvc.perform(get("/courier/status/abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string("DELIVERED"));
    }

    @Test
    void test_get_history_returns_status() throws Exception {

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
        when(courierService.getAssignments()).thenReturn(expectedAssignments);
        mockMvc.perform(get("/courier/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));

    }
}
