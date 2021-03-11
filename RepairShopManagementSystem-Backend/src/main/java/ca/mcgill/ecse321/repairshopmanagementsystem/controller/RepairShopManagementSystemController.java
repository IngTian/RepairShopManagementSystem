package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/system")
public class RepairShopManagementSystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping(value = {"/create"})
    public RepairShopManagementSystemDto createSystem(@RequestParam String name, @RequestParam String phoneNo, @RequestParam String address) {
        RepairShopManagementSystem system = systemService.createSystem(name, phoneNo, address);
        return convertToDto(system);
    }

    @GetMapping(value = "GutenTag")
    public String sayHello() {
        return "Guten Tag! Wie gieten Sie?";
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress());
    }
}
