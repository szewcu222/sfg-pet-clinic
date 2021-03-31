package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final long OWNER_ID = 1L;

    private static final String LAST_NAME = "Smith";
    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    Owner globOwner;

    @BeforeEach
    void setUp() {
        globOwner = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        //given
        when(ownerRepository.findByLastName(any())).thenReturn(globOwner);    // mock ownerRepository not ownerService
        //when
        Owner smith = ownerService.findByLastName(LAST_NAME);
        //then
        assertEquals(LAST_NAME,smith.getLastName());
        verify(ownerRepository).findByLastName(LAST_NAME);
    }

    @Test
    void findAll() {
        // given
        Owner owner2 = Owner.builder().id(2L).build();
        HashSet<Owner> returnOwners = new HashSet(){{add(globOwner); add(owner2);}};
        when(ownerRepository.findAll()).thenReturn(returnOwners);
        //when
        Set<Owner> foundOwners = ownerService.findAll();
        // then
        assertNotNull(foundOwners);
        assertEquals(returnOwners.size(), foundOwners.size());
    }

    @Test
    void findById() {
        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(globOwner));
        // when
        Owner foundOwner = ownerService.findById(OWNER_ID);
        // then
        assertNotNull(foundOwner);
        assertEquals(OWNER_ID, foundOwner.getId());
    }

    @Test
    void findByIdNotFound() {
        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        Owner foundOwner = ownerService.findById(OWNER_ID);
        // then
        assertNull(foundOwner);
    }

    @Test
    void saveExistingId() {
        // given
        when(ownerRepository.save(any())).thenReturn(globOwner);
        // when
        Owner savedOwner = ownerService.save(globOwner);
        // then
        assertNotNull(savedOwner);
        assertEquals(globOwner.getId(), savedOwner.getId());
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void saveNoId() {
        // given
        Owner ownerNoId = Owner.builder().build();
        when(ownerRepository.save(any())).thenReturn(ownerNoId);
        // when
        Owner savedOwner = ownerService.save(ownerNoId);
        // then
        assertNotNull(savedOwner);
        verify(ownerRepository).save(any(Owner.class));
    }

    @Test
    void delete() {
        // given
        ownerService.delete(globOwner);
        // then - only verify if method was called
        // defauld times is 1
        verify(ownerRepository, times(1)).delete(any());

    }

    @Test
    void deleteById() {
        ownerService.deleteById(globOwner.getId());

        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}