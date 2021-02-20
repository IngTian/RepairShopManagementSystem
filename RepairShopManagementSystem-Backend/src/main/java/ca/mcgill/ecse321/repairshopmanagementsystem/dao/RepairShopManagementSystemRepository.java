package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepairShopManagementSystem;
import org.springframework.data.repository.CrudRepository;

public interface RepairShopManagementSystemRepository extends CrudRepository<RepairShopManagementSystem, Integer> {
}
