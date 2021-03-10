package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AccountService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Transactional
    public Owner createOwner(String username, String password, String name, RepairShopManagementSystem system) {
        Owner newOwner = new Owner();
        newOwner.setName(name);
        newOwner.setUsername(username);
        newOwner.setPassword(password);
        newOwner.setRepairShopManagementSystem(system);
        newOwner.setSchedule(system.getSchedule());
        ownerRepository.save(newOwner);
        return newOwner;
    }

    @Transactional
    public Owner getOwner(String username) {
        return ownerRepository.findOwnerByUsername(username);
    }

    @Transactional
    public List<Owner> getAllOwners() {
        return toList(ownerRepository.findAll());
    }

    @Transactional
    public User updateUserInformation(User user, String username, String password, String name) {
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        if (user instanceof Owner)
            ownerRepository.save((Owner) user);
        else if (user instanceof Assistant)
            assistantRepository.save((Assistant) user);
        else
            customerRepository.save((Customer) user);

        return user;
    }

    @Transactional
    public Assistant createAssistant(String username, String password, String name, RepairShopManagementSystem system) {
        Assistant assistant = new Assistant();
        assistant.setUsername(username);
        assistant.setPassword(password);
        assistant.setName(name);
        assistant.setRepairShopManagementSystem(system);
        assistant.setSchedule(system.getSchedule());
        assistantRepository.save(assistant);
        return assistant;
    }

    @Transactional
    public Assistant getAssistant(String username) {
        return assistantRepository.findAssistantByUsername(username);
    }

    @Transactional
    public List<Assistant> getAllAssistants() {
        return toList(assistantRepository.findAll());
    }

    @Transactional
    public Customer createCustomer(String username, String password, String name, RepairShopManagementSystem system, String phoneNo, String address, String email) {
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setName(name);
        customer.setPhoneNo(phoneNo);
        customer.setEmail(email);
        customer.setHomeAddress(address);
        customer.setRepairShopManagementSystem(system);
        customer.setSchedule(system.getSchedule());
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Customer findCustomer(String username) {
        return customerRepository.findCustomerByUsername(username);
    }

    @Transactional
    public Customer addACarToCustomer(Customer customer, Car aCar) {
        Set<Car> cars = customer.getCar();
        cars.add(aCar);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> findAllCustomers() {
        return toList(customerRepository.findAll());
    }

    @Transactional
    public Customer updateCustomer(Customer customer, String email, String address, String phoneNo) {
        customer.setEmail(email);
        customer.setHomeAddress(address);
        customer.setPhoneNo(phoneNo);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public Car createCar(String plateNo, String model, String year, String manufacturer, Customer customer) {
        Car aCar = new Car();
        aCar.setCustomer(customer);
        aCar.setModel(model);
        aCar.setManufacturer(manufacturer);
        aCar.setPlateNo(plateNo);
        aCar.setYear(year);
        carRepository.save(aCar);
        return aCar;
    }

    @Transactional
    public Car getCar(String plateNo) {
        return carRepository.findCarByPlateNo(plateNo);
    }

    @Transactional
    public List<Car> getCars() {
        return toList(carRepository.findAll());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}
