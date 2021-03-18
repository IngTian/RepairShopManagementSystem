package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

public class SpaceDto {

    private RepairShopManagementSystemDto system;
    private Integer id;
    private Integer maxWeightLoad;

    public SpaceDto() {
    }

    public SpaceDto(Integer id, Integer maxWeightLoad){
        this.id = id;
        this.maxWeightLoad = maxWeightLoad;
    }

    public SpaceDto(Integer id, Integer maxWeightLoad, RepairShopManagementSystemDto system) {
        this.system = system;
        this.id = id;
        this.maxWeightLoad = maxWeightLoad;
    }

    public SpaceDto(Integer id, RepairShopManagementSystemDto system) {
        this.system = system;
        this.id = id;
        this.maxWeightLoad = 2000;
    }

    public Integer getSpaceId() {
        return id;
    }

    public Integer getMaxWeightLoad() {
        return maxWeightLoad;
    }

    public RepairShopManagementSystemDto getRepairShopManagementSystem() {
        return system;
    }
}