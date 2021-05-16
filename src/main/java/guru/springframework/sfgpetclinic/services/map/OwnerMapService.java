package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Profile({"default","map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    @Autowired
    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return this.findAll().stream()
                .filter(owner -> lastName.equalsIgnoreCase(owner.getLastName()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Owner> findByLastNameIgnoreCaseAllIgnoreCase(String lastName) {
        return null;
    }

    @Override
    public Collection<Owner> findByLastNameContainingIgnoreCase(String lastName) {
        return null;
    }

    @Override
    public Collection<Owner> findAllByLastNameLike(String lastName) {
        // todo - implement
        return null;
    }

    @Override
    public Owner save(Owner owner) {
        if(owner != null){
//            if(owner.getPets().size() > 0){
//                owner.getPets().forEach(pet -> {
//                    if(pet.getPetType().getId() != null) {
//                        pet.setPetType(petTypeService.save(pet.getPetType()));
//                    } else {
//                        throw new RuntimeException("Pet type is required");
//                    }
//                    if(pet.getId() == null) {
//                        Pet savedPet = petService.save(pet);
//                        pet.setId(savedPet.getId());
//                    }
//                });
//            }
            return super.save(owner);
        } else {
            return null;
        }


    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }


}
