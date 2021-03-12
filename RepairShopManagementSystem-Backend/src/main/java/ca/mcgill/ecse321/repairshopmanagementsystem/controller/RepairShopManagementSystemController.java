package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import net.sf.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/system")
public class RepairShopManagementSystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping(value = "create")
    public RepairShopManagementSystemDto createSystem(@RequestBody RepairShopManagementSystemDto system) {
        RepairShopManagementSystem s = systemService.createSystem(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress());
        return convertToDto(s);
    }

    @GetMapping(value = "most_recent")
    public RepairShopManagementSystemDto getMostRecentSystem() {
        return convertToDto(systemService.getMostRecentSystem());
    }

    @GetMapping(value = "GutenTag")
    public String sayHello() {
        return "Salve! Quid Agis?";
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress());
    }
}
