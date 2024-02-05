package net.crusader.games.interviewreview.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.crusader.games.interviewreview.dto.GetPersonResponse;
import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import net.crusader.games.interviewreview.service.PersonCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class PersonController {
    private final PersonRepository personRepository;
    private final PersonCrudService personCrudService;

    public PersonController(PersonRepository personRepository, PersonCrudService personCrudService) {
        this.personRepository = personRepository;
        this.personCrudService = personCrudService;
    }

    /**
     * RESTful API basically is a CRUD for a resource
     *
     * C = Create
     * R = Read
     * U = Update
     * D = Delete
     */

    /**
     * FIRST ENDPOINT FOR RESTful API
     * GET RESOURCE BY ID
     *
     * @param id <- part of the path
     * @return 200 and Resource - OR, 404 not found
     */
    @GetMapping("/person/{id}")
    public GetPersonResponse getPerson(@PathVariable Long id, HttpServletResponse response) {
        GetPersonResponse personResponse = new GetPersonResponse();
        try {
            Person person = personCrudService.getPersonById(id);
            personResponse.setPerson(person);
            personResponse.setStatus(HttpStatus.OK.getReasonPhrase());
        } catch (ResourceNotFoundException e) {
            personResponse.setErrorMessage(e.getMessage());
            personResponse.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setStatus(404);
        }
        return personResponse;
    }

    // Aspect Orient Programming

    /**
     * GET ALL RECORDS - typically will accept query parameters to filter resources
     *
     * @param firstName <- example of query parameter
     * @param lastName  <- example of query parameter
     * @return all filtered records
     */
    @GetMapping("/person")
    public List<Person> getAll(@RequestParam(required = false, name = "firstName") String firstName, @RequestParam(required = false) String lastName) {
        return this.personRepository.findActiveRecords();
    }

    /**
     * CREATE A RESOURCE -
     */
    @PostMapping("/person")
    public Person createPerson(@RequestBody PersonDto body) {
        return personRepository.save(new Person(null, body));
    }


    @PutMapping("/person/{id}")
    public ResponseEntity<Person> replacePerson(@PathVariable("id") Long id, @RequestBody PersonDto body) {
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Person person = personRepository.save(new Person(id, body));

        return ResponseEntity.ok(person);
    }

    @PatchMapping("/person/{id}")
    public ResponseEntity<Person> updatePersonByField(@PathVariable("id") Long id, @RequestBody PersonDto body) {
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


    /**
     * DELETE A RESOURCE
     *
     * @param id <-- id of resource to delete
     *           even if the record does not exist - return 200
     */
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable Long id) {
        Optional<Person> personOptional = this.personRepository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setActive(false);
            this.personRepository.save(person);
        }
        // this.personRepository.deleteById(id);
    }


}
