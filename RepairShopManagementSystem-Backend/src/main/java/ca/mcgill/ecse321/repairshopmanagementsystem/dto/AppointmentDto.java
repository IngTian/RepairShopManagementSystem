package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.Set;

public class AppointmentDto {
    private Integer appointmentId;
    private Set<BillDto> bill;
    private ServiceDto service;
    private ShiftDto shift;
    private CustomerDto customer;
    private CarDto car;
    private SpaceDto space;

    public AppointmentDto() {

    }

    public AppointmentDto(Integer appointmentId, Set<BillDto> bill, ServiceDto service, ShiftDto shift, CustomerDto customer, CarDto car, SpaceDto space) {
        this.appointmentId = appointmentId;
        this.car = car;
        this.bill = bill;
        this.space = space;
        this.service = service;
        this.shift = shift;
        this.customer = customer;
    }
    public AppointmentDto(Integer appointmentId, Set<BillDto> bill, ShiftDto shift, CustomerDto customer, CarDto car, SpaceDto space) {
        this.appointmentId = appointmentId;
        this.car = car;
        this.bill = bill;
        this.space = space;
       
        this.shift = shift;
        this.customer = customer;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public Set<BillDto> getBill() {
        return bill;
    }

    public ServiceDto getService() {
        return service;
    }

    public ShiftDto getShift() {
        return shift;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public CarDto getCar() {
        return car;
    }

    public SpaceDto getSpace() {
        return space;
    }

}
