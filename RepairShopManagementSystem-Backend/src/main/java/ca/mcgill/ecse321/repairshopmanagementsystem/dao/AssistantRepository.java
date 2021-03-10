package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Assistant;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.User;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface AssistantRepository extends CrudRepository<Assistant, Integer> {
  Assistant findAssistantByUserId(Integer userId);
  
  Assistant findAssistantByUsername(String username);
  
   Set<User> findAssistantBySchedule(Schedule schedule);}