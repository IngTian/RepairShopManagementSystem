package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bill {
    private Integer billNo;

    private void setBillNo(Integer value) {
        this.billNo = value;
    }

    @Id
    private Integer getBillNo() {
        return this.billNo;
    }

    private Integer price;

    private void setPrice(Integer value) {
        this.price = value;
    }

    private Integer getPrice() {
        return this.price;
    }

    private Appointment appointment;

    @ManyToOne(optional = false)
    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

}
