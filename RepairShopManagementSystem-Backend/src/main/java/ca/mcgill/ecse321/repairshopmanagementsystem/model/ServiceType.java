package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public enum ServiceType {
    ;

    private int id;

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
