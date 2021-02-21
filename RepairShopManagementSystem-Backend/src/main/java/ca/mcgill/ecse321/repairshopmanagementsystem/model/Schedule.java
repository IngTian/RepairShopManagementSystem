package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Schedule {
    private Integer id;

    public void setId(Integer value) {
        this.id = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return this.id;
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

    private RepairShopManagementSystem repairShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }

    public void setRepairShopManagementSystem(RepairShopManagementSystem repariShopManagementSystem) {
        this.repairShopManagementSystem = repariShopManagementSystem;
    }

}
