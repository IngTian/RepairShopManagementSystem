package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public Set<Shift> getShifts(Schedule schedule) {
        return shiftRepository.findShiftsBySchedule(schedule);
    }

    @Transactional
    public List<Schedule> getAllSchedules() {
        return toList(scheduleRepository.findAll());
    }

    @Transactional
    public Schedule findSchedule(Integer id) {
        return scheduleRepository.findScheduleById(id);
    }
    
    @Transactional
    public Shift getShiftById(Integer id) {
    	return shiftRepository.findShiftByShiftId(id);
    }


    @Transactional
    public Schedule createSchedule(Set<Shift> shifts,  RepairShopManagementSystem repairShopManagementSystem ) {

        Schedule schedule = new Schedule();
        schedule.setTimeSlot(shifts);
        schedule.setRepairShopManagementSystem(repairShopManagementSystem) ;
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Transactional
    public Schedule updateSchedule(Schedule schedule, Shift shift) {
    	if(!schedule.getTimeSlot().remove(shift)) {
    		Set<Shift> shiftNew = schedule.getTimeSlot();
    		shiftNew.add(shift);
    		schedule.setTimeSlot(shiftNew);
    	}
        scheduleRepository.save(schedule);
        return schedule;
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }
}