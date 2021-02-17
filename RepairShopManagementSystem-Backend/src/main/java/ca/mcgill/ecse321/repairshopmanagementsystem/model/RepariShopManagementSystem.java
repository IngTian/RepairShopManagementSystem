package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class RepariShopManagementSystem {
    private Integer id;

    public void setId(Integer value) {
        this.id = value;
    }

    @Id
    public Integer getId() {
        return this.id;
    }

    private Set<User> user;

    @OneToMany(mappedBy = "repariShopManagementSystem", cascade = {CascadeType.ALL})
    public Set<User> getUser() {
        return this.user;
    }

    public void setUser(Set<User> users) {
        this.user = users;
    }

}
