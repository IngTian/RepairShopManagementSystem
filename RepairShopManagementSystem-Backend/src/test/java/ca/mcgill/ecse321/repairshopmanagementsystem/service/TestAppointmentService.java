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


        lenient().when(carDao.save(any(Car.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(appointmentDao.save(any(Appointment.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(shiftDao.save(any(Shift.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(spaceDao.save(any(Space.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(systemDao.save(any(RepairShopManagementSystem.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(billDao.save(any(Bill.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(serviceDao.save(any(Service.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(assistantDao.save(any(Assistant.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(scheduleDao.save(any(Schedule.class))).thenAnswer(returnParameterAsAnswer);
        lenient().when(customerDao.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    }

    @Test
    public void testCreateAppointment() {
        Appointment a = null;
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String serviceType = "Car Wash";
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String username1 = "TestAsssss1";
        String password1 = "test12345";
        String name1 = "test";
        Assistant ass1 = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        ;
        TA.add(ass);
        TA.add(ass1);
        Service service = appointmentService.createService(serviceType, TA, a);
        Integer max = 200;
        Space space = appointmentService.createSpace(max, system);
        String email = "p11@mcgill.ca";
        String add = "1178 rue saint west";
        Customer customer = accountService.createCustomer(username, password, name, system, phno, add, email);
        String plate = "A123456";
        String model = "benz";
        String man = "kevin";
        String year = "1999";
        Car car = accountService.createCar(plate, model, year, man, customer);
        Set<Car> CA = new HashSet<Car>();
        CA.add(car);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "12:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = appointmentService.createShift(date, startTime, endTime, a, schedule, ass);
        Integer price = 100;
        Bill bill = appointmentService.createBill(price, a, false);
        Set<Bill> BA = new HashSet<Bill>();
        BA.add(bill);
        try {
            a = appointmentService.createAppointment(service, space, customer, CA, shift, BA);

        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(a);
        assertEquals(a.getCustomer(), customer);
    }

    @Test
    public void testCreateAppointmentFaileNullService() {
        String error = "";
        Appointment a = null;
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String serviceType = "Car Wash";
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String username1 = "TestAsssss1";
        String password1 = "test12345";
        String name1 = "test";
        Assistant ass1 = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        ;
        TA.add(ass);
        TA.add(ass1);
        Service service = appointmentService.createService(serviceType, TA, a);
        Integer max = 200;
        Space space = appointmentService.createSpace(max, system);
        String email = "p11@mcgill.ca";
        String add = "1178 rue saint west";
        Customer customer = accountService.createCustomer(username, password, name, system, phno, add, email);
        String plate = "A123456";
        String model = "benz";
        String man = "kevin";
        String year = "1999";
        Car car = accountService.createCar(plate, model, year, man, customer);
        Set<Car> CA = new HashSet<Car>();
        CA.add(car);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "12:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = appointmentService.createShift(date, startTime, endTime, a, schedule, ass);
        Integer price = 100;
        Bill bill = appointmentService.createBill(price, a, false);
        Set<Bill> BA = new HashSet<Bill>();
        BA.add(bill);
        try {
            a = appointmentService.createAppointment(null, space, customer, CA, shift, BA);

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(a);
        assertEquals(error, "Can not have a null service");
    }

    @Test
    public void testCreateAppointmentFaileNullCar() {
        String error = "";
        Appointment a = null;
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String serviceType = "Car Wash";
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String username1 = "TestAsssss1";
        String password1 = "test12345";
        String name1 = "test";
        Assistant ass1 = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        ;
        TA.add(ass);
        TA.add(ass1);
        Service service = appointmentService.createService(serviceType, TA, a);
        Integer max = 200;
        Space space = appointmentService.createSpace(max, system);
        String email = "p11@mcgill.ca";
        String add = "1178 rue saint west";
        Customer customer = accountService.createCustomer(username, password, name, system, phno, add, email);
        String plate = "A123456";
        String model = "benz";
        String man = "kevin";
        String year = "1999";
        Car car = accountService.createCar(plate, model, year, man, customer);
        Set<Car> CA = new HashSet<Car>();
        CA.add(car);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "12:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = appointmentService.createShift(date, startTime, endTime, a, schedule, ass);
        Integer price = 100;
        Bill bill = appointmentService.createBill(price, a, false);
        Set<Bill> BA = new HashSet<Bill>();
        BA.add(bill);
        try {
            a = appointmentService.createAppointment(service, space, customer, null, shift, BA);

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(a);
        assertEquals(error, "You must have a car for appointment");
    }

    @Test
    public void testCreateSpace() {
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Integer maxWeightLoad = 200;
        Space space = null;
        try {
            space = appointmentService.createSpace(maxWeightLoad, system);

        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(space);
        assertEquals(space.getMaxWeightLoad(), maxWeightLoad);
    }

    /**
     * @author kevinli
     * create a space unsucessfully due to invalid weight;
     */
    @Test
    public void testCreateSpaceInvalidMaxWeight() {
        String error = null;
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Integer maxWeightLoad = 0;
        Space space = null;
        try {
            space = appointmentService.createSpace(maxWeightLoad, system);

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(space);
        assertEquals(error, "invalid maxweight");


    }

    /**
     * @author kevinLi
     * create a shift that meets all the standard
     */
    @Test
    public void testCreateShift() {
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String username = "TestAsssss";
        String password = "test12344";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "12:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = null;
        Appointment app = new Appointment();
        try {
            shift = appointmentService.createShift(date, startTime, endTime, app, schedule, ass);

        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(shift);
        assertEquals(shift.getDate(), date);

    }

    @Test
    public void testCreateShiftWithNullInput() {
        String error = "";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "12:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = null;
        Appointment app = new Appointment();
        try {
            shift = appointmentService.createShift(null, startTime, endTime, app, schedule, ass);

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals(error, "why do you have a null input, out of your mind?");

    }

    @Test
    public void testCreateShiftWithInvalidInput() {
        String error = "";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        Assistant ass = accountService.createAssistant(username, password, name, system);
        String valueDate = "2021-03-20";
        Date date = Date.valueOf(valueDate);
        String valueTime = "11:00:00";
        Time startTime = Time.valueOf(valueTime);
        String valueendTime = "10:00:00";
        Time endTime = Time.valueOf(valueendTime);
        Schedule schedule = scheduleService.createSchedule(null, null, system);
        Shift shift = null;
        Appointment app = new Appointment();
        try {
            shift = appointmentService.createShift(date, startTime, endTime, app, schedule, ass);

        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals(error, "starttime cant be after endtime");


    }

    @Test
    public void testUpdateShiftDate() {
        Date date = Date.valueOf("2020-03-03");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("12:00:00");
        Integer id = 1;
        Shift shift = null;
        try {
            shift = appointmentService.updateShift(date, startTime, endTime, id);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(shift);
        assertEquals(shift.getDate(), date);

    }

    @Test
    public void testUpdateShiftTime() {
        Date date = Date.valueOf("2020-03-03");
        Time startTime = Time.valueOf("11:00:00");
        Time endTime = Time.valueOf("12:00:00");
        Integer id = 1;
        Shift shift = null;
        try {
            shift = appointmentService.updateShift(date, startTime, endTime, id);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(shift);
        assertEquals(shift.getStartTime(), startTime);

    }

    @Test
    public void testUpdateShiftTimeFailed() {
        String error = "";
        Date date = Date.valueOf("2020-03-03");
        Time startTime = Time.valueOf("11:00:00");
        Time endTime = Time.valueOf("10:00:00");
        Integer id = 1;
        Shift shift = null;
        try {
            shift = appointmentService.updateShift(date, startTime, endTime, id);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(shift);
        assertEquals(error, "starttime cant be after endtime");

    }

    @Test
    public void testUpdateBill() {
        Bill b = null;
        Integer price = 200;
        try {
            b = appointmentService.updateBill(ID, price, true, price);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(b);
        assertEquals(b.getIsPaid(), true);
        assertEquals(b.getPrice(), price);

    }

    @Test
    public void testUpdateBillFailed() {
        String error = "";
        Bill b = null;
        Integer price = 0;
        try {
            b = appointmentService.updateBill(ID, price, true, price);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(b);
        assertEquals(error, "invalid newprice");


    }

    @Test
    public void testUpdateService() {
        Service service = null;
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Assistant ass = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        TA.add(ass);
        String ServiceT = "Maintenance";
        try {

            service = appointmentService.updateService(ID, ServiceT, TA);


        } catch (IllegalArgumentException e) {
            fail();
        }


        assertNotNull(service);
        assertEquals(service.getServiceType(), ServiceT);

    }


    @Test
    public void testUpdateServiceFailedNoAssistant() {
        String error = "";
        Service service = null;
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Assistant ass = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        //TA.add(ass);
        String ServiceT = "Maintenance";
        try {

            service = appointmentService.updateService(ID, ServiceT, TA);


        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }


        assertNull(service);
        assertEquals(error, "Assistant List cannot be empty!");

    }


    @Test
    public void testUpdateServiceFailedNullAssistant() {
        String error = "";
        Service service = null;
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Assistant ass = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        //TA.add(ass);
        String ServiceT = "Maintenance";
        try {

            service = appointmentService.updateService(ID, ServiceT, null);


        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }


        assertNull(service);
        assertEquals(error, "Assistant List cannot be null!");

    }


    @Test
    public void testUpdateServiceFailedNullserviceType() {
        String error = "";
        Service service = null;
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Assistant ass = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        TA.add(ass);
        String ServiceT = "Maintenance";
        try {

            service = appointmentService.updateService(ID, null, TA);


        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }


        assertNull(service);
        assertEquals(error, "Service type cannot be null!");

    }


    @Test
    public void testUpdateServiceFailedEmptyserviceType() {
        String error = "";
        Service service = null;
        String username = "TestAsssss";
        String password = "test12345";
        String name = "test";
        String phno = "4190000000";
        String address = "123 mcgill";
        RepairShopManagementSystem system = systemService.createSystem("businessName", phno, address);
        Assistant ass = accountService.createAssistant(username, password, name, system);
        Set<Assistant> TA = new HashSet<Assistant>();
        TA.add(ass);
        String ServiceT = "Maintenance";
        try {

            service = appointmentService.updateService(ID, "", TA);


        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }


        assertNull(service);
        assertEquals(error, "Service type cannot be empty!");

    }


    @Test
    public void testCreateService() {
        assertEquals(0, appointmentService.getAllServices().size());
        String username = "SOMEBODY1";
        String password = "Qwerty123";
        String name = "Some Body";
        RepairShopManagementSystem system = systemService.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        String serviceType = "Car Wash";
        Set<Assistant> assistants = new HashSet<Assistant>();
        assistants.add(accountService.createAssistant(username, password, name, system));
        ca.mcgill.ecse321.repairshopmanagementsystem.model.Service service = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(service);
        assertEquals(serviceType, service.getServiceType());
        assertEquals(assistants, service.getAssistant());
    }

    @Test
    public void testCreateServiceTypeNull() {
        assertEquals(0, appointmentService.getAllServices().size());
        String username = "SOMEBODY1";
        String password = "Qwerty123";
        String name = "Some Body";
        RepairShopManagementSystem system = systemService.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        String serviceType = null;
        Set<Assistant> assistants = new HashSet<Assistant>();
        assistants.add(accountService.createAssistant(username, password, name, system));
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Service type cannot be null!", error);
    }

    @Test
    public void testCreateServiceTypeEmpty() {
        assertEquals(0, appointmentService.getAllServices().size());
        String username = "SOMEBODY1";
        String password = "Qwerty123";
        String name = "Some Body";
        RepairShopManagementSystem system = systemService.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        String serviceType = "";
        Set<Assistant> assistants = new HashSet<Assistant>();
        assistants.add(accountService.createAssistant(username, password, name, system));
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Service type cannot be empty!", error);
    }

    @Test
    public void testCreateServiceTypeSpaces() {
        assertEquals(0, appointmentService.getAllServices().size());
        String username = "SOMEBODY1";
        String password = "Qwerty123";
        String name = "Some Body";
        RepairShopManagementSystem system = systemService.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        String serviceType = " ";
        Set<Assistant> assistants = new HashSet<Assistant>();
        assistants.add(accountService.createAssistant(username, password, name, system));
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Service type cannot be empty!", error);
    }

    @Test
    public void testCreateServiceTypeWrong() {
        assertEquals(0, appointmentService.getAllServices().size());
        String username = "SOMEBODY1";
        String password = "Qwerty123";
        String name = "Some Body";
        RepairShopManagementSystem system = systemService.createSystem(TEST_SYSTEM_NAME, TEST_SYSTEM_PHONE_NO, TEST_SYSTEM_ADDRESS);
        String serviceType = "aNewType";
        Set<Assistant> assistants = new HashSet<Assistant>();
        assistants.add(accountService.createAssistant(username, password, name, system));
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Service type must be Car Wash, Maintenance, Change Tire or Repair!", error);
    }

    @Test
    public void testCreateServiceAssistantNull() {
        assertEquals(0, appointmentService.getAllServices().size());
        String serviceType = "Car Wash";
        Set<Assistant> assistants = null;
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Assistant List cannot be null!", error);
    }

    @Test
    public void testCreateServiceAssistantEmpty() {
        assertEquals(0, appointmentService.getAllServices().size());
        String serviceType = null;
        Set<Assistant> assistants = new HashSet<Assistant>();
        Service service = null;
        String error = null;
        try {
            service = appointmentService.createService(serviceType, assistants, new Appointment());
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(service);
        assertEquals("Assistant List cannot be empty!", error);
    }

    @Test
    public void testCreateBill() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = 114514;
        Bill bill = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            fail();
        }
        assertNotNull(bill);
        assertEquals(price, bill.getPrice());
        assertEquals(false, bill.getIsPaid());
    }

    @Test
    public void testCreateBillPriceNull() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = null;
        Bill bill = null;
        String error = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(bill);
        assertEquals("Price cannot be empty!", error);
    }

    @Test
    public void testCreateBillPriceNotPositive() {
        assertEquals(0, appointmentService.getAllBills().size());
        Integer price = -11514;
        Bill bill = null;
        String error = null;
        try {
            bill = appointmentService.createBill(price, new Appointment(), false);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        assertNull(bill);
        assertEquals("Price cannot be negative or zero!", error);
    }

    // Whenever anything is saved, just return the parameter object
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
        return invocation.getArgument(0);
    };


}


