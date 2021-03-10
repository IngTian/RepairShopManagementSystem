package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Owner;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.User;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {
  Owner findOwnerByUserId(Integer ID);
  
   Owner findOwnerByUsername(String username);
   
   Set<User> findOwnerBySchedule(Schedule schedule);
}