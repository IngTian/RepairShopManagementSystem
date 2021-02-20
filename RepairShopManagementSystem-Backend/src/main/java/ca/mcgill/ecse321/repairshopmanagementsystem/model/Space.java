package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Space {
    private Integer spaceId;

    public void setSpaceId(Integer value) {
        this.spaceId = value;
    }

    @Id
    public Integer getSpaceId() {
        return this.spaceId;
    }

    private RepairShopManagementSystem repariShopManagementSystem;

    @ManyToOne(optional = false)
    public RepairShopManagementSystem getRepariShopManagementSystem() {
        return this.repariShopManagementSystem;
    }

    public void setRepariShopManagementSystem(RepairShopManagementSystem repariShopManagementSystem) {
        this.repariShopManagementSystem = repariShopManagementSystem;
    }

    private Integer maxWeightLoad;

    public void setMaxWeightLoad(Integer value) {
        this.maxWeightLoad = value;
    }

    public Integer getMaxWeightLoad() {
        return this.maxWeightLoad;
    }
}
