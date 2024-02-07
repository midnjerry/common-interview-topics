package net.crusader.games.interviewreview.service;

import net.crusader.games.interviewreview.controller.PersonController;
import net.crusader.games.interviewreview.exceptions.ResourceNotFoundException;
import net.crusader.games.interviewreview.models.Person;
import net.crusader.games.interviewreview.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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


}
