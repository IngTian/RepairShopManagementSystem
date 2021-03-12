package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    @Transactional
    public Appointment createAppointment(ca.mcgill.ecse321.repairshopmanagementsystem.model.Service service,
                                         Space space, Customer customer, Set<Car> Cars, Shift shift, Set<Bill> Bills) {
        if (service == null)
            throw new IllegalArgumentException("Can not have a null service");
        if (Cars == null || Cars.size() == 0)
            throw new IllegalArgumentException("You must have a car for appointment");
        if (shift == null)
            throw new IllegalArgumentException("You must have a Time for the appointment");
        List<Appointment> appointments = toList(appointmentRepository.findAll());
        for (Appointment a : appointments) {
            if (a.getSpace().getSpaceId() == space.getSpaceId()) {
                if (a.getShift().getDate().equals(shift.getDate())) {
                    if (((a.getShift().getStartTime().compareTo(shift.getStartTime()) < 0)
                            && a.getShift().getEndTime().compareTo(shift.getStartTime()) > 0)
                            || ((a.getShift().getStartTime().compareTo(shift.getEndTime()) < 0)
                            && a.getShift().getEndTime().compareTo(shift.getEndTime()) > 0)) {
                        throw new IllegalArgumentException("OverLap Space");
                    }
                }
            }

        }
        Appointment newAppointment = new Appointment();
        newAppointment.setBill(Bills);
        newAppointment.setService(service);
        newAppointment.setCar(Cars);
        newAppointment.setCustomer(customer);
        newAppointment.setShift(shift);
        newAppointment.setAppointmentId(customer.hashCode() + customer.getPassword().hashCode()
                + shift.hashCode() * shift.getStartTime().hashCode());
        appointmentRepository.save(newAppointment);
        return newAppointment;

    }

    @Transactional
    public Appointment findAppointment(Customer customer, Shift shift) {

        return appointmentRepository.findAppointmentByCustomerAndShift(customer, shift);
    }

    @Transactional
    public List<Appointment> findAppointmentsOfCustomer(String username) {
       Customer customer= customerRepository.findCustomerByUsername(username);
        return appointmentRepository.findByCustomer(customer);
    }

    @Transactional
    public Appointment updateAppointment(ca.mcgill.ecse321.repairshopmanagementsystem.model.Service service,
                                         Customer customer, Shift shift, Shift newShift, Set<Car> newCars, Space space, Set<Bill> Bills) {

        Appointment app = appointmentRepository.findAppointmentByCustomerAndShift(customer, shift);
        app.setBill(Bills);
        app.setService(service);
        app.setCar(newCars);
        app.setCustomer(customer);
        app.setShift(newShift);
        appointmentRepository.save(app);

        return app;
    }

    @Transactional
    public Space createSpace(Integer maxWeightLoad, RepairShopManagementSystem RepairShopManagementSystem) {
        String error = "";
        if (maxWeightLoad == 0) {
            error = "invalid maxweight";
        }

        error = error.trim();
        if (error.length() > 0)
            throw new IllegalArgumentException(error);
        Space newSpace = new Space();
        newSpace.setMaxWeightLoad(maxWeightLoad);
        newSpace.setRepairShopManagementSystem(RepairShopManagementSystem);
        spaceRepository.save(newSpace);
        return newSpace;

    }

    @Transactional
    public ca.mcgill.ecse321.repairshopmanagementsystem.model.Service createService(String serviceType,
                                                                                    Set<Assistant> assistants, Appointment appointment) {
        if (assistants == null)
            throw new IllegalArgumentException("Assistant List cannot be null!");
        if (assistants.size() == 0)
            throw new IllegalArgumentException("Assistant List cannot be empty!");
        if (serviceType == null)
            throw new IllegalArgumentException("Service type cannot be null!");
        if (serviceType.length() == 0)
            throw new IllegalArgumentException("Service type cannot be empty!");
        if (serviceType.charAt(0) == ' ')
            throw new IllegalArgumentException("Service type cannot be empty!");
        if (!serviceType.equals("Car Wash") && !serviceType.equals("Maintenance") && !serviceType.equals("Change Tire")
                && !serviceType.equals("Repair"))
            throw new IllegalArgumentException("Service type must be Car Wash, Maintenance, Change Tire or Repair!");
        ca.mcgill.ecse321.repairshopmanagementsystem.model.Service aService = new ca.mcgill.ecse321.repairshopmanagementsystem.model.Service();
        aService.setServiceType(serviceType);
        aService.setAssistant(assistants);
        aService.setAppointment(appointment);
        serviceRepository.save(aService);
        return aService;
    }

    @Transactional
    public ca.mcgill.ecse321.repairshopmanagementsystem.model.Service updateService(Integer appointmentID,
                                                                                    String serviceType, Set<Assistant> assistants) {

        ca.mcgill.ecse321.repairshopmanagementsystem.model.Service ser = null;
        if (assistants == null)
            throw new IllegalArgumentException("Assistant List cannot be null!");
        if (assistants.size() == 0)
            throw new IllegalArgumentException("Assistant List cannot be empty!");
        if (serviceType == null)
            throw new IllegalArgumentException("Service type cannot be null!");
        if (serviceType.length() == 0)
            throw new IllegalArgumentException("Service type cannot be empty!");
        if (serviceType.charAt(0) == ' ')
            throw new IllegalArgumentException("Service type cannot be empty!");
        if (!serviceType.equals("Car Wash") && !serviceType.equals("Maintenance") && !serviceType.equals("Change Tire")
                && !serviceType.equals("Repair"))
            throw new IllegalArgumentException("Service type must be Car Wash, Maintenance, Change Tire or Repair!");

        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentID);

        ser = appointment.getService();

        ser.setAssistant(assistants);
        ser.setServiceType(serviceType);
        ser.setAppointment(appointment);

        appointment.setService(ser);

        serviceRepository.save(ser);
        appointmentRepository.save(appointment);

        return ser;
    }

    @Transactional
    public Bill createBill(Integer price, Appointment appointment, boolean isPaid) {
        if (price == null)
            throw new IllegalArgumentException("Price cannot be empty!");
        if (price <= 0)
            throw new IllegalArgumentException("Price cannot be negative or zero!");
        Bill newBill = new Bill();
        newBill.setAppointment(appointment);
        newBill.setIsPaid(isPaid);
        newBill.setPrice(price);
        billRepository.save(newBill);

        return newBill;
    }

    @Transactional
    public Bill updateBill(Integer appointmentid, Integer price, boolean isPaid, Integer newPrice) {
        String error = "";
        if (newPrice <= 0) {
            error = "invalid newprice";
        }

        error = error.trim();
        if (error.length() > 0)
            throw new IllegalArgumentException(error);

        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentid);
        Bill b = new Bill();
        b.setIsPaid(isPaid);
        b.setPrice(newPrice);
        b.setAppointment(appointment);
        Set<Bill> TB = appointment.getBill();

        for (Bill a : TB) {

            if (a.getPrice() == price) {
                TB.remove(a);
                TB.add(b);
                break;
            }

        }
        billRepository.save(b);
        appointment.setBill(TB);
        appointmentRepository.save(appointment);

        return b;
    }

    @Transactional
    public Bill getBill(Appointment appointment, Integer price) {
        return billRepository.findBillByAppointmentAndPrice(appointment, price);
    }

    @Transactional
    public List<Appointment> getAllAppointments() {
        return toList(appointmentRepository.findAll());
    }

    @Transactional
    public List<ca.mcgill.ecse321.repairshopmanagementsystem.model.Service> getAllServices() {
        return toList(serviceRepository.findAll());
    }

    @Transactional
    public List<Bill> getAllBills() {
        return toList(billRepository.findAll());
    }

    @Transactional
    public List<Space> getAllSpace() {
        return toList(spaceRepository.findAll());
    }

    @Transactional
    public List<Shift> getAllShift() {
        return toList(shiftRepository.findAll());
    }

    @Transactional
    public ca.mcgill.ecse321.repairshopmanagementsystem.model.Service getService(String serviceType) {
        return serviceRepository.findServiceByServiceType(serviceType);
    }

    @Transactional
    public Shift createShift(Date startDate, Time startTime, Time endTime, Appointment appointment, Schedule schedule,
                             Assistant assistant) {


        if (startDate == null || startTime == null || schedule == null || assistant == null) {
            throw new IllegalArgumentException("why do you have a null input, out of your mind?");


        }
        String error = "";
        if (startTime.compareTo(endTime) > 0)
            error = "starttime cant be after endtime";
        error = error.trim();
        if (error.length() > 0)
            throw new IllegalArgumentException(error);
        Shift newShift = new Shift();
        newShift.setAppointment(appointment);
        newShift.setAssistant(assistant);
        newShift.setDate(startDate);
        newShift.setStartTime(startTime);
        newShift.setEndTime(endTime);
        newShift.setSchedule(schedule);
        int hash = assistant.hashCode();
        if (appointment != null)
            hash = appointment.hashCode();
        newShift.setShiftId(startDate.hashCode() * endTime.hashCode() + startTime.hashCode() + hash);
        shiftRepository.save(newShift);

        return newShift;
    }

    @Transactional
    public Shift getShift(Appointment appointment) {
        return shiftRepository.findShiftByAppointment(appointment);
    }

    @Transactional
    public Shift updateShift(Date startDate, Time startTime, Time endTime, Integer appointmentID) {
        String error = "";
        if (startTime.compareTo(endTime) > 0)
            error = "starttime cant be after endtime";
        error = error.trim();
        if (error.length() > 0)
            throw new IllegalArgumentException(error);

        Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(appointmentID);
        Shift s = new Shift();
        s.setDate(startDate);
        s.setStartTime(startTime);
        s.setEndTime(endTime);

        appointment.setShift(s);
        shiftRepository.save(s);
        appointmentRepository.save(appointment);
        return appointment.getShift();

    }

}
