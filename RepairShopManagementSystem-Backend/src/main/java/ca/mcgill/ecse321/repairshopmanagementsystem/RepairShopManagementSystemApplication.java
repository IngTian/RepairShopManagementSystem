package ca.mcgill.ecse321.repairshopmanagementsystem;

import ca.mcgill.ecse321.repairshopmanagementsystem.dao.RepairShopManagementSystemRepository;
import ca.mcgill.ecse321.repairshopmanagementsystem.model.RepairShopManagementSystem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class RepairShopManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairShopManagementSystemApplication.class, args);
    }

    /**
     * An example of how to create an object.
     *
     * @param repo
     * @return
     */
    @Bean
    public CommandLineRunner demo(RepairShopManagementSystemRepository repo) {
        return (args) -> {
            RepairShopManagementSystem aSystem = new RepairShopManagementSystem();
            aSystem.setBusinessAddress("Avenue des Champs-Élysées, Paris");
            aSystem.setBusinessName("Louis Vuitton");
            aSystem.setBusinessPhoneNumber("00-12-34-56-78");
            repo.save(aSystem);
        };
    }

}
