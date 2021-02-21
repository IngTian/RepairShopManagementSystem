package ca.mcgill.ecse321.repairshopmanagementsystem.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRepairShopManagementPersistence {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AssistantRepository assistantRepository;
    @Autowired
    private BillRepository BillRepository;
    @Autowired
    private CarRepository CarRepository;
    @Autowired
    private CustomerRepository CustomerRepository;
    @Autowired
    private OwnerRepository OwnerRepository;
    @Autowired
    private RepairShopManagementSystemRepository RepairShopManagementSystemRepository;
    @Autowired
    private ScheduleRepository ScheduleRepository;
    @Autowired
    private ServiceRepository ServiceRepository;

    @Autowired
    private ShiftRepository ShiftRepository;
    @Autowired
    private SpaceRepository SpaceRepository;


    @AfterEach
    @BeforeEach
    public void clearAll() {

//        appointmentRepository.deleteAll();
//        assistantRepository.deleteAll();
//        BillRepository.deleteAll();
//        CarRepository.deleteAll();
//        CustomerRepository.deleteAll();
//        OwnerRepository.deleteAll();
//        ShiftRepository.deleteAll();
        RepairShopManagementSystemRepository.deleteAll();
//        ScheduleRepository.deleteAll();
//        ServiceRepository.deleteAll();
//
//        ShiftRepository.deleteAll();
//        SpaceRepository.deleteAll();


    }

    @Test
    public void testSpaceRepPer() {
        Integer id = 1;
        Space space = new Space();
        space.setSpaceId(id);
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        space.setRepairShopManagementSystem(sys);
        RepairShopManagementSystemRepository.save(sys);
        SpaceRepository.save(space);
        space = null;
        space = SpaceRepository.findSpaceByspaceId(id);

        assertNotNull(space);
        assertEquals(space.getSpaceId(), id);
    }

    @Test
    public void testShiftPer() {
        Integer id = 2;
        Shift shift = new Shift();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        shift.setShiftId(id);
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        shift.setSchedule(schedule);
        ScheduleRepository.save(schedule);


        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);
        shift.setAssistant(assistant);


        Appointment app = new Appointment();
        shift.setAppointment(app);
        app.setShift(shift);
        ;
        Service s = new Service();
        s.setAppointment(app);
        Space space = new Space();
        space.setRepairShopManagementSystem(sys);
        SpaceRepository.save(space);

        app.setService(s);
        Customer cu = new Customer();
        cu.setRepairShopManagementSystem(sys);
        CustomerRepository.save(cu);
        ServiceRepository.save(s);


        appointmentRepository.save(app);
        ShiftRepository.save(shift);
        shift = null;
        shift = ShiftRepository.findSpaceByShiftId(id);
        assertNotNull(shift);
        assertEquals(shift.getShiftId(), id);


    }

    @Test
    public void testServicePer() {
        Integer id = 3;
        Shift shift = new Shift();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        shift.setShiftId(id);
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        shift.setSchedule(schedule);
        ScheduleRepository.save(schedule);


        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);
        shift.setAssistant(assistant);


        Appointment app = new Appointment();
        shift.setAppointment(app);
        app.setShift(shift);
        ;
        Service s = new Service();
        s.setAppointment(app);
        Space space = new Space();
        space.setRepairShopManagementSystem(sys);
        SpaceRepository.save(space);
        s.setServiceId(id);

        app.setService(s);
        Customer cu = new Customer();
        cu.setRepairShopManagementSystem(sys);
        CustomerRepository.save(cu);
        ServiceRepository.save(s);


        appointmentRepository.save(app);
        ShiftRepository.save(shift);
        s = null;
        s = ServiceRepository.findServiceByserviceId(id);
        assertNotNull(shift);
        assertEquals(shift.getShiftId(), id);
    }

    @Test
    public void testSchedule() {
        Integer id = 4;
        Schedule schedule = new Schedule();
        schedule.setId(id);
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);
        schedule.setId(id);
        schedule = null;
        schedule = ScheduleRepository.findScheduleById(id);
        assertNotNull(schedule);
        assertEquals(schedule.getId(), id);

    }

    @Test
    public void testAppointment() {

        // Save RepairShopManagementSystem.
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        // Save Schedule.
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        ScheduleRepository.save(schedule);

        // Create assistant.
        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        // Create shift.
        Integer shiftId = 5;
        Shift shift = new Shift();
        shift.setShiftId(shiftId);
        shift.setSchedule(schedule);
        shift.setAssistant(assistant);
        ShiftRepository.save(shift);

        // Create space.
        Space testSpace = new Space();
        testSpace.setRepairShopManagementSystem(sys);
        SpaceRepository.save(testSpace);

        // Create customer.
        Customer testCustomer = new Customer();
        testCustomer.setRepairShopManagementSystem(sys);
        CustomerRepository.save(testCustomer);

        // Create service.
        Service testService = new Service();

        // Create appointment.
        Integer appointmentId = 100;
        Appointment testAppointment = new Appointment();
        testAppointment.setAppointmentId(appointmentId);
        shift.setAppointment(testAppointment);
        testAppointment.setShift(shift);
        testService.setAppointment(testAppointment);
        testAppointment.setService(testService);
        testAppointment.setCustomer(testCustomer);
        testAppointment.setSpace(testSpace);
        appointmentRepository.save(testAppointment);


        testAppointment = null;
        testAppointment = appointmentRepository.findAppointmentByAppointmentId(appointmentId);
        assertNotNull(testAppointment);
        assertEquals(testAppointment.getAppointmentId(), appointmentId);
    }

    @Test
    public void testCusPer() {
        Integer id = 1;
        Customer customer = new Customer();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        customer.setRepairShopManagementSystem(sys);
        CustomerRepository.save(customer);

        customer = null;
        customer = CustomerRepository.findCustomerByUserId(id);
        assertNotNull(customer);
        assertEquals(customer.getUserId(), id);
    }

    @Test
    public void testAssPer() {
        Integer id = 1;
        Assistant assistant = new Assistant();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);

        assistant = null;
        assistant = assistantRepository.findAssistantByUserId(id);
        assertNotNull(assistant);
        assertEquals(assistant.getUserId(), id);
    }

    @Test
    public void testBill() {
        Integer billNum = 1;
        Integer price = 100;

        Integer id = 2;
        Shift shift = new Shift();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        shift.setShiftId(id);
        Schedule schedule = new Schedule();
        schedule.setRepairShopManagementSystem(sys);
        shift.setSchedule(schedule);
        ScheduleRepository.save(schedule);


        Assistant assistant = new Assistant();
        assistant.setRepairShopManagementSystem(sys);
        assistantRepository.save(assistant);
        shift.setAssistant(assistant);


        Appointment app = new Appointment();
        shift.setAppointment(app);
        app.setShift(shift);
        ;
        Service s = new Service();
        s.setAppointment(app);
        Space space = new Space();
        space.setRepairShopManagementSystem(sys);
        SpaceRepository.save(space);

        app.setService(s);
        Customer cu = new Customer();
        cu.setRepairShopManagementSystem(sys);
        CustomerRepository.save(cu);
        ServiceRepository.save(s);


        appointmentRepository.save(app);


        Bill bill = new Bill();
        bill.setPrice(price);
        bill.setBillNo(billNum);
        bill.setIsPaid(true);
        bill.setAppointment(app);

        BillRepository.save(bill);
        bill = BillRepository.findBybillNo(billNum);
        assertNotNull(bill);
        assertEquals(price, bill.getPrice());
        assertEquals(billNum, bill.getBillNo());
        assertEquals(true, bill.getIsPaid());
        assertEquals(app, bill.getAppointment());
    }

    @Test
    public void testOwnerPer() {
        Owner owner = new Owner();
        Integer id = 8;
        owner.setUserId(id);
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);
        owner.setRepairShopManagementSystem(sys);
        owner.setName("Testname");
        OwnerRepository.save(owner);
        owner = null;
        owner = OwnerRepository.findOwnerByUserId(id);
        assertNotNull(owner);
        assertEquals(owner.getUserId(), id);
        assertEquals(owner.getName(), "Testname");

    }

    @Test
    public void testCar() {

        Customer customer = new Customer();
        Car A = new Car();
        String test = "testcar";
        A.setPlateNo(test);
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);
        customer.setRepairShopManagementSystem(sys);


        A.setCustomer(customer);

        CustomerRepository.save(customer);
        CarRepository.save(A);

        A = CarRepository.findCarByPlateNo(test);

        assertNotNull(A);
        assertEquals(test, A.getPlateNo());


    }
}
