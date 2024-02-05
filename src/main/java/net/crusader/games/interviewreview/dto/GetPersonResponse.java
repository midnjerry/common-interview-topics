package net.crusader.games.interviewreview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import net.crusader.games.interviewreview.models.Person;

@Data
public class GetPersonResponse extends BaseResponse {
    private Person person;
}
