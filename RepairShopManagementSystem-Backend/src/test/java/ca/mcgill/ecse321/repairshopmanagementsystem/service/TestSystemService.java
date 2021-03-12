package ca.mcgill.ecse321.repairshopmanagementsystem.service;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;

@ExtendWith(MockitoExtension.class)
public class TestSystemService {

    @Mock
    private RepairShopManagementSystemRepository systemDao;

    @InjectMocks
    private SystemService service;

    private static final Integer TEST_SYSTEM_ID = 1;
    private static final String TEST_SYSTEM_NAME = "A test shop";
    private static final String TEST_SYSTEM_ADDRESS = "1234 Sunshine Street, Palm Beach, Florida";
    private static final String TEST_SYSTEM_PHONE_NO = "1002003000";

    /**
     * @author Byron Chen
     */
    @BeforeEach
    public void setMockOutput() {
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

        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
    }

    /**
     * Case1: Create a good system
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystem() {
        assertEquals(0, service.getAllSystems().size());

        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNotNull(system);
        assertEquals(TEST_SYSTEM_NAME, system.getBusinessName());
        assertEquals(TEST_SYSTEM_PHONE_NO, system.getBusinessPhoneNumber());
        assertEquals(TEST_SYSTEM_ADDRESS, system.getBusinessAddress());
    }

    /**
     * Case2: Create a null system
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystemNull() {
        assertEquals(0, service.getAllSystems().size());

        String name = null;
        String address = null;
        String phoneNo = null;

        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(name, phoneNo, address);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(system);
        // check error
        assertEquals("Business name cannot be empty! Business phone number cannot be empty! Business address cannot be empty! ", error);
    }

    /**
     * Case3: Create an empty system
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystemEmpty() {
        assertEquals(0, service.getAllSystems().size());

        String name = "";
        String phoneNo = "";
        String address = "";


        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(name, phoneNo, address);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(system);
        // check error
        assertEquals("Business name cannot be empty! Business phone number cannot be empty! Business address cannot be empty! ", error);
    }

    /**
     * Case4: Create a system with a bad name but all other are good
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystemWithBadName() {
        assertEquals(0, service.getAllSystems().size());

        String name = "A Big Company#";

        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(name, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(system);
        // check error
        assertEquals("Business name illegal! ", error);
    }

    /**
     * Case5: Create a system with bad phone number but all other are good
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystemWithBadPhoneNo() {
        assertEquals(0, service.getAllSystems().size());

        String phoneNo = "12345678901234567890";

        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(TEST_SYSTEM_NAME, phoneNo, TEST_SYSTEM_ADDRESS);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(system);
        // check error
        assertEquals("Business phone number illegal! ", error);
    }

    /**
     * Case6: Create a system with bad address but all other are good
     *
     * @author Byron Chen
     */
    @Test
    public void testCreateSystemWithBadAddress() {
        assertEquals(0, service.getAllSystems().size());

        String address = "1231 goodStreet$$$, Montreal";

        RepairShopManagementSystem system = null;
        String error = "";

        try {
            system = service.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, address);

        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNull(system);
        // check error
        assertEquals("Business address illegal! ", error);
    }
}