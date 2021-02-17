package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class TimeSlot {

    private int id;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Date date;

    private void setDate(Date value) {
        this.date = value;
    }

    private Date getDate() {
        return this.date;
    }

    private Time startTime;

    private void setStartTime(Time value) {
        this.startTime = value;
    }

    private Time getStartTime() {
        return this.startTime;
    }

    private Time endTime;

    private void setEndTime(Time value) {
        this.endTime = value;
    }

    private Time getEndTime() {
        return this.endTime;
    }

    private Appointment appointment;

    @OneToOne(mappedBy = "timeSlot")
    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    private Schedule schedule;

    @ManyToOne(optional = false)
    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

}
