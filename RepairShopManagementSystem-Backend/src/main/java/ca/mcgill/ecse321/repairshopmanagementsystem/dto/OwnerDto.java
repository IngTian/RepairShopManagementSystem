package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.Set;

public class OwnerDto extends UserDto {
    private String username;
    private String password;
    private String name;

    public OwnerDto() {

    }

    public OwnerDto(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public OwnerDto(String username, String password, String name, RepairShopManagementSystemDto system) {
        super(username, password, name, system);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}
