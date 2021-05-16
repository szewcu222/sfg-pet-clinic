package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);
    Collection<Owner> findAllByLastNameLike(String lastName);

    List<Owner> findByLastNameIgnoreCaseAllIgnoreCase(String lastName);

    Collection<Owner> findByLastNameContainingIgnoreCase(String lastName);

    List<Owner> findByLastNameIsLike(String lastName);


}
