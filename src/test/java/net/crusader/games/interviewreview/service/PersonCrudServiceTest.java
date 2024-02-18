package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        verify(personRepository).findById(1L);
        assertEquals(person, actual);
    }

    /**
     * This is an awkward test.  The method we are testing is: deletePerson(Long id);
     *
     * We want to retrieve the record with ID 1L then set its active field to false.
     *
     * We are using a mock repository, so there are no records to access.  We have to
     * simulate that an active record of id 1L exists and that we disable it.
     *
     */
    @Test
    public void delete_savesDisabledPerson(){
        // Arrange
        Person personSavedInDatabase = new Person();
        personSavedInDatabase.setId(1L);
        personSavedInDatabase.setFirstName("John");
        personSavedInDatabase.setLastName("Connor");
        personSavedInDatabase.setBirthday(LocalDate.of(1985, 2, 28));
        personSavedInDatabase.setPhoneNumber("555-5555");
        personSavedInDatabase.setActive(true);
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(personSavedInDatabase));

        Person disabledPerson = new Person();
        disabledPerson.setId(1L);
        disabledPerson.setFirstName("John");
        disabledPerson.setLastName("Connor");
        disabledPerson.setBirthday(LocalDate.of(1985, 2, 28));
        disabledPerson.setPhoneNumber("555-5555");
        disabledPerson.setActive(false);

        // Act
        personCrudService.deletePerson(1L);

        // Assert
        // We want to assert that we saved a disabled version of the Person in the database.
        verify(personRepository).save(disabledPerson);
    }

    /**
     * Although this test seems redundant, it also ensures that no exception is thrown
     * (which would halt execution flow) during the negative case when
     * the record does not exist.
     */
    @Test
    public void delete_deletingRecordThatDoesNotExistShouldNotSaveAnything(){
        // Arrange
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        personCrudService.deletePerson(1L);

        // Assert
        verify(personRepository, times(0)).save(any());
    }
}