package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PersonCrudServiceWithFakeRepositoryIntegrationTest {
    @Autowired
    private PersonCrudService personCrudService;
    @MockBean
    private PersonRepository personRepository;

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