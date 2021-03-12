package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.*;

public class AssistantDto extends UserDto {
    private String username;
    private String password;
    private String name;
    private Set<ServiceDto> services;
    private Set<ShiftDto> shifts;

    public AssistantDto() {
    }

    public AssistantDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
        this.services = new HashSet<>();
        this.shifts = new HashSet<>();
    }

    public AssistantDto(String username, String password, String name, RepairShopManagementSystemDto system, Set<ServiceDto> services, Set<ShiftDto> shifts) {
        super(username, password, name, system);
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

    public Set<ServiceDto> getServices() {
        return services;
    }

    public Set<ShiftDto> getShifts() {
        return shifts;
    }
}
