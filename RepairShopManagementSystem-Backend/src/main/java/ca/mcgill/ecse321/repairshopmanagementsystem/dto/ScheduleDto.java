package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.HashSet;
import java.util.Set;

public class ScheduleDto {
    private Set<ShiftDto> shifts;
    private Set<UserDto> user;
    private RepairShopManagementSystemDto repairShopManagementSystem;

    public ScheduleDto() {
    }

    public ScheduleDto(RepairShopManagementSystemDto system) {
        this.user = new HashSet<>();
        this.shifts = new HashSet<>();
        this.repairShopManagementSystem = system;
    }

    public ScheduleDto(Set<ShiftDto> timeSlot, Set<UserDto> user, RepairShopManagementSystemDto repairShopManagementSystem) {
        this.shifts = timeSlot;
        this.user = user;
        this.repairShopManagementSystem = repairShopManagementSystem;
    }

    public Set<ShiftDto> getShifts() {
        return this.shifts;
    }

    public Set<UserDto> getUser() {
        return user;
    }

    public RepairShopManagementSystemDto getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }
}
