package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "schedule")
    public List<ScheduleDto> getAllSchedules() {
        List<Schedule> scheduleList = scheduleService.getAllSchedules();
        return convertToDtoListForSchedule(scheduleList);
    }

    @PostMapping(value = "schedule/create")
    public ScheduleDto createSchedule(@RequestBody RepairShopManagementSystemDto system) {
    	Set<Shift> shifts = new HashSet<Shift>();
        Schedule schedule = scheduleService.createSchedule(shifts, systemService.getSystem(system.getBusinessName(), system.getBusinessPhoneNumber(), system.getBusinessAddress()));
        return convertToDto(schedule);
    }

    @PostMapping(value = "schedule/update_Schedule_info")
    public ScheduleDto updateSchedule(ScheduleDto scheduleDto, ShiftDto shift) {
        Schedule schedule = scheduleService.updateSchedule(scheduleService.findSchedule(scheduleDto.getid()),scheduleService.getShiftById(shift.getshiftID()));
        return convertToDto(schedule);
    }

    /*
    ----------------------------------------------------------------------------
    --------------------------------Shifts-----------------------------------
    ----------------------------------------------------------------------------
     */

    @GetMapping(value = "assistants")
    public List<AssistantDto> getAllAssistants() {
        List<Assistant> assistantList = accountService.getAllAssistants();
        return convertToDtoListForAssistant(assistantList);
    }

    @PostMapping(value = "assistants/create_to_most_recent_system")
    public AssistantDto createAssistantToMostRecentSystem(@RequestBody AssistantDto a) {
        Assistant assistant = accountService.createAssistant(a.getUsername(), a.getPassword(), a.getName(), systemService.getMostRecentSystem());
        return convertToDto(assistant);
    }

    @PostMapping(value = "assistants/update_info")
    public AssistantDto updateOwnerInfo(@RequestParam String newUsername, @RequestParam String newPassword, @RequestParam String newName, @RequestBody AssistantDto o) {
        Assistant newAssistant = (Assistant) accountService.updateUserInformation(accountService.getOwner(o.getUsername()), newUsername, newPassword, newName);
        return convertToDto(newAssistant);
    }


	private ShiftDto convertToDto(Shift shift) {
		ScheduleDto sto = new ScheduleDto(shift.getSchedule().getId(),convertToDto(shift.getSchedule().getRepairShopManagementSystem()));
		return new ShiftDto(shift.getDate(), shift.getStartTime(), shift.getEndTime(),
				convertToDto(shift.getAssistant()), convertToDto(shift.getAppointment()),
				sto,shift.getShiftId());
	}
	
	private AppointmentDto convertToDto(Appointment appointment) {
		List<Assistant> ass=new ArrayList<>();
		for(Assistant a:appointment.getService().getAssistant()) {
			ass.add(a);
		}
		ServiceDto sto=new ServiceDto(appointment.getService().getServiceType(),convertToDtoListForAssistant(ass));
		
		
		return new AppointmentDto();
	}

	private List<ShiftDto> convertToDtoListForShift(Set<Shift> shifts) {
		List<ShiftDto> result = new ArrayList<>();
		for (Shift s : shifts) {
			result.add(convertToDto(s));
		}
		return result;
	}
	private ScheduleDto convertToDto(Schedule schedule) {

		return new ScheduleDto(schedule.getId(),convertToDtoListForShift(schedule.getTimeSlot()),
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
