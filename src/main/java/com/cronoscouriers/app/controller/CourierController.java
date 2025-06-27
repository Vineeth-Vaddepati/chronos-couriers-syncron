package com.cronoscouriers.app.controller;

import com.cronoscouriers.app.entity.Assignment;
import com.cronoscouriers.app.entity.Package;
import com.cronoscouriers.app.entity.Rider;
import com.cronoscouriers.app.service.CourierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courier")
public class CourierController {

    private CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping()
    public String placeOrder(@RequestBody Package pack){
        Rider rider = courierService.placeOrder(pack);
        return "Package Ordered and assigned to "+rider.getRiderType()+" rider :"+rider.getRiderId();
    }

    @GetMapping("status/{id}")
    public String getStatusOfPackage(@PathVariable("id") String id){
       return courierService.getStatusOfPackage(id);
    }
    @GetMapping("history")
    public List<Assignment> getAssignments(){
        return courierService.getAssignments();
    }



}
