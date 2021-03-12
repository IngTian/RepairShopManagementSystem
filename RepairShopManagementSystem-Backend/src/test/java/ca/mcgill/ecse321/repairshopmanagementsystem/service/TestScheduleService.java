package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import static org.junit.Assert.*;
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

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;


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
    private static final Integer TEST_SHIFT_ID = 111;


    @BeforeEach
    public void setMockOutput() {
        // findScheduleById
        lenient().when(scheduleDao.findScheduleById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TEST_SHIFT_ID)) {
                Shift shift = new Shift();
                shift.setShiftId(TEST_SHIFT_ID);
                return shift;
            } else {
                return null;
            }
        });
        
        // findScheduleById
        lenient().when(shiftDao.findShiftByShiftId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
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
            schedule = scheduleService.createSchedule(null, defaultSystem);
        } catch (Exception e) {
            error = e.getMessage();
        }

        assertNotNull(schedule);
        assertNull(schedule.getTimeSlot());
    }

    @Test
    public void testUpdateSchedule() {
        RepairShopManagementSystem defaultSystem = systemService.getSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        Schedule schedule = new Schedule();
        String error = "";
        Set<Shift> shifts = new HashSet<Shift>();
        Shift shift = new Shift();
        shift.setShiftId(111);
        Shift test_shift = new Shift();
        test_shift.setShiftId(222);
        shifts.add(test_shift);
        shifts.add(shift);
        try {
            schedule = scheduleService.createSchedule(shifts, defaultSystem);
            scheduleService.updateSchedule(schedule, shift);
        } catch (Exception e) {
            error = e.getMessage();
        }
        shifts.remove(shift);
        assertNotNull(schedule);
        assertEquals(shifts, schedule.getTimeSlot());
    }}
