package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.Set;

public class OwnerDto extends UserDto {
    private String username;
    private String password;
    private String name;

    private Set<ScheduleDto> schedule;
    private RepairShopManagementSystemDto repairShopManagementSystemDto;

    public OwnerDto() {

    }

    public OwnerDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
    }

    public OwnerDto(String username, String password, String name, Set<ScheduleDto> schedule, RepairShopManagementSystemDto repairShopManagementSystemDto) {

        super(username, password, name, schedule, repairShopManagementSystemDto);
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


    public RepairShopManagementSystemDto getRepairShopManagementSystemDto() {
        return repairShopManagementSystemDto;
    }


}
