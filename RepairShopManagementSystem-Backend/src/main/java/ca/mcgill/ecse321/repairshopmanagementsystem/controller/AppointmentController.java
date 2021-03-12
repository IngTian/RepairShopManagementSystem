package ca.mcgill.ecse321.repairshopmanagementsystem.controller;

import java.util.*;

import ca.mcgill.ecse321.repairshopmanagementsystem.dto.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.service.*;
import ca.mcgill.ecse321.repairshopmanagementsystem.utils.Util;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping(value = "available_time")
    public List<ShiftDto> getAllAvailableTimeSlots() {
        return null;
    }

    @PostMapping(value = "payment")
    public BillDto makePayment(@RequestParam Integer billNo) {
        return null;
    }

    @PostMapping(value = "book")
    public AppointmentDto makeAnAppointment(HttpServletRequest request) {
        JSONObject info = Util.getJSON(request);
        String username = info.getString("username");
        String plateNo = info.getString("plateNo");
        String serviceType = info.getString("serviceType");
        String shiftId = info.getString("shiftId");

        return null;
    }

    @PostMapping(value = "change")
    public AppointmentDto changeAnAppointment(HttpServletRequest request) {
        JSONObject info = Util.getJSON(request);
        Integer appointmentId = info.getInt("appointmentId");
        String newPlateNo = info.getString("newPlateNo");
        String newServiceType = info.getString("newServiceType");
        String newShiftId = info.getString("newShiftId");

        return null;
    }

    @PostMapping(value = "cancel")
    public AppointmentDto cancelAnAppointment(@RequestParam Integer appointmentId) {
        return null;
    }

}
