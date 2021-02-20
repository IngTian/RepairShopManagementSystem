package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Schedule {
    private Integer yearAndWeekNo;

    public void setYearAndWeekNo(Integer value) {
        this.yearAndWeekNo = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getYearAndWeekNo() {
        return this.yearAndWeekNo;
    }

    private Set<Shift> timeSlot;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.ALL})
    public Set<Shift> getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(Set<Shift> timeSlots) {
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

    private RepairShopManagementSystem repariShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepariShopManagementSystem() {
        return this.repariShopManagementSystem;
    }

    public void setRepariShopManagementSystem(RepairShopManagementSystem repariShopManagementSystem) {
        this.repariShopManagementSystem = repariShopManagementSystem;
    }

}
