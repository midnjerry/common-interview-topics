package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class PersonCrudServiceTest {
    private PersonCrudService personCrudService;
    private PersonRepository personRepository;

    @BeforeEach
    public void runBeforeEachTest(){
        personRepository = Mockito.mock(PersonRepository.class);
        personCrudService = new PersonCrudService(personRepository);
    }


    @Test
    public void getPerson_throwsException(){
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personCrudService.getPersonById(5L);
        });

        assertEquals("Person with 5 is not found.", exception.getMessage());
    }

    @Test
    public void getPerson_returnsPerson(){
        Person person = new Person( );
        person.setFirstName("John");
        person.setLastName("Connor");
        person.setBirthday(LocalDate.of(1985, 2, 28));
        person.setPhoneNumber("555-5555");

        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        Person actual = personCrudService.getPersonById(1L);

        Mockito.verify(personRepository).findById(1L);
        assertEquals(person, actual);
    }
}