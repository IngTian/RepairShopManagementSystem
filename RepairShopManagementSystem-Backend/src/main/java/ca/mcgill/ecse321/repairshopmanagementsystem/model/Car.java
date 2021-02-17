package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Car {
    private String plateNo;

    private void setPlateNo(String value) {
        this.plateNo = value;
    }

    @Id
    private String getPlateNo() {
        return this.plateNo;
    }

    private String model;

    private void setModel(String value) {
        this.model = value;
    }

    private String getModel() {
        return this.model;
    }

    private String year;

    private void setYear(String value) {
        this.year = value;
    }

    private String getYear() {
        return this.year;
    }

    private String manufacturer;

    private void setManufacturer(String value) {
        this.manufacturer = value;
    }

    private String getManufacturer() {
        return this.manufacturer;
    }

    private Customer customer;

    @ManyToOne(optional = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Set<Appointment> appointment;

    @ManyToMany
    public Set<Appointment> getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Set<Appointment> appointments) {
        this.appointment = appointments;
    }

}
