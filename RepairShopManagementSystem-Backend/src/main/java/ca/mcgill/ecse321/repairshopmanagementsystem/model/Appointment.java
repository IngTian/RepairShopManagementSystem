package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Appointment {
    private Integer appointmentId;

    private void setAppointmentId(Integer value) {
        this.appointmentId = value;
    }

    @Id
    private Integer getAppointmentId() {
        return this.appointmentId;
    }

    private Set<Bill> bill;

    @OneToMany(mappedBy = "appointment", cascade = {CascadeType.ALL})
    public Set<Bill> getBill() {
        return this.bill;
    }

    public void setBill(Set<Bill> bills) {
        this.bill = bills;
    }

    private Service service;

    @OneToOne(mappedBy = "appointment", cascade = {CascadeType.ALL})
    public Service getService() {
        return this.service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    private TimeSlot timeSlot;

    @OneToOne(optional = false)
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    private Customer customer;

    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Set<Car> car;

    @ManyToMany(mappedBy = "appointment")
    public Set<Car> getCar() {
        return this.car;
    }

    public void setCar(Set<Car> cars) {
        this.car = cars;
    }

}
