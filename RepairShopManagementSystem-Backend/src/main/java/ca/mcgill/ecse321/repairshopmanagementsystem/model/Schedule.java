package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Schedule {
    private Integer yearAndWeekNo;

    private void setYearAndWeekNo(Integer value) {
        this.yearAndWeekNo = value;
    }

    @Id
    private Integer getYearAndWeekNo() {
        return this.yearAndWeekNo;
    }

    private Set<TimeSlot> timeSlot;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.ALL})
    public Set<TimeSlot> getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(Set<TimeSlot> timeSlots) {
        this.timeSlot = timeSlots;
    }

    private Set<User> user;

    @ManyToMany(mappedBy = "schedule")
    public Set<User> getUser() {
        return this.user;
    }

    public void setUser(Set<User> users) {
        this.user = users;
    }

}
