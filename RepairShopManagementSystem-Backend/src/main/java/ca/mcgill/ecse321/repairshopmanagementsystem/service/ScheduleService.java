package ca.mcgill.ecse321.repairshopmanagementsystem.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import ca.mcgill.ecse321.repairshopmanagementsystem.dao.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;


public class ScheduleService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    public Set<User> getUsers(Schedule schedule) {
        Set<User> set1 = ownerRepository.findOwnerBySchedule(schedule);
        Set<User> set2 = customerRepository.findCustomerBySchedule(schedule);
        Set<User> set3 = assistantRepository.findAssistantBySchedule(schedule);
        Set<User> result = new HashSet<User>();
        if (set1 != null) {
            result.addAll(set1);
        }
        if (set2 != null) {
            result.addAll(set2);
        }
        if (set3 != null) {
            result.addAll(set3);
        }
        return result;
    }


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
    public List<Schedule> findAllSchedules(String username) {

        return scheduleRepository.findSchedulesbyUsername(username);
    }

    @Transactional
    public Schedule createSchedule(Set<User> user, Set<Shift> shifts) {

        Schedule schedule = new Schedule();
        schedule.setUser(user);
        schedule.setTimeSlot(shifts);
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Transactional
    public Schedule updateSchedule(Schedule schedule, Set<User> user, Set<Shift> shifts) {
        schedule.setUser(user);
        schedule.setTimeSlot(shifts);
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