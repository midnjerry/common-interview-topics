package net.crusader.games.interviewreview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class BaseResponse {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;
}
