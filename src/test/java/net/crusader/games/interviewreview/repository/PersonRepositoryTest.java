package net.crusader.games.interviewreview.repository;

import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are writing an integration test for the repository,
 * so we can test the custom query methods.  There is no
 * need to test the standard query methods.
 * <p>
 * Notice how we are using @SpringBootTest to load the
 * Spring framework.  The Spring framework loads the
 * database configured in the application.yml for the
 * test profile.  If none is configured, it will attempt
 * to spin up an H2 database.
 *
 * We use @Autowired to pull the personRepository from memory.
 * We can then use the object for testing.
 */

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    Person person1, person2, person3, inactive;

    @BeforeEach
    public void setup() {
        // Clear the repository before each test
        personRepository.deleteAll();
        person1 = new Person(null, new PersonDto("Peter", "", "Parker", LocalDate.of(1990, 1, 1), "214-555-5555"));
        person2 = new Person(null, new PersonDto("Penny", "", "Parker", LocalDate.of(1980, 1, 1), "214-555-1234"));
        person3 = new Person(null, new PersonDto("Clark", "", "Kent", LocalDate.of(1970, 1, 1), "214-555-7777"));
        inactive = new Person(null, new PersonDto("I", "Am", "Deleted", LocalDate.of(1975, 1, 1), "214-333-1234"));
        inactive.setActive(false);

        // Save entries and put them back into variables
        // They will now be populated with ids.
        person1 = personRepository.save(person1);
        person2 = personRepository.save(person2);
        person3 = personRepository.save(person3);
        inactive = personRepository.save(inactive);
    }

    /**
     * You could argue that we do not have to write a test for this, since we're "testing the framework" and not our code.
     *
     * However, with custom JPA Queries, I find it very useful to write the test to ensure I used
     * the appropriate syntax for the query.  For the most part, it's not the test that fails, but the compiler
     * that fails.
     *
     * For example try to declare public findByFirstNameContainingAndByLastNameContainingAndByActiveTrue(String firstName, String lastName) in PersonRepository
     * IntelliJ will tell you that it's valid code, but when you run it, the compiler will give you an error.
     *
     * This is because the method is invalid syntax.
     */
    @Test
    public void findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue_returnsAllActiveRecords() {
        List<Person> personList = personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue("", "");
        assertEquals(3, personList.size());
    }

    @Test
    public void findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue_returnsRecordsThatStartWithP() {
        List<Person> personList = personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue("P", "");
        assertEquals(2, personList.size());
    }

    @Test
    public void findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue_returnsRecordsWithLastNameParker() {
        List<Person> personList = personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue("", "Parker");
        assertEquals(2, personList.size());
    }

    /**
     * Another example of how a test can help you keep track of edge cases.  I wrote this test to see if
     * personRepository.findByFirstNameContainingAndLastNameContainingAndActiveTrue("p", "") will return Peter Parker
     * and Penny Parker with a lowercase 'p'.  It doesn't.  After some research, I found that you have to add `IgnoreCase`
     * after Containing if you want a case-insensitive search.
     *
     * This test seems to be redundant, but it's the tests themselves that help me formulate the appropriate syntax
     * for the method name.
     */
    @Test
    public void findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue_returnsRecordsThatStartWithLowerCaseP() {
        List<Person> personList = personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue("p", "");
        assertEquals(2, personList.size());
    }

    @Test
    public void findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue_returnsRecordsThatStartWithPeter() {
        List<Person> personList = personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue("Peter", "");
        assertEquals(1, personList.size());
    }

}