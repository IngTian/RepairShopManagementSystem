package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.HashSet;
import java.util.Set;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;

public class CustomerDto extends UserDto {
    private String username;
    private String password;
    private String name;
    private String phoneNo;
    private String homeAddress;
    private String email;
    private Set<CarDto> Cars;
    private Set<AppointmentDto> appointments;
    private RepairShopManagementSystemDto repairShopManagementSystemDto;
    private Set<ScheduleDto> schedule;

    public CustomerDto(){

    }

    public CustomerDto(String username, String password, String name, String phoneNo, String homeAddress, String email,
                       Set<CarDto> Cars, Set<AppointmentDto> appointments, RepairShopManagementSystemDto repairShopManagementSystemDto, Set<ScheduleDto> schedule) {
        super(username, password, name, schedule, repairShopManagementSystemDto);
        this.homeAddress = homeAddress;
        this.email = email;
        this.Cars = Cars;
        this.appointments = appointments;
        this.phoneNo = phoneNo;
    }

    public CustomerDto(RepairShopManagementSystemDto repairShopManagementSystemDto, Set<ScheduleDto> schedule, String username, String password, String name, String phoneNo, String homeAddress, String email,
                       Set<CarDto> Cars) {
        super(username, password, name, schedule, repairShopManagementSystemDto);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
        this.Cars = Cars;
        this.appointments = new HashSet<>();

    }

    public CustomerDto(RepairShopManagementSystemDto repairShopManagementSystemDto, Set<ScheduleDto> schedule, String username, String password, String name, String phoneNo, String homeAddress, String email
    ) {

        super(username, password, name, schedule, repairShopManagementSystemDto);
        this.phoneNo = phoneNo;
        this.homeAddress = homeAddress;
        this.email = email;
        this.Cars = new HashSet<>();
        this.appointments = new HashSet<>();

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getEmail() {
        return email;
    }

    public Set<CarDto> getCars() {
        return Cars;
    }

    public Set<AppointmentDto> getAppointments() {
        return appointments;
    }

    public Set<ScheduleDto> getSchedule() {
        return schedule;
    }


    public RepairShopManagementSystemDto getRepairShopManagementSystemDto() {
        return repairShopManagementSystemDto;
    }

}
