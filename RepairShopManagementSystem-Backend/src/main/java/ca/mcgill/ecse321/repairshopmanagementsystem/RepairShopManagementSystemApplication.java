package ca.mcgill.ecse321.repairshopmanagementsystem;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.RepairShopManagementSystemRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.Owner;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepariShopManagementSystem;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class RepairShopManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairShopManagementSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(RepairShopManagementSystemRepository repo){
        return (args) -> {
            RepariShopManagementSystem a = new RepariShopManagementSystem();
            a.setId(10);
            Set<User> users = new HashSet<>();
            Owner b = new Owner();
            b.setRepariShopManagementSystem(a);
            users.add(b);
            a.setUser(users);
            repo.save(a);
        };
    }

}
