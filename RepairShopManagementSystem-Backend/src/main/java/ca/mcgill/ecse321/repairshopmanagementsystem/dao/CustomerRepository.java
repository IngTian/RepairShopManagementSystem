package ca.mcgill.ecse321.repairshopmanagementsystem.dao;

import ca.mcgill.ecse321.repairshopmanagementsystem.model.Customer;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Schedule;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.User;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
  Customer findCustomerByUserId(Integer userId);
  
  Customer findCustomerByUsername(String username);
  
   Set<User> findCustomerBySchedule(Schedule schedule);
}