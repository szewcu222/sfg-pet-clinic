package guru.springframework.sfgpetclinic.formatters;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String s, Locale locale) throws ParseException {
        Collection<PetType> foundPetTypes = petTypeService.findAll();
        for (PetType type : foundPetTypes) {
            if (s.equals(type.getName())) {
                return type;
            }
        }
        throw new ParseException("type not found: " + s, 0);
    }


}
