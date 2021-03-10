package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.HashSet;
import java.util.Set;

public abstract class UserDto {
    private String username;
    private String password;
    private String name;
    private Set<ScheduleDto> schedule;
    private RepairShopManagementSystemDto system;

    public UserDto() {

    }

    public UserDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.system = system;
        this.schedule = new HashSet<>();
    }

    public UserDto(String username, String password, String name, Set<ScheduleDto> schedule, RepairShopManagementSystemDto repairShopManagementSystemDto) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.schedule = schedule;
        this.system = repairShopManagementSystemDto;

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


    public Set<ScheduleDto> getSchedule() {
        return schedule;
    }


    public RepairShopManagementSystemDto getSystem() {
        return system;
    }


}
