package net.crusader.games.interviewreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO = Data Transfer Object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private String phoneNumber;
}
