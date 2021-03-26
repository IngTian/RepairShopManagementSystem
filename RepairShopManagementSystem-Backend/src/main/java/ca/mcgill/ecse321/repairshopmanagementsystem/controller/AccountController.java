package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.AccountService;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemService systemService;

    /*
    ----------------------------------------------------------------------------
    ------------------------------------Owner-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "owners")
    public List<OwnerDto> getAllOwners() {
        List<Owner> ownerList = accountService.getAllOwners();
        return convertToDtoListForOwner(ownerList);
    }

    @PostMapping(value = "owners/create_to_most_recent_system")
    public OwnerDto createOwnerToMostRecentSystem(@RequestBody OwnerDto o) {
        Owner owner = accountService.createOwner(o.getUsername(), o.getPassword(), o.getName(), systemService.getMostRecentSystem());
        return convertToDto(owner);
    }

    @PostMapping(value = "owners/update_info")
    public OwnerDto updateOwnerInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestBody OwnerDto o) {
        Owner newOwner = (Owner) accountService.updateUserInformation(accountService.getOwner(o.getUsername()), newUsername, newPassword, newName);
        return convertToDto(newOwner);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Assistant-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "assistants")
    public List<AssistantDto> getAllAssistants() {
        List<Assistant> assistantList = accountService.getAllAssistants();
        return convertToDtoListForAssistant(assistantList);
    }
    @GetMapping(value = "assistants/get_By_AssistantName")
    public AssistantDto getOneAssistant(@RequestParam String userName) {
     Assistant assistant = accountService.getAssistant(userName);
        return convertToDto(assistant);
    }
    @PostMapping(value = "assistants/create_to_most_recent_system")
    public AssistantDto createAssistantToMostRecentSystem(@RequestBody AssistantDto a) {
        Assistant assistant = accountService.createAssistant(a.getUsername(), a.getPassword(), a.getName(), systemService.getMostRecentSystem());
        return convertToDto(assistant);
    }

    @PostMapping(value = "assistants/update_info")
    public AssistantDto updateAssistantInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestBody AssistantDto o) {
        Assistant newAssistant = (Assistant) accountService.updateUserInformation(accountService.getAssistant(o.getUsername()), newUsername, newPassword, newName);
        return convertToDto(newAssistant);
    }

    /*
    ----------------------------------------------------------------------------
    ---------------------------------Customer-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "customers")
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = accountService.getAllCustomers();
        return convertToDtoListForCustomer(customerList);
    }
    @GetMapping(value = "customers/get_by_username")
    public CustomerDto getOneCustomer(@RequestParam String username) {
      Customer customer = accountService.getCustomer(username);
        return convertToDto(customer);
    }

    @PostMapping(value = "customers/update_info")
    public CustomerDto updateCustomerInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestParam String newPhoneNo, @RequestParam String newAddress, @RequestParam String newEmail, @RequestBody CustomerDto o) {
        Customer newCustomer = (Customer) accountService.updateUserInformation(accountService.getCustomer(o.getUsername()), newUsername, newPassword, newName);
        accountService.updateCustomer(newCustomer, newEmail, newAddress, newPhoneNo);
        return convertToDto(newCustomer);
    }

    @PostMapping(value = "customers/create_to_most_recent_system")
    public CustomerDto createCustomerToMostRecentSystem(@RequestBody CustomerDto a) {
        Customer customer = accountService.createCustomer(a.getUsername(), a.getPassword(), a.getName(), systemService.getMostRecentSystem(), a.getPhoneNo(), a.getHomeAddress(), a.getEmail());
        return convertToDto(customer);
    }

    /*
    ----------------------------------------------------------------------------
    -----------------------------------Car--------------------------------------
    ----------------------------------------------------------------------------
     */

    @PostMapping(value = "cars/create")
    public CarDto createCarForACustomer(@RequestParam String username, @RequestBody CarDto car) {
        Car c = accountService.createCar(car.getPlateNo(), car.getModel(), car.getYear(), car.getManufacturer(), accountService.getCustomer(username));
        return convertToDto(c);
    }

    @PostMapping(value = "cars/update")
    public CarDto updateCarInfo(@RequestParam String newUsername, @RequestParam String newModel, @RequestParam String newYear, @RequestParam String newManufacturer, @RequestBody CarDto car) {
        Car c = accountService.updateCar(accountService.getCar(car.getPlateNo()), newModel, newYear, newManufacturer, accountService.getCustomer(newUsername));
        return convertToDto(c);
    }

    private OwnerDto convertToDto(Owner owner) {
        return new OwnerDto(owner.getUsername(), owner.getPassword(), owner.getName(), convertToDto(owner.getRepairShopManagementSystem()));
    }

    private AssistantDto convertToDto(Assistant a) {
        return new AssistantDto(a.getUsername(), a.getPassword(), a.getName(), convertToDto(a.getRepairShopManagementSystem()));
    }

    private CustomerDto convertToDto(Customer a) {
        return new CustomerDto(convertToDto(a.getRepairShopManagementSystem()), a.getUsername(), a.getPassword(), a.getName(), a.getPhoneNo(), a.getHomeAddress(), a.getEmail());
    }

    private CarDto convertToDto(Car c) {
        return new CarDto(c.getPlateNo(), c.getModel(), c.getManufacturer(), c.getYear());
    }

    private List<OwnerDto> convertToDtoListForOwner(List<Owner> ownerList) {
        List<OwnerDto> result = new ArrayList<>();
        for (Owner o : ownerList)
            result.add(convertToDto(o));
        return result;
    }

    private List<AssistantDto> convertToDtoListForAssistant(List<Assistant> user) {
        List<AssistantDto> result = new ArrayList<>();
        for (Assistant o : user)
            result.add(convertToDto(o));
        return result;
    }

    private List<CustomerDto> convertToDtoListForCustomer(List<Customer> user) {
        List<CustomerDto> result = new ArrayList<>();
        for (Customer o : user)
            result.add(convertToDto(o));
        return result;
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessPhoneNumber());
    }

}
