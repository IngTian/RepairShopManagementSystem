package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.sql.Date;
import java.sql.Time;

public class ShiftDto {
    private AppointmentDto appointment;
    private ScheduleDto schedule;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Integer shiftId;

    private AssistantDto assistant;

    public ShiftDto() {
    }

    public ShiftDto(Date date, Time startTime, Time endTime){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShiftDto(Date date, Time startTime, Time endTime, AssistantDto assistant){
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assistant = assistant;
    }

    public ShiftDto(ScheduleDto schedule, Date date, Time startTime, Time endTime, AssistantDto assistant, Integer shiftID) {
        this.schedule = schedule;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftId = shiftID;
        this.assistant = assistant;
    }

    public ShiftDto(Date date, Time startTime, Time endTime, AssistantDto assistant, AppointmentDto appointment, ScheduleDto schedule, Integer ShiftID) {
        this.appointment = appointment;
        this.shiftId = ShiftID;
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


    public AppointmentDto getAppointment() {
        return appointment;
    }

    public Integer getShiftID() {
        return this.shiftId;
    }

}