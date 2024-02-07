package net.crusader.games.interviewreview.streams;

import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.models.Employee;
import net.crusader.games.interviewreview.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamExampleTests {

    private static Employee[] arrayOfEmps;

    private Person[] persons;

    @BeforeEach
    public void setup() {
        arrayOfEmps = new Employee[]{
                new Employee(1L, "Jeff Bezos", 100000.0),
                new Employee(2L, "Bill Gates", 200000.0),
                new Employee(3L, "Mark Zuckerberg", 300000.0)
        };

        persons = new Person[]{
                new Person(1L, new PersonDto("Jeff", "", "Bezos", LocalDate.of(1980, 1, 1), "555-5555")),
                new Person(2L, new PersonDto("Bill", "", "Gates", LocalDate.of(1980, 1, 1), "555-5555")),
                new Person(3L, new PersonDto("Mark", "", "Zuckerberg", LocalDate.of(1980, 1, 1), "555-5555"))
        };
    }

    @Test
    public void gettingAStream() {
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(arrayOfEmps));
        System.out.println(employeeList);
        Stream.of(1, 2, 3, 4, 5).forEach(element -> {
            System.out.println("This element is: " + element);
        });
        System.out.println("***");
        Stream.of(1, 2, 3, 4, 5).filter(element -> element > 3).forEach(element -> {
            System.out.println("This element is: " + element);
        });

        Stream.Builder<Employee> employeeStreamBuilder = Stream.builder();

        employeeStreamBuilder.accept(arrayOfEmps[0]);
        employeeStreamBuilder.accept(arrayOfEmps[1]);
        employeeStreamBuilder.accept(arrayOfEmps[2]);

        Stream<Employee> employeeStream = employeeStreamBuilder.build();

        employeeStream.filter(e -> e.getSalary() > 150000).filter(e -> e.getName().startsWith("M")).forEach(e -> System.out.println(e));
    }

    @Test
    public void forEach_whenIncrementSalaryForEachEmployee_thenApplyNewSalary() {
        List<Employee> employeeList = new ArrayList<>(Arrays.asList(arrayOfEmps));

        employeeList.stream().forEach(e -> e.salaryIncrement(10.0));

        assertEquals(110000, employeeList.get(0).getSalary(), .00001);
        assertEquals(220000, employeeList.get(1).getSalary(), .00001);
        assertEquals(330000, employeeList.get(2).getSalary(), .00001);
    }

    @Test
    public void map_whenMapIdToEmployees_thenGetEmployeeStream() {
        List<Employee> employees = Arrays.stream(persons)
                .map((person) -> {
                    Employee employee = new Employee();
                    employee.setId(person.getId());
                    employee.setName(person.getFirstName() + " " + person.getMiddleName() + person.getLastName());
                    employee.setSalary(20000.0);
                    return employee;
                }).toList();

        System.out.println(Arrays.toString(persons));
        System.out.println(employees);

        assertEquals(persons.length, employees.size());
    }

    @Test
    public void collect_gatherDifferentDatatypes() {
        List<Employee> employees = Arrays.stream(persons)
                .map((person) -> {
                    Employee employee = new Employee();
                    employee.setId(person.getId());
                    employee.setName(person.getFirstName() + " " + person.getMiddleName() + person.getLastName());
                    employee.setSalary(20000.0);
                    return employee;
                }).toList();

        Set<Employee> employeeSet = employees.stream().collect(Collectors.toSet());
        employeeSet.add(new Employee());
        System.out.println(employeeSet);

        List<Employee> unmodifiableList = employees.stream().collect(Collectors.toUnmodifiableList());
        assertTrue(employeeSet instanceof HashSet<Employee>, "Stream should return a HashSet");
    }

    @Test
    public void findFirst() {
        Integer[] empIds = {1, 2, 3, 4};

        Optional<Integer> optional = Arrays.stream(empIds).findFirst();
        Integer value = optional.get();
        assertTrue(optional.isPresent());
        assertEquals(1, value);

        Optional<Integer> evenIdOptional = Arrays.stream(empIds).filter(id -> id % 2 == 0).findFirst();
        Integer evenId = evenIdOptional.get();
        assertTrue(evenIdOptional.isPresent());
        assertEquals(2, evenId);

        Optional<Integer> optionalC = Arrays.stream(empIds).filter(id -> id > 10).findFirst();
        assertTrue(optionalC.isEmpty());
    }

    @Test
    public void toArray_convertToArray() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        Employee[] employeeArray = employeeList.stream().toArray(Employee[]::new);
        Employee[] employeeArray2 = employeeList.toArray(new Employee[0]);

        assertEquals(Arrays.toString(employeeArray), Arrays.toString(employeeArray2));
    }

    @Test
    public void flatMap_convertsToAList() {
        Integer[] empIds = {1, 2, 3, 4};
        Integer[] empIds2 = {4, 5, 8, 10};
        Integer[] empIds3 = {20, 30, 40};

        List<Integer[]> listOfArrays = Arrays.asList(
                empIds, empIds2, empIds3
        );

        System.out.println(listOfArrays);

        List<Integer> numList = listOfArrays.stream().flatMap(array -> Arrays.stream(array)).collect(Collectors.toList());
        System.out.println(numList);
    }

    @Test
    public void whenFlatMapEmployeeNames_thenGetNameStream() {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));

        List<String> namesFlatStream = namesNested.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertEquals(namesFlatStream.size(), namesNested.size() * 2);
    }

    @Test
    public void whenIncrementSalaryUsingPeek_thenApplyNewSalary() {
        Employee[] arrayOfEmps = {
                new Employee(1L, "Jeff Bezos", 100000.0),
                new Employee(2L, "Bill Gates", 200000.0),
                new Employee(3L, "Mark Zuckerberg", 300000.0)
        };

        List<Employee> empList = Arrays.asList(arrayOfEmps);

        List<Employee> actualList = empList.stream()
                .peek(e -> e.salaryIncrement(10.0))
                .peek(System.out::println)
                .peek(e -> System.out.println("Salary: " + e.getSalary()))
                .collect(Collectors.toList());


        System.out.println(empList);
        System.out.println(actualList);
    }
}
