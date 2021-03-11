package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @InjectMocks
    private SystemService systemService;

    private static final String TEST_USER_USERNAME = "TestUsername";
    private static final String TEST_USER_PASSWORD = "TestPassword";
    private static final String TEST_USER_NAME = "Someone";
    private static final Integer TEST_USER_ID = 1;
    private static final String TEST_CUSTOMER_PHONE_NO = "1002003000";
    private static final String TEST_CUSTOMER_ADDRESS = "1234 Sunshine Street, Palm Beach, Florida";
    private static final String TEST_CUSTOMER_EMAIL = "someone@123.com";

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

        // Whenever tries to find system, just return the default.
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

        // Whenever tries to find system, just return the default.
        lenient().when(systemDao.findRepairShopManagementSystemByBusinessNameAndBusinessAddressAndBusinessPhoneNumber(anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SYSTEM_NAME) && invocation.getArgument(1).equals(TEST_SYSTEM_ADDRESS) && invocation.getArgument(2).equals(TEST_SYSTEM_PHONE_NO)) {
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

        // Whenever tries to find car, just return the default.
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

    /*
    ----------------------------------------------------------------------------
    --------------------------------Test: Owner---------------------------------
    ----------------------------------------------------------------------------
     */

    @Test
    public void testCreateOwner() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(owner);
        assertEquals(TEST_USER_NAME, owner.getName());
        assertEquals(TEST_USER_PASSWORD, owner.getPassword());
        assertEquals(TEST_USER_USERNAME, owner.getUsername());
    }

    @Test
    public void testCreateOwnerWithEmptyUsername() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";
        try {
            owner = accountService.createOwner("", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testCreateOwnerWithTooShortUsername() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";
        try {
            owner = accountService.createOwner("abcd1", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithTooLongUsername() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";
        try {
            owner = accountService.createOwner("abcd14567abcd14567", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithUsernameContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";
        try {
            owner = accountService.createOwner("abcde!!@gf", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithEmptyPassword() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";

        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, "", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Password cannot be empty.", error);
    }

    @Test
    public void testCreateOwnerWithTooShortPassword() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";

        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, "abcd", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithTooLongPassword() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";

        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, "abcdefghijklmnopqrstuvwxyz", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithPasswordContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";

        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, "abcd@!!hijk", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateOwnerWithEmptyName() {
        assertEquals(0, accountService.getAllOwners().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Owner owner = null;
        String error = "";

        try {
            owner = accountService.createOwner(TEST_USER_USERNAME, TEST_USER_PASSWORD, "", defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(owner);
        assertEquals("Name cannot be empty.", error);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------Test: Update User Info----------------------------
    ----------------------------------------------------------------------------
     */

    @Test
    public void testUpdateUserInfo() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "");
        assertEquals(TEST_USER_USERNAME, aUser.getUsername());
        assertEquals(TEST_USER_NAME, aUser.getName());
        assertEquals(TEST_USER_PASSWORD, aUser.getPassword());
    }

    @Test
    public void testUpdateUserInfoWithEmptyUsername() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, "", TEST_USER_PASSWORD, TEST_USER_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "New username cannot be empty.");
        assertEquals("hellohello", aUser.getUsername());
        assertEquals("aName", aUser.getName());
        assertEquals("hellohello", aUser.getPassword());
    }

    @Test
    public void testUpdateUserInfoWithEmptyPassword() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, TEST_USER_USERNAME, "", TEST_USER_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "New password cannot be empty.");
        assertEquals("hellohello", aUser.getUsername());
        assertEquals("aName", aUser.getName());
        assertEquals("hellohello", aUser.getPassword());
    }

    @Test
    public void testUpdateUserInfoWithEmptyName() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, TEST_USER_USERNAME, TEST_USER_PASSWORD, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "New name cannot be empty.");
        assertEquals("hellohello", aUser.getUsername());
        assertEquals("aName", aUser.getName());
        assertEquals("hellohello", aUser.getPassword());
    }

    @Test
    public void testUpdateUserInfoWithIllegalUsername() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, "abcde!!@gf", TEST_USER_PASSWORD, TEST_USER_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.");
        assertEquals("hellohello", aUser.getUsername());
        assertEquals("aName", aUser.getName());
        assertEquals("hellohello", aUser.getPassword());
    }

    @Test
    public void testUpdateUserInfoWithIllegalPassword() {
        Owner aUser = new Owner();
        aUser.setName("aName");
        aUser.setUserId(1);
        aUser.setPassword("hellohello");
        aUser.setUsername("hellohello");

        String error = "";

        try {
            accountService.updateUserInformation(aUser, TEST_USER_USERNAME, "abcd@!!hijk", TEST_USER_NAME);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNotNull(aUser);
        assertEquals(error, "Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.");
        assertEquals("hellohello", aUser.getUsername());
        assertEquals("aName", aUser.getName());
        assertEquals("hellohello", aUser.getPassword());
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------Test: Create Assistant----------------------------
    ----------------------------------------------------------------------------
     */
    @Test
    public void testCreateAssistant() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(assistant);
        assertEquals(TEST_USER_NAME, assistant.getName());
        assertEquals(TEST_USER_PASSWORD, assistant.getPassword());
        assertEquals(TEST_USER_USERNAME, assistant.getUsername());
    }

    @Test
    public void testCreateAssistantWithEmptyUsername() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";
        try {
            assistant = accountService.createAssistant("", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testCreateAssistantWithTooShortUsername() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";
        try {
            assistant = accountService.createAssistant("abcd1", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithTooLongUsername() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";
        try {
            assistant = accountService.createAssistant("abcd14567abcd14567", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithUsernameContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";
        try {
            assistant = accountService.createAssistant("abcde!!@gf", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithEmptyPassword() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";

        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, "", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Password cannot be empty.", error);
    }

    @Test
    public void testCreateAssistantWithTooShortPassword() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";

        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, "abcd", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithTooLongPassword() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";

        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, "abcdefghijklmnopqrstuvwxyz", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithPasswordContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";

        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, "abcd@!!hijk", TEST_USER_NAME, defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateAssistantWithEmptyName() {
        assertEquals(0, accountService.getAllAssistants().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Assistant assistant = null;
        String error = "";

        try {
            assistant = accountService.createAssistant(TEST_USER_USERNAME, TEST_USER_PASSWORD, "", defaultSystem);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(assistant);
        assertEquals("Name cannot be empty.", error);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------Test: Create Customer-----------------------------
    ----------------------------------------------------------------------------
     */
    @Test
    public void testCreateCustomer() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        assertNotNull(customer);
        assertEquals(TEST_USER_NAME, customer.getName());
        assertEquals(TEST_USER_PASSWORD, customer.getPassword());
        assertEquals(TEST_USER_USERNAME, customer.getUsername());
        assertEquals(TEST_CUSTOMER_PHONE_NO, customer.getPhoneNo());
        assertEquals(TEST_CUSTOMER_ADDRESS, customer.getHomeAddress());
        assertEquals(TEST_CUSTOMER_EMAIL, customer.getEmail());
    }

    @Test
    public void testCreateCustomerWithEmptyUsername() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer("", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Username cannot be empty!", error);
    }

    @Test
    public void testCreateCustomerWithTooShortUsername() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer("abcd", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithTooLongUsername() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer("abcdabcdabcdabcdabcd", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithUsernameContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer("abcd!!!hijk", TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Username must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithTooShortPassword() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, "abcd", TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithTooLongPassword() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, "abcdabcdabcdabcdabcd", TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithPasswordContainingIllegalCharacters() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";
        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, "abcd!!!hijk", TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Password must be at least 8 characters and at most 16 characters, " +
                "containing only lowercase and uppercase English alphabets and numbers.", error);
    }

    @Test
    public void testCreateCustomerWithEmptyName() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, "", defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Name cannot be empty.", error);
    }

    @Test
    public void testCreateCustomerWithEmptyPhoneNo() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, "", TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Phone NO cannot be empty.", error);
    }

    @Test
    public void testCreateCustomerWithEmptyAddress() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, "", TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Address cannot be empty.", error);
    }

    @Test
    public void testCreateCustomerWithEmptyEmail() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, "");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Email cannot be empty.", error);
    }

    @Test
    public void testCreateCustomerWithIllegalPhoneNo() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, "12345abc", TEST_CUSTOMER_ADDRESS, TEST_CUSTOMER_EMAIL);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Phone NO must be a 10-digit number.", error);
    }

    @Test
    public void testCreateCustomerWithIllegalEmail() {
        assertEquals(0, accountService.getAllCustomers().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Customer customer = null;
        String error = "";

        try {
            customer = accountService.createCustomer(TEST_USER_USERNAME, TEST_USER_PASSWORD, TEST_USER_NAME, defaultSystem, TEST_CUSTOMER_PHONE_NO, TEST_CUSTOMER_ADDRESS, "abc123.com");
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }

        assertNull(customer);
        assertEquals("Email must be of the form part_1@part_2.part_3.", error);
    }

    /*
    ----------------------------------------------------------------------------
    ------------------------Test: Add a Car to Customer-------------------------
    ----------------------------------------------------------------------------
     */
    @Test
    public void testUpdateCar(){



    }
}
