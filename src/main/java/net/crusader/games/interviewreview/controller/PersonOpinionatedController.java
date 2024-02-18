package net.crusader.games.interviewreview.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.crusader.games.interviewreview.dto.GetPersonResponse;
import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.service.PersonCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class PersonOpinionatedController {
    private final PersonCrudService personCrudService;

    public PersonOpinionatedController(PersonCrudService personCrudService) {
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

    /**
     * GET ALL RECORDS - typically will accept query parameters to filter resources
     *
     * @param firstName <- example of query parameter
     * @param lastName  <- example of query parameter
     * @return all filtered records
     */
    @GetMapping("/person")
    public List<Person> getAll(@RequestParam(required = false, name = "firstName") String firstName, @RequestParam(required = false) String lastName) {
        return personCrudService.filterPeople(firstName, lastName);
    }

    /**
     * CREATE A RESOURCE -
     */
    @PostMapping("/person")
    public Person createPerson(@RequestBody PersonDto body) {
        return personCrudService.createPerson(body);
    }


    @PutMapping("/person/{id}")
    public ResponseEntity<Person> replacePerson(@PathVariable("id") Long id, @RequestBody PersonDto body) {
        return ResponseEntity.ok(personCrudService.replacePerson(id, body));
    }

    @PatchMapping("/person/{id}")
    public ResponseEntity<Person> updatePersonByField(@PathVariable("id") Long id, @RequestBody PersonDto body) {
        return ResponseEntity.ok(personCrudService.partialUpdatePerson(id, body));
    }

    /**
     * DELETE A RESOURCE
     *
     * @param id <-- id of resource to delete
     *           even if the record does not exist - return 200
     */
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable Long id) {
        personCrudService.deletePerson(id);
    }


}


