package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();

        petTypes = new HashSet<>();
        petTypes.add(PetType.builder().name("Dog").build());
        petTypes.add(PetType.builder().name("Cat").build());

        mockMvc = MockMvcBuilders
                .standaloneSetup(petController)
                .build();
    }


    @Test
    void populatePetTypes() {
        // given
        when(petTypeService.findAll()).thenReturn(petTypes);
        // when
        Collection<PetType> populatedPetTypes = petController.populatePetTypes();
        // then
        assertEquals(populatedPetTypes.size(), petTypes.size());
    }


    @Test
    void initCreationForm() throws Exception {
        //given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        //when
        //then
        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processCreationForm() throws Exception {
        // given
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        //then
        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }


    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

        mockMvc.perform(get("/owners/1/pets/2/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        // when(petService.findById(anyLong())).thenReturn(Pet.builder().id(2L).build());

        mockMvc.perform(post("/owners/1/pets/2/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }
}