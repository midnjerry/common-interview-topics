package net.crusader.games.interviewreview.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO = Data Transfer Object
 */
@Data
public class PersonDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;
}
