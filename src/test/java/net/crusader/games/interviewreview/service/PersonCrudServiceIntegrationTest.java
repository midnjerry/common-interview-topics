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

/**
 * This is an example of an integration test.  You can tell
 * because this test has annotation @SpringBootTest.  This annotation
 * loads the entire Spring framework.  We use
 * @Autowired to retrieve the PersonCrudService and
 * PersonRepository objects from memory, so we can use them.
 *
 * This test takes a few seconds to load.  You need a VERY
 * GOOD REASON to execute these types of tests!
 */
@SpringBootTest
class PersonCrudServiceIntegrationTest {
    @Autowired
    private PersonCrudService personCrudService;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Although this is a valid integration test, it takes a few seconds to execute.  You can test the same
     * functionality using vanilla Java, but much faster... milliseconds compared to seconds.  This test
     * should be converted to a unit test, refer to: {@link net.crusader.games.interviewreview.service.PersonCrudServiceTest#getPerson_throwsException()}
     */
    @Test
    public void getPerson_throwsException(){
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            personCrudService.getPersonById(5L);
        });

        assertEquals("Person with 5 is not found.", exception.getMessage());
    }


    /**
     * Although this is a valid integration test, it takes a few seconds to execute.  You can test the same
     * functionality using vanilla Java, but much faster... milliseconds compared to seconds.  This test
     * should be converted to a unit test, refer to: {@link net.crusader.games.interviewreview.service.PersonCrudServiceTest#getPerson_returnsPerson()}
     */
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