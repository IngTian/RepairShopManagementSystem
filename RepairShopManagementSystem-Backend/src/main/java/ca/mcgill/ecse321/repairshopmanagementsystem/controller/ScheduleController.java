package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users")
public class ScheduleController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private ScheduleService scheduleService;

    /*
    ----------------------------------------------------------------------------
    ------------------------------------Schedule-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "schedules")
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return convertToDtoListForSchedule(scheduleList);
    }

    @PostMapping(value = "schedules/create")
    public ScheduleDto createSchedule(@RequestParam Integer weekNo) {
        Schedule newSchedule = scheduleService.createSchedule(systemService.getMostRecentSystem(), weekNo);
        return convertToDto(newSchedule);
    }

    @GetMapping(value = "schedule/{weekNo}")
    public ScheduleDto getSpecificSchedule(@PathVariable Integer weekNo) {
        Schedule schedule = scheduleService.getScheduleByWeekNo(weekNo);
        return convertToDto(schedule);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Shifts-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "shifts")
    public List<ShiftDto> getAllShifts() {
        List<Shift> shiftList = scheduleService.getAllShifts();
        return convertToDtoListForShift(shiftList);
    }

    @GetMapping(value = "shifts/assistant")
    public List<ShiftDto> getAllShiftsForAnAssistant(@RequestParam String username) {
        List<Shift> shifts = scheduleService.getAllShiftsByAssistant(accountService.getAssistant(username));
        return convertToDtoListForShift(shifts);
    }

    @PostMapping(value = "shifts/create")
    public ShiftDto createShift(@RequestParam Date date, @RequestParam Time startTime, @RequestParam Time endTime, @RequestParam String username) {
        Shift newShift = scheduleService.createShift(date, startTime, endTime, accountService.getAssistant(username));
        return convertToDto(newShift);
    }

    @PostMapping(value = "shifts/delete")
    public ShiftDto deleteShift(@RequestParam Integer shiftId) {
        Shift s = scheduleService.deleteShift(scheduleService.getShiftById(shiftId));
        return convertToDto(s);
    }

    @PostMapping(value = "shifts/change")
    public ShiftDto changeShift(@RequestParam Date newDate, @RequestParam Time newStartTime, @RequestParam Time newEndTime, @RequestParam Integer shiftId){
        Shift s = scheduleService.changeShift(scheduleService.getShiftById(shiftId), newDate, newStartTime, newEndTime);
        return convertToDto(s);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Convertors----------------------------------
    ----------------------------------------------------------------------------
     */

    private ShiftDto convertToDto(Shift shift) {
        ScheduleDto sto = new ScheduleDto(shift.getSchedule().getId(), convertToDto(shift.getSchedule().getRepairShopManagementSystem()));
        return new ShiftDto(shift.getDate(), shift.getStartTime(), shift.getEndTime(),
                convertToDto(shift.getAssistant()), convertToDto(shift.getAppointment()),
                sto, shift.getShiftId());
    }

    private AppointmentDto convertToDto(Appointment appointment) {
        List<Assistant> ass = new ArrayList<>();
        for (Assistant a : appointment.getService().getAssistant()) {
            ass.add(a);
        }
        ServiceDto sto = new ServiceDto(appointment.getService().getServiceType(), convertToDtoListForAssistant(ass));


        return new AppointmentDto();
    }

    private List<ShiftDto> convertToDtoListForShift(Iterable<Shift> shifts) {
        List<ShiftDto> result = new ArrayList<>();
        for (Shift s : shifts) {
            result.add(convertToDto(s));
        }
        return result;
    }

    private ScheduleDto convertToDto(Schedule schedule) {

        return new ScheduleDto(schedule.getId(), convertToDtoListForShift(schedule.getTimeSlot()),
                convertToDto(schedule.getRepairShopManagementSystem()));
    }


    private List<AssistantDto> convertToDtoListForAssistant(List<Assistant> user) {
        List<AssistantDto> result = new ArrayList<>();
        for (Assistant o : user)
            result.add(convertToDto(o));
        return result;
    }

    private List<ScheduleDto> convertToDtoListForSchedule(List<Schedule> ScheduleList) {
        List<ScheduleDto> result = new ArrayList<>();
        for (Schedule o : ScheduleList)
            result.add(convertToDto(o));
        return result;
    }

    private AssistantDto convertToDto(Assistant a) {
        return new AssistantDto(a.getUsername(), a.getName(), a.getPassword(),
                convertToDto(a.getRepairShopManagementSystem()));
    }

    private RepairShopManagementSystemDto convertToDto(RepairShopManagementSystem system) {
        return new RepairShopManagementSystemDto(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessPhoneNumber());
    }

}
