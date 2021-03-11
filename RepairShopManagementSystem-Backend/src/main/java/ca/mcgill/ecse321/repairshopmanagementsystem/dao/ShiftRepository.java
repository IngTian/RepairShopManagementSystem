package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Appointment;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Shift;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface ShiftRepository extends CrudRepository<Shift, Integer> {
	Shift findShiftByShiftId(Integer id);
	Shift findShiftByAppointment(Appointment appointment);
	 Set<Shift> findShiftsBySchedule(Schedule schedule);
}
