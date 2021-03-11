package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
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
    public Owner createOwner(String username, String password, String name, RepairShopManagementSystem system) throws IllegalArgumentException {

        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

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
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("New username cannot be empty.");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("New password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("New name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

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
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");

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
        // Check for null or empty inputs.
        if (username == null || username.equals(""))
            throw new IllegalArgumentException("Username cannot be empty!");
        if (password == null || password.equals(""))
            throw new IllegalArgumentException("Password cannot be empty.");
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Name cannot be empty.");
        if (phoneNo == null || phoneNo.equals(""))
            throw new IllegalArgumentException("Phone NO cannot be empty.");
        if (address == null || address.equals(""))
            throw new IllegalArgumentException("Address cannot be empty.");
        if (email == null || email.equals(""))
            throw new IllegalArgumentException("Email cannot be empty.");

        // Check for formats.
        if (!Util.isUsernameCorrect(username))
            throw new IllegalArgumentException("Username must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPasswordCorrect(password))
            throw new IllegalArgumentException("Password must be at least 8 characters and at most 16 characters, " +
                    "containing only lowercase and uppercase English alphabets and numbers.");
        if (!Util.isPhoneNoCorrect(phoneNo))
            throw new IllegalArgumentException("Phone NO must be a 10-digit number.");
        if (!Util.isEmailAddressCorrect(email))
            throw new IllegalArgumentException("Email must be of the form part_1@part_2.part_3.");

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
        String plateNo = aCar.getPlateNo(), year = aCar.getYear(), model = aCar.getModel(), manufacturer = aCar.getManufacturer();

        Set<Car> cars = customer.getCar();
        cars.add(aCar);
        customerRepository.save(customer);
        return customer;
    }

    @Transactional
    public List<Customer> getAllCustomers() {
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
