package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PersonCrudServiceIntegrationTest {
    @Autowired
    private PersonCrudService personCrudService;

    @Autowired
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
        person = personRepository.save(person);

        Person actual = personCrudService.getPersonById(1L);

        assertEquals(person, actual);
    }
}