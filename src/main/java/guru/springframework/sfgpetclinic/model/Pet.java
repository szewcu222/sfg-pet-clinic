package guru.springframework.sfgpetclinic.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    private String name;
    private LocalDate birthDate;

    @ManyToOne()
    @JoinColumn(name = "type_id")   // because PetType table name changed to "types"
    private PetType petType;

    @ManyToOne
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet") //if Pet is deleted, all visit history will be deleted as well
    private Set<Visit> visits = new HashSet<>();

}
