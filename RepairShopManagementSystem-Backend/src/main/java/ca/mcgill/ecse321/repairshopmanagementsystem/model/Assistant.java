package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Assistant extends User {
    private Set<Service> service;

    @ManyToMany
    public Set<Service> getService() {
        return this.service;
    }

    public void setService(Set<Service> services) {
        this.service = services;
    }

}
