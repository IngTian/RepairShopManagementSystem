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
import java.util.HashSet;
import java.util.Set;

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
public class TestAppointmentService {
    @Mock
    private AppointmentRepository appointmentDao;
    @Mock
    private ShiftRepository shiftDao;
    @Mock
    private SpaceRepository spaceDao;
    @Mock
    private ServiceRepository serviceDao;
    @Mock
    private BillRepository billDao;
    @Mock
    private RepairShopManagementSystemRepository systemDao;

    @Mock
    private AssistantRepository assistantDao;

    @Mock
    private ScheduleRepository scheduleDao;

    @Mock
    private CustomerRepository customerDao;

    @Mock
    private CarRepository carDao;


    @InjectMocks
    private AccountService accountService;
    @InjectMocks
    private AppointmentService appointmentService;
    @InjectMocks
    private SystemService systemService;

    @InjectMocks
    private ScheduleService scheduleService;
    //private static final Shift shift=new Shift();

    private static final String customerName = "TESTCUSTOMER";
    private static final Integer ID = 1;
    private static final String AssistantName = "TESTASSISTANT";
    private static final String AssistantPassword = "123456sdf";
    private static final String Name = "TESTNAME";
    private static final String startDate = "2021-03-02";
    private static final String startTime = "10:00:00";
    private static final String EndTime = "11:00:00";
    private static final boolean ispaid = false;
    private static final Integer price = 100;
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

    private static final Integer TEST_BILL_PRICE = 114514;

    private static final String TEST_SERVICE_TYPE = "Car Wash";
    private static final String serviceType = "Car Wash";


    @BeforeEach
    public void setMockOutput() {

        lenient().when(appointmentDao.findAppointmentByAppointmentId(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ID)) {
                Appointment app = new Appointment();
                app.setAppointmentId(ID);
                Shift shift = new Shift();
                shift.setAppointment(app);
                shift.setDate(Date.valueOf(startDate));
                shift.setStartTime(Time.valueOf(startTime));
                shift.setEndTime(Time.valueOf(EndTime));

                Assistant assistant = new Assistant();
                assistant.setName(AssistantName);
                assistant.setPassword(AssistantPassword);
                assistant.setUsername(Name);
                Schedule schedule = new Schedule();


                shift.setAssistant(assistant);
                shift.setSchedule(schedule);
                app.setShift(shift);

                Bill b = new Bill();
                b.setAppointment(app);
                b.setIsPaid(ispaid);
                b.setPrice(price);
                Set<Bill> TB = new HashSet<Bill>();
                TB.add(b);
                Set<Assistant> AT = new HashSet<Assistant>();
                AT.add(assistant);

                Service service = new Service();
                service.setAppointment(app);
                service.setServiceType(serviceType);
                service.setAssistant(AT);
                app.setBill(TB);
                app.setService(service);
                return app;
            } else {
                return null;
            }
        });
        
    }


}


