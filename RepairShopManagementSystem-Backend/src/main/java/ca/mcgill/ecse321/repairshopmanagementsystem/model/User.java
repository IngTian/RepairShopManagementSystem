package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public abstract class User {
    private String username;

    private void setUsername(String value) {
        this.username = value;
    }

    private String getUsername() {
        return this.username;
    }

    private String password;

    private void setPassword(String value) {
        this.password = value;
    }

    private String getPassword() {
        return this.password;
    }

    private String name;

    private void setName(String value) {
        this.name = value;
    }

    private String getName() {
        return this.name;
    }

    private Set<Schedule> schedule;

    @ManyToMany
    public Set<Schedule> getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Set<Schedule> schedules) {
        this.schedule = schedules;
    }

    private RepariShopManagementSystem repariShopManagementSystem;

    @ManyToOne(optional = false)
    public RepariShopManagementSystem getRepariShopManagementSystem() {
        return this.repariShopManagementSystem;
    }

    public void setRepariShopManagementSystem(RepariShopManagementSystem repariShopManagementSystem) {
        this.repariShopManagementSystem = repariShopManagementSystem;
    }

    private Integer userId;

    private void setUserId(Integer value) {
        this.userId = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer getUserId() {
        return this.userId;
    }
}
