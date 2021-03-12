package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleDto {
    private List<ShiftDto> shifts;
    
    private RepairShopManagementSystemDto repairShopManagementSystem;

    public ScheduleDto() {
    }

    public ScheduleDto(RepairShopManagementSystemDto system) {
        
        this.shifts = new ArrayList<>();
        this.repairShopManagementSystem = system;
    }

    public ScheduleDto(List<ShiftDto> timeSlot, RepairShopManagementSystemDto repairShopManagementSystem) {
        this.shifts = timeSlot;
       
        this.repairShopManagementSystem = repairShopManagementSystem;
    }

    public List<ShiftDto> getShifts() {
        return this.shifts;
    }

   

    public RepairShopManagementSystemDto getRepairShopManagementSystem() {
        return this.repairShopManagementSystem;
    }
}
