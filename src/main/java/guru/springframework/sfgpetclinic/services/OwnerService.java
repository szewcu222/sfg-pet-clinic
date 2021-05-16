package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.Collection;
import java.util.List;


public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
    List<Owner> findByLastNameIgnoreCaseAllIgnoreCase(String lastName);
    Collection<Owner> findByLastNameContainingIgnoreCase(String lastName);
    Collection<Owner> findAllByLastNameLike(String lastName);
}
