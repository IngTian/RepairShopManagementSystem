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
import java.sql.*;
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

    	ServiceRepository.deleteAll();
        RepairShopManagementSystemRepository.deleteAll();



    }
    /**
     * @author kevinli
     */
    @Test
    public void testSpaceRepistoryPersistenceAndLoad() {
       //Create and Save repairshopmanagement system
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);
       //Create and Save Space in the database
        Integer maxweight=90;
        Space space = new Space();
        space.setRepairShopManagementSystem(sys);
        space.setMaxWeightLoad(90);
        SpaceRepository.save(space);
        
        //get the generated id
        Integer id=space.getSpaceId();
        space = null;
        
        //find the corresponding space from database table
        space = SpaceRepository.findSpaceBySpaceId(id);

        assertNotNull(space);
        assertEquals(space.getSpaceId(), id);
        assertEquals(space.getMaxWeightLoad(),maxweight);
    }
    /**
     * @author kevinli
     */

    @Test
    public void testShiftRepistoryPersistenceAndLoad() {
    	
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
        
        shift = null;
        
        //get the corresponding shift from database table
        shift = ShiftRepository.findShiftByShiftId(shiftId);

        assertNotNull(shift);
        assertEquals(shift.getShiftId(), shiftId);


    }
    /**
     * @author Ao Shen
     */
    @Test
    public void testServiceRepistoryPersistenceAndLoad() {
    	// Save RepairShopManagementSystem.
    	String serviceName="test";
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


        // Create appointment.
        Integer appointmentId = 100;
        Appointment testAppointment = new Appointment();
        
        // Create service.
        Service testService = new Service();
        
        testAppointment.setAppointmentId(appointmentId);
        shift.setAppointment(testAppointment);
        testAppointment.setShift(shift);
        testService.setAppointment(testAppointment);
        testAppointment.setCustomer(testCustomer);
        testAppointment.setSpace(testSpace);
        appointmentRepository.save(testAppointment);
        
        testService.setAppointment(testAppointment);
        testService.setServiceType("A");
        testService.setServiceType(serviceName);
        ServiceRepository.save(testService);
        Integer  serviceid  = testService.getServiceId();
        
        testService = null;
        testService = ServiceRepository.findServiceByServiceId(serviceid);

        assertNotNull(testService);
        assertEquals(testService.getServiceId(), serviceid);
        assertEquals(testService.getServiceType(), serviceName);
    }
    /**
     * @author Ing Tian
     */
    @Test
    public void testScheduleRepositoryPersistenceAndLoad() {
    	//Create and save a system
    	 RepairShopManagementSystem sys = new RepairShopManagementSystem();
         RepairShopManagementSystemRepository.save(sys);

         // Save Schedule.
         Schedule schedule = new Schedule();
         schedule.setRepairShopManagementSystem(sys);
         ScheduleRepository.save(schedule);
        Integer id=schedule.getId();
         schedule = null;
        
        schedule = ScheduleRepository.findScheduleById(id);
        
       
        assertNotNull(schedule);
        assertEquals(schedule.getId(), id);

    }
    /**
     * @author Ing Tian
     */
    @Test
    public void testAppointmentRepositoryPersistenceAndLoad() {

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
    /**
     * @author Byron Chen
     */
    @Test
    public void testCustomerRepositoryPersistenceAndLoad() {
        String phoneNo="3321-321";
        String address="mcgill";
        Customer customer = new Customer();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        customer.setRepairShopManagementSystem(sys);
        customer.setPhoneNo(phoneNo);
        customer.setHomeAddress(address);
        CustomerRepository.save(customer);
        Integer id=customer.getUserId();
        customer = null;
        customer = CustomerRepository.findCustomerByUserId(id);
        assertNotNull(customer);
        assertEquals(customer.getUserId(), id);
        assertEquals(customer.getPhoneNo(), phoneNo);
        assertEquals(customer.getHomeAddress(), address);
    }
    /**
     * @author Xiang Li
     */
    @Test
    public void testAssistantRepositoryPersistenceAndLoad() {
       String username="testuser";
        Assistant assistant = new Assistant();
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);

        assistant.setRepairShopManagementSystem(sys);
        assistant.setName(username);
        assistantRepository.save(assistant);
        Integer id=assistant.getUserId();

        assistant = null;
        assistant = assistantRepository.findAssistantByUserId(id);
        assertNotNull(assistant);
        assertEquals(assistant.getUserId(), id);
        assertEquals(assistant.getName(), username);
    }
    /**
     * @author Xiang Li
     */
    @Test
    public void testBillRepositoryPersistenceAndLoad() {
    	Integer price=10000;
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
        
        //Create Bill
        Integer BillId ;
        Bill bill = new Bill();
        bill.setAppointment(testAppointment);
        bill.setPrice(price);
        bill.setIsPaid(true);
        BillRepository.save(bill);
        BillId=bill.getBillNo();
        
        
        bill = null;
        bill = BillRepository.findBillByBillNo(BillId);
        assertNotNull(bill);
        assertEquals(bill.getBillNo(), BillId);
        assertEquals(bill.getPrice(), price);
    }
    /**
     * @author Xiang Li
     */
    @Test
    public void testOwnerRepositoryPersistenceAndLoad() {
    	String userName="testuser";
        Owner owner = new Owner();
        //Create and Save a system in the database table
        RepairShopManagementSystem sys = new RepairShopManagementSystem();
        RepairShopManagementSystemRepository.save(sys);
        //Create and save an owner in the system
        owner.setRepairShopManagementSystem(sys);
        owner.setName(userName);
        OwnerRepository.save(owner);
        Integer id=owner.getUserId();
        owner = null;
        
        
        owner = OwnerRepository.findOwnerByUserId(id);
        assertNotNull(owner);
        assertEquals(owner.getUserId(), id);
        assertEquals(owner.getName(), userName);

    }
    /**
     * @author Ao Shen
     */
    @Test
    public void testCarRepositoryPersistenceAndLoad() {
         String model="test";
    	 Customer customer = new Customer();
    	 //Create and Save a  RepairShopManagementSystem in the database
         RepairShopManagementSystem sys = new RepairShopManagementSystem();
         RepairShopManagementSystemRepository.save(sys);

          //Create and save a customer
         customer.setRepairShopManagementSystem(sys);
         CustomerRepository.save(customer);
         
         //Create and save a car
        Car A = new Car();
        String test = "testcar";
        A.setPlateNo(test);
       A.setCustomer(customer);
       A.setModel(model);
       CarRepository.save(A);
        //find the corresponding car
        A = CarRepository.findCarByPlateNo(test);

        assertNotNull(A);
        assertEquals(test, A.getPlateNo());
        assertEquals(model, A.getModel());


    }
}
