package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemService systemService;

    @GetMapping(value = "/owners")
    public List<OwnerDto> getAllOwners() {
        List<Owner> ownerList = accountService.getAllOwners();
        return convertToDto(ownerList);
    }

    @PostMapping(value = "/owners/create")
    public OwnerDto createOwner(@RequestParam String username, @RequestParam String password, @RequestParam String name, @RequestBody RepairShopManagementSystemDto system) {
        Owner owner = accountService.createOwner(username, password, name, systemService.getSystem(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress()));
        return convertToDto(owner);
    }

    private OwnerDto convertToDto(Owner owner) {
        return new OwnerDto(owner.getUsername(), owner.getName(), owner.getPassword(), convertToDto(owner.getRepairShopManagementSystem()));
    }

    private List<OwnerDto> convertToDto(List<Owner> ownerList) {
        List<OwnerDto> result = new ArrayList<>();
        for (Owner o : ownerList)
            result.add(new OwnerDto(o.getName(), o.getUsername(), o.getPassword(), convertToDto(o.getRepairShopManagementSystem())));
        return result;
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessPhoneNumber());
    }

}
