package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.*;

public class AssistantDto extends UserDto {
    private String username;
    private String password;
    private String name;
    private Set<ScheduleDto> schedules;
    private RepairShopManagementSystemDto system;
    private Set<ServiceDto> services;
    private Set<ShiftDto> shifts;

    public AssistantDto() {
    }

    public AssistantDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
        this.schedules = system.getSchedules();
        this.services = new HashSet<>();
        this.shifts = new HashSet<>();
    }

    public AssistantDto(String username, String password, String name, Set<ScheduleDto> schedules, RepairShopManagementSystemDto system, Set<ServiceDto> services, Set<ShiftDto> shifts) {
        super(username, password, name, system);
        this.schedules = schedules;
        this.services = services;
        this.shifts = shifts;
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

    public Set<ScheduleDto> getSchedules() {
        return schedules;
    }

    public RepairShopManagementSystemDto getSystem() {
        return system;
    }

    public Set<ServiceDto> getServices() {
        return services;
    }

    public Set<ShiftDto> getShifts() {
        return shifts;
    }
}
