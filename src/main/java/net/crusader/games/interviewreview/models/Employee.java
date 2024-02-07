package net.crusader.games.interviewreview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Long id;
    private String name;
    private Double salary;

    public void salaryIncrement(double raise) {
        salary *= 1 + (raise / 100.0);
    }
}
