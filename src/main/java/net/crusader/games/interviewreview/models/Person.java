package net.crusader.games.interviewreview.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import net.crusader.games.interviewreview.dto.PersonDto;

import java.time.LocalDate;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;
    @JsonIgnore
    private boolean active = true;

    public Person (Long id, PersonDto dto){
        this.id = id;
        firstName = dto.getFirstName();
        middleName = dto.getMiddleName();
        lastName = dto.getLastName();
        birthday = dto.getBirthday();
        phoneNumber = dto.getPhoneNumber();
    }
//
//    private Person father;
//    private Person mother;
}
