package guru.springframework.sfgpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {

    private String address;
    private String city;
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets == null) { pets = new HashSet<>(); }
        this.pets = pets;
    }

    public Set<Pet> getPets() {
//        if (this.pets == null) {
//            this.pets = new HashSet<>();
//        }
        return pets;
    }

    public Pet getPet(String name) {
        return getPet(name, false);
    }

    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : this.getPets()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)){
                    return pet;
                }
            }
        }
        return null;
    }
}
