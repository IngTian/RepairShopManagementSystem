package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;

@ExtendWith(MockitoExtension.class)
public class TestAccountService {

    @Mock
    private OwnerRepository ownerDao;
    @Mock
    private AssistantRepository assistantDao;
    @Mock
    private CustomerRepository customerDao;
    @Mock
    private RepairShopManagementSystemRepository systemDao;
    @Mock
    private CarRepository carDao;

    @InjectMocks
    private AccountService accountService;

    private static final String TEST_USER_USERNAME = "TestUsername";
    private static final String TEST_USER_PASSWORD = "TestPassword";
    private static final String TEST_USER_NAME = "Someone";
    private static final Integer TEST_USER_ID = 1;

    private static final Integer TEST_SYSTEM_ID = 1;
    private static final String TEST_SYSTEM_NAME = "A test shop";
    private static final String TEST_SYSTEM_ADDRESS = "1234 Sunshine Street, Palm Beach, Florida";
    private static final String TEST_SYSTEM_PHONE_NO = "1002003000";

    private static final String TEST_CAR_PLATE_NO = "A123456";
    private static final String TEST_CAR_MODEL = "S650";
    private static final String TEST_CAR_MANUFACTURER = "Mercedes Benz";
    private static final String TEST_CAR_YEAR = "2021";


    @BeforeEach
    public void setMockOutput() {

        // Whenever tries to find owner, just return the default owner.
        lenient().when(ownerDao.findOwnerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_USER_USERNAME)) {
                Owner owner = new Owner();
                owner.setUsername(TEST_USER_USERNAME);
                owner.setPassword(TEST_USER_PASSWORD);
                owner.setName(TEST_USER_NAME);
                owner.setUserId(TEST_USER_ID);
                return owner;
            } else {
                return null;
            }
        });

        // Whenever tries to find assistant, just return the default.
        lenient().when(assistantDao.findAssistantByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_USER_USERNAME)) {
                Assistant assistant = new Assistant();
                assistant.setUsername(TEST_USER_USERNAME);
                assistant.setPassword(TEST_USER_PASSWORD);
                assistant.setName(TEST_USER_NAME);
                assistant.setUserId(TEST_USER_ID);
                return assistant;
            } else {
                return null;
            }
        });

        // Whenever tries to find customer, just return the default.
        lenient().when(customerDao.findCustomerByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_USER_USERNAME)) {
                Customer customer = new Customer();
                customer.setUsername(TEST_USER_USERNAME);
                customer.setPassword(TEST_USER_PASSWORD);
                customer.setName(TEST_USER_NAME);
                customer.setUserId(TEST_USER_ID);
                return customer;
            } else {
                return null;
            }
        });

        // Whenever tries to find customer, just return the default.
        lenient().when(systemDao.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SYSTEM_ID)) {
                RepairShopManagementSystem system = new RepairShopManagementSystem();
                system.setId(TEST_SYSTEM_ID);
                system.setBusinessName(TEST_SYSTEM_NAME);
                system.setBusinessAddress(TEST_SYSTEM_ADDRESS);
                system.setBusinessPhoneNumber(TEST_SYSTEM_PHONE_NO);
                return system;
            } else {
                return null;
            }
        });

        // Whenever tries to find customer, just return the default.
        lenient().when(carDao.findCarByPlateNo(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_CAR_PLATE_NO)) {
                Car car = new Car();
                car.setPlateNo(TEST_CAR_PLATE_NO);
                car.setModel(TEST_CAR_MODEL);
                car.setYear(TEST_CAR_YEAR);
                car.setManufacturer(TEST_CAR_MANUFACTURER);
                return car;
            } else {
                return null;
            }
        });

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(ownerDao.save(any(Owner.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(assistantDao.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(carDao.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
    }
}
