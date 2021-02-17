package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
public class Customer extends User {
    private String phoneNo;

    private void setPhoneNo(String value) {
        this.phoneNo = value;
    }

    private String getPhoneNo() {
        return this.phoneNo;
    }

    private String homeAddress;

    private void setHomeAddress(String value) {
        this.homeAddress = value;
    }

    private String getHomeAddress() {
        return this.homeAddress;
    }

    private String email;

    private void setEmail(String value) {
        this.email = value;
    }

    private String getEmail() {
        return this.email;
    }

    private Set<Car> car;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    public Set<Car> getCar() {
        return this.car;
    }

    public void setCar(Set<Car> cars) {
        this.car = cars;
    }

    private Set<Appointment> appointment;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    public Set<Appointment> getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Set<Appointment> appointments) {
        this.appointment = appointments;
    }

}
