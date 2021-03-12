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
	private ScheduleRepository scheduleRepository;
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private ShiftRepository shiftRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CarRepository carRepository;
	@Autowired
	private AssistantRepository assistantRepository;

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
	public Appointment makeAppointment(String serviceType, String username, String plateNo, Date startDate,
			Time startTime, Time endTime, Integer scheduleID, Integer weight) {

		List<Assistant> assistants = toList(assistantRepository.findAll());
		Assistant ass = null;
		for (Assistant a : assistants) {
			
			if (a.getShift().size() == 0) {
				ass = a;
				break;
			} else {
				for (Shift s : a.getShift()) {
                       if(s.getDate().equals(startDate)) {
                    	   if(s.getStartTime().compareTo(startTime)<0 &&s.getEndTime().compareTo(endTime)<=0 ) {
                    		   ass=a;
                    		   break;
                    	   }
                    	   else if(s.getStartTime().compareTo(endTime)>=0 ) {
                    		   ass=a;
                    		   break;
                    	   }
                       }
				}

			}
		}
		if(ass==null) throw new IllegalArgumentException("Dont have available assistant");
		
		// 在这个办法里因为model限制无法知道具体的bill和service相连的assistant，所以在这里全部设为null
		// 然后由店主或者assistant去把这些缺少的东西加上去

		Appointment app = null;
		Customer customer = customerRepository.findCustomerByUsername(username);
		ca.mcgill.ecse321.repairshopmanagementsystem.model.Service service = new ca.mcgill.ecse321.repairshopmanagementsystem.model.Service();
		service.setServiceType(serviceType);

		Schedule schedule = scheduleRepository.findScheduleById(scheduleID);
		Set<Shift> shifts = shiftRepository.findShiftsBySchedule(schedule);

		// 在做appointment的时候我们只考虑一辆车的情况，因为考虑多辆车有点奇怪。
		Car car = carRepository.findCarByPlateNo(plateNo);
		if (car == null)
			throw new IllegalArgumentException("you need to have a car to start the appointment");
		Set<Car> carset = new HashSet<>();
		carset.add(car);

		Shift selected = null;
		List<Appointment> apps = toList(appointmentRepository.findAll());
		List<Space> spaces = toList(spaceRepository.findAll());

		// 在系统里寻找maxweightload符合标准的space，没有的话就预约失败
		List<Space> enoughWeight = new ArrayList<>();
		for (Space s : spaces) {
			if (s.getMaxWeightLoad() >= weight) {
				enoughWeight.add(s);
			}
		}

		if (enoughWeight.size() == 0)
			throw new IllegalArgumentException("no space for you");

		// 寻找是否又符合标准的shift，没有的话就结束，ps：我知道这样检测不完整，但在这里就不管他了

		for (Shift s : shifts) {
			if (s.getDate().equals(startDate) && s.getStartTime().compareTo(startTime) < 0
					&& s.getEndTime().compareTo(endTime) > 0) {
				selected = s;
				break;
			}
		}
		if (selected == null)
			throw new IllegalArgumentException("no available shift");

		// 遍历每一个appointment，如果他的shift和当前想预定的时间重合并且他用的space符合标准的话，
		// 从enoughweight这个list里去掉这个space，在最后再检查是都还有可用的space。
		for (Appointment a : apps) {
			if (a.getShift().getDate().equals(startDate) && a.getShift().getStartTime().compareTo(startTime) <= 0
					&& a.getShift().getEndTime().compareTo(startTime) >= 0) {
				if (enoughWeight.contains(a.getSpace()))
					enoughWeight.remove(a.getSpace());

			} else if (a.getShift().getDate().equals(startDate) && a.getShift().getStartTime().compareTo(endTime) <= 0
					&& a.getShift().getEndTime().compareTo(endTime) >= 0) {

				if (enoughWeight.contains(a.getSpace()))
					enoughWeight.remove(a.getSpace());

			}
		}
		if (enoughWeight.size() == 0)
			throw new IllegalArgumentException("no available space at this time");
         Set<Assistant> assistantSet=new HashSet<>();
         assistantSet.add(ass);
         service.setAssistant( assistantSet);
         selected.setAssistant(ass);
		app.setCustomer(customer);
		app.setService(service);
		app.setCar(carset);
		app.setShift(selected);
		app.setSpace(enoughWeight.get(0));
		app.setAppointmentId(customer.getUserId() + service.hashCode() * carset.hashCode());
		service.setAppointment(app);
		customer.getAppointment().add(app);
		selected.setAppointment(app);

		customer.getAppointment().add(app);
		customerRepository.save(customer);
		serviceRepository.save(service);
		shiftRepository.save(selected);
		appointmentRepository.save(app);

		return app;
	}

	@Transactional
	public Bill addABillToAppointment(Integer id, Integer price) {
		// base on the model, its impossible to set the bill automatique when customer
		// make an appointment, there fore we have no choice but to set it to become
		// that once an appointment is maid, owner(or assistant) will update the bill
		// for the appointment

		if (price == null || price <= 0)
			throw new IllegalArgumentException("Invalid price Input");
		Bill result = new Bill();
		result.setIsPaid(false);
		result.setPrice(price);

		Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(id);
		result.setAppointment(appointment);
		if (appointment.getBill() == null) {
			Set<Bill> bills = new HashSet<>();
			bills.add(result);
			appointment.setBill(bills);
		} else {
			appointment.getBill().add(result);
		}
		appointmentRepository.save(appointment);
		billRepository.save(result);
		return result;
	}

	@Transactional
	public List<Appointment> findAppointmentsOfCustomer(String username) {
		Customer customer = customerRepository.findCustomerByUsername(username);
		return appointmentRepository.findByCustomer(customer);
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
		// base on the model, its impossible to set the bill automatique when customer
		// make an appointment, there fore we have no choice but to set it to become
		// that once an appointment is maid, owner(or assistant) will update the bill
		// for the appointment
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
	public Shift getShift(Integer ID) {
		Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(ID);
		return shiftRepository.findShiftByAppointment(appointment);
	}

	@Transactional
	public Space getSpaceOfAppointment(Integer ID) {
		Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(ID);
		return appointment.getSpace();
	}

	@Transactional
	public Set<Assistant> getAssistantOfAppointment(Integer ID) {
		Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(ID);
		return appointment.getService().getAssistant();
	}

	@Transactional
	public ca.mcgill.ecse321.repairshopmanagementsystem.model.Service findServiceByAppointment(Integer ID) {

		Appointment appointment = appointmentRepository.findAppointmentByAppointmentId(ID);
		return appointment.getService();
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
		appointment.getShift().setDate(startDate);
		appointment.getShift().setStartTime(startTime);
		appointment.getShift().setEndTime(endTime);

		shiftRepository.save(appointment.getShift());
		appointmentRepository.save(appointment);
		return appointment.getShift();

	}

}
