package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.RepairShopManagementSystemApplication;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepariShopManagementSystem;
import org.springframework.data.repository.CrudRepository;

public interface RepairShopManagementSystemRepository extends CrudRepository<RepariShopManagementSystem, Integer> {
}
