package ca.mcgill.ecse321.repairshopmanagementsystem.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.AssistantRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.CarRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.CustomerRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.OwnerRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.RepairShopManagementSystemRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.ScheduleRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.ShiftRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Assistant;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Car;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Customer;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Owner;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepairShopManagementSystem;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Shift;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.User;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.AccountService;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.ScheduleService;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.SystemService;


@ExtendWith(MockitoExtension.class)
public class TestScheduleService {

    @Mock
    private OwnerRepository ownerDao;
    @Mock
    private AssistantRepository assistantDao;
    @Mock
    private ShiftRepository shiftDao;
    @Mock
    private RepairShopManagementSystemRepository systemDao;
    @Mock
    private ScheduleRepository scheduleDao;

    @InjectMocks
    private ScheduleService scheduleService;
    @InjectMocks
    private SystemService systemService;

    private static final String TEST_USER_USERNAME = "TestUsername";
    private static final String TEST_USER_PASSWORD = "TestPassword";
    private static final String TEST_USER_NAME = "Someone";
    private static final Integer TEST_USER_ID = 1;

    private static final Integer TEST_SYSTEM_ID = 1;
    private static final String TEST_SYSTEM_NAME = "A test shop";
    private static final String TEST_SYSTEM_ADDRESS = "1234 Sunshine Street, Palm Beach, Florida";
    private static final String TEST_SYSTEM_PHONE_NO = "1002003000";

    private static final Integer TEST_SCHEDULE_ID = 123456789;
    private static final Shift TEST_SHIFT = new Shift();


    @BeforeEach
    public void setMockOutput() {
        // findScheduleById
        lenient().when(scheduleDao.findScheduleById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SCHEDULE_ID)) {
                Schedule schedule = new Schedule();
                schedule.setId(TEST_SCHEDULE_ID);
                return schedule;
            } else {
                return null;
            }
        });

        // system
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


        // Whenever anything is saved, just return the parameter object
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        };

        lenient().when(scheduleDao.save(any(Schedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
    }
    @Test
    public void testCreateSchedule() {
        assertEquals(0, scheduleService.getAllSchedules().size());

        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Schedule schedule = new Schedule();
        String error = "";
        try {
            schedule = scheduleService.createSchedule(null,null, defaultSystem);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNotNull(schedule);
        assertEquals(null, schedule.getUser());
        assertEquals(null, schedule.getTimeSlot());
    }

    @Test
    public void testUpdateSchedule() {
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Schedule schedule = new Schedule();
        String error = "";
        Set<User> users = new HashSet<User>();
        Set<Shift> shifts = new HashSet<Shift>();
        User test_user = new Customer();
        users.add(test_user);
        Shift test_shift = new Shift();
        shifts.add(test_shift);
        try {
            schedule = scheduleService.createSchedule(null,null, defaultSystem);
            scheduleService.updateSchedule(schedule, users, shifts);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNotNull(schedule);
        assertEquals(users, schedule.getUser());
        assertEquals(shifts, schedule.getTimeSlot());
    }


}