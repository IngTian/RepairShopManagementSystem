package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.sql.Date;
import java.sql.Time;

public class ShiftDto {
    private AppointmentDto appointment;
    private ScheduleDto schedule;
    private Date date;
    private Time startTime;
    private Time endTime;
    private RepairShopManagementSystemDto system;
    private AssistantDto assistant;

    public ShiftDto() {
    }

    public ShiftDto(ScheduleDto schedule, Date date, Time startTime, Time endTime, RepairShopManagementSystemDto system, AssistantDto assistant) {
        this.schedule = schedule;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.system = system;
        this.assistant = assistant;
    }

    public ShiftDto(Date date, Time startTime, Time endTime, AssistantDto assistant, AppointmentDto appointment, ScheduleDto schedule, RepairShopManagementSystemDto system) {
        this.appointment = appointment;
        this.system = system;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.schedule = schedule;
        this.assistant = assistant;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public Date getDate() {
        return date;
    }

    public AssistantDto getAssistant() {
        return assistant;
    }

    public ScheduleDto getSchedule() {
        return schedule;
    }

    public RepairShopManagementSystemDto getRepairShopManagementSystem() {
        return system;
    }

    public AppointmentDto getAppointment() {
        return appointment;
    }


}
