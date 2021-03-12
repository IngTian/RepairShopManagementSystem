package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.ShiftDto;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping(value = "create")
    public ShiftDto createAShift(@RequestParam Integer employeeId, @RequestBody ShiftDto shiftInfo) {
        return null;
    }

    @PostMapping(value = "cancel")
    public ShiftDto cancelShift(@RequestParam Integer shiftId) {
        return null;
    }

    @PostMapping(value = "change")
    public ShiftDto changeShift(@RequestParam Integer shiftId, @RequestBody ShiftDto newShiftInfo) {
        return null;
    }

}
