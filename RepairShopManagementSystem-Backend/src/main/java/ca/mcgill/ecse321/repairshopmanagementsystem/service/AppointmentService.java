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
	public List<Appointment> findAppointmentsOfCustomer(Customer customer) {

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
		Space newSpace = new Space();
		newSpace.setMaxWeightLoad(maxWeightLoad);
		newSpace.setRepairShopManagementSystem(RepairShopManagementSystem);
		spaceRepository.save(newSpace);
		return newSpace;

	}

	@Transactional
	public ca.mcgill.ecse321.repairshopmanagementsystem.model.Service createService(String serviceType,
			Set<Assistant> assistants, Appointment appointment) {
		ca.mcgill.ecse321.repairshopmanagementsystem.model.Service aService = new ca.mcgill.ecse321.repairshopmanagementsystem.model.Service();
		aService.setServiceType(serviceType);
		aService.setAssistant(assistants);
		aService.setAppointment(appointment);
		serviceRepository.save(aService);
		return aService;
	}

	@Transactional
	public Bill createBill(Integer price, Appointment appointment, boolean isPaid) {

		Bill newBill = new Bill();
		newBill.setAppointment(appointment);
		newBill.setIsPaid(isPaid);
		newBill.setPrice(price);
		billRepository.save(newBill);

		return newBill;
	}

	@Transactional
	public Bill updateBill(Appointment appointment, Integer price, boolean isPaid, Integer newPrice) {
		Bill b = billRepository.findBillByAppointmentAndPrice(appointment, price);
		b.setIsPaid(isPaid);
		b.setPrice(newPrice);
		billRepository.save(b);

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
		Shift newShift = new Shift();
		newShift.setAppointment(appointment);
		newShift.setAssistant(assistant);
		newShift.setDate(startDate);
		newShift.setStartTime(startTime);
		newShift.setEndTime(endTime);
		newShift.setSchedule(schedule);
      newShift.setShiftId(startDate.hashCode()*endTime.hashCode()+startTime.hashCode()+appointment.hashCode());
		shiftRepository.save(newShift);

		return newShift;
	}

	@Transactional
	public Shift getShift(Appointment appointment) {
		return shiftRepository.findShiftByAppointment(appointment);
	}

	public Shift updateShift(Date startDate, Time startTime, Time endTime, Appointment appointment
			) {
		Shift s=shiftRepository.findShiftByAppointment(appointment);
		s.setDate(startDate);
		s.setStartTime(startTime);
		s.setEndTime(endTime);
		shiftRepository.save(s);
		return s;
	}

}
