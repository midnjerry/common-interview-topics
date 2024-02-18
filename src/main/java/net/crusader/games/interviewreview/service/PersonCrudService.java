package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.controller.PersonController;
import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonCrudService {
    private PersonRepository personRepository;

    @Autowired
    public PersonCrudService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    public Person getPersonById(Long id){
        Optional<Person> optional = this.personRepository.findById(id);
        if (optional.isEmpty()){
            throw new ResourceNotFoundException("Person with " + id + " is not found.");
        }
        return optional.get();
    }


    public Person partialUpdatePerson(Long id, PersonDto body) {
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Person with " + id + " is not found.");
        }
        Person savedInDatabase = optional.get();

        if (body.getFirstName() != null) {
            savedInDatabase.setFirstName(body.getFirstName());
        }

        if (body.getLastName() != null) {
            savedInDatabase.setLastName(body.getLastName());
        }

        if (body.getBirthday() != null) {
            savedInDatabase.setBirthday(body.getBirthday());
        }

        if (body.getMiddleName() != null) {
            savedInDatabase.setMiddleName(body.getMiddleName());
        }

        if (body.getPhoneNumber() != null) {
            savedInDatabase.setPhoneNumber(body.getPhoneNumber());
        }

        Person result = personRepository.save(savedInDatabase);
        return result;
    }

    /**
     * DELETE
     *
     * We are implementing a soft-delete.  So deactivate the record instead of deleting it
     * All other queries must be aware of this status and should search for all records
     * where active is true.
     *
     * @param id id of record to "delete"
     */
    public void deletePerson(Long id) {
        Optional<Person> personOptional = this.personRepository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setActive(false);
            this.personRepository.save(person);
        }

        // This is how you would truly delete a record
        // this.personRepository.deleteById(id);
    }

    public Person replacePerson(Long id, PersonDto body) {
        return null;
    }

    public Person createPerson(PersonDto body) {
        return null;
    }

    public List<Person> filterPeople(String firstName, String lastName) {
        return personRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndActiveTrue(firstName, lastName);
    }
}
