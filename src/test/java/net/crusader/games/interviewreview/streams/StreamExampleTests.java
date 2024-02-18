package net.crusader.games.interviewreview.streams;

import net.crusader.games.interviewreview.dto.PersonDto;
import net.crusader.games.interviewreview.models.Employee;
import net.crusader.games.interviewreview.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Test
    public void whenFindMin_thenGetMinElementFromStream() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        Employee firstEmp = employeeList.stream()
                .min((e1, e2) -> e1.getSalary().compareTo(e2.getSalary()))
                .orElseThrow(NoSuchElementException::new);

        assertEquals(100000, firstEmp.getSalary());
    }

    @Test
    public void whenApplyMatch_thenReturnBoolean() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);

        boolean allEven = intList.stream().allMatch(i -> i % 2 == 0);
        boolean oneEven = intList.stream().anyMatch(i -> i % 2 == 0);
        boolean noneMultipleOfThree = intList.stream().noneMatch(i -> i % 3 == 0);

        assertEquals(allEven, false);
        assertEquals(oneEven, true);
        assertEquals(noneMultipleOfThree, false);
    }

    @Test
    public void whenFindMaxOnIntStream_thenGetMaxInteger() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));

        Long latestEmpId = employeeList.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElseThrow(NoSuchElementException::new);

        assertEquals(3, latestEmpId);


    }

    @Test
    public void range_showExampleWithIntStream() {
        Integer max = IntStream.range(10, 20).peek(e -> System.out.println("Number: " + e)).max().orElseThrow(NoSuchElementException::new);
        assertEquals(19, max);
    }

    @Test
    public void whenApplySumOnIntStream_thenGetSum() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        Double avgSal = employeeList.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElseThrow(NoSuchElementException::new);

        Double sum = employeeList.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        assertEquals(200000, avgSal);
        assertEquals(600000, sum);
    }

    @Test
    public void whenApplyReduceOnStream_thenGetValue() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        Double sumSal = employeeList.stream()
                .map(Employee::getSalary)
                .reduce(0.0, (accumulatedValue, element) -> {
                    System.out.println("accumulatedValue: " + accumulatedValue + " element: " + element);
                    // accumulatedValue = accumulatedValue + element;  <--- this is the equivalent for statement below.
                    return accumulatedValue + element;
                });

        assertEquals(600000, sumSal);

        Double raiseBudget = employeeList.stream()
                .map(Employee::getSalary)
                .reduce(0.0, (accumulatedValue, element) -> {
                    System.out.println("accumulatedValue: " + accumulatedValue + " element: " + element);
                    // accumulatedValue = accumulatedValue + element;  <--- this is the equivalent for statement below.
                    return accumulatedValue + element * .05;
                });
        assertEquals(30000, raiseBudget);
    }

    @Test
    public void whenCollectByJoining_thenGetJoinedString() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        List<String> names = new ArrayList<>();
        for (Employee employee : employeeList) {
            names.add(employee.getName());
        }
        String.join(", ", names);

        String empNames = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));

        assertEquals("Jeff Bezos, Bill Gates, Mark Zuckerberg", empNames);


    }

    @Test
    public void whenToVectorCollection_thenGetVector() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        Vector<String> empNames = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(Vector::new));

        assertEquals(empNames.size(), 3);
    }

    @Test
    public void whenApplySummarizing_thenGetBasicStats() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        DoubleSummaryStatistics stats = employeeList.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        assertEquals(stats.getCount(), 3);
        assertEquals(stats.getSum(), 600000.0, 0);
        assertEquals(stats.getMin(), 100000.0, 0);
        assertEquals(stats.getMax(), 300000.0, 0);
        assertEquals(stats.getAverage(), 200000.0, 0);
    }

    @Test
    public void whenApplySummaryStatistics_thenGetBasicStats() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));

        DoubleSummaryStatistics stats = employeeList.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        assertEquals(stats.getCount(), 3);
        assertEquals(stats.getSum(), 600000.0, 0);
        assertEquals(stats.getMin(), 100000.0, 0);
        assertEquals(stats.getMax(), 300000.0, 0);
        assertEquals(stats.getAverage(), 200000.0, 0);
    }

    @Test
    public void whenStreamPartition_thenGetMap() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);
        Map<Boolean, List<Integer>> booleanToElementMap = intList.stream().collect(
                Collectors.partitioningBy(element -> element % 2 == 0));

        List<Integer> numbers = booleanToElementMap.get(true);
        System.out.println(numbers);
        System.out.println(booleanToElementMap.get(false));

        assertEquals(4, booleanToElementMap.get(true).size());
        assertEquals(1, booleanToElementMap.get(false).size());

        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));

        Map<Boolean, List<Employee>> employeesGroupedByFirstLetterMap = employeeList
                .stream()
                .collect(Collectors.partitioningBy(e -> e.getName().toUpperCase().startsWith("B")));
        System.out.println(employeesGroupedByFirstLetterMap);

    }


    @Test
    public void whenStreamGroupingBy_thenGetMap() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));
        employeeList.add(new Employee(4L, "Jerry Balderas", 50000.0));

        Map<Character, List<Employee>> firstLetterToEmployeeMap = new HashMap<>();

        for (Employee employee : employeeList) {
            Character firstCharacter = employee.getName().toUpperCase().charAt(0);
            List<Employee> savedEmployees = firstLetterToEmployeeMap.getOrDefault(firstCharacter, new ArrayList<>());
            savedEmployees.add(employee);
            firstLetterToEmployeeMap.put(firstCharacter, savedEmployees);
        }

        assertEquals("Bill Gates", firstLetterToEmployeeMap.get('B').get(0).getName());
        assertEquals("Jeff Bezos", firstLetterToEmployeeMap.get('J').get(0).getName());
        assertEquals("Jerry Balderas", firstLetterToEmployeeMap.get('J').get(1).getName());
        assertEquals("Mark Zuckerberg", firstLetterToEmployeeMap.get('M').get(0).getName());

        Map<Character, List<Employee>> groupByAlphabet = employeeList.stream().collect(Collectors.groupingBy(e -> e.getName().charAt(0)));

        assertEquals("Bill Gates", groupByAlphabet.get('B').get(0).getName());
        assertEquals("Jeff Bezos", groupByAlphabet.get('J').get(0).getName());
        assertEquals("Jerry Balderas", groupByAlphabet.get('J').get(1).getName());
        assertEquals("Mark Zuckerberg", groupByAlphabet.get('M').get(0).getName());

    }

    @Test
    public void whenStreamMapping_thenGetMap() {
        List<Employee> employeeList = new ArrayList<>(List.of(arrayOfEmps));

        Map<Character, List<Long>> idGroupedByAlphabet = employeeList.stream()
                .collect(
                Collectors.groupingBy(e -> e.getName().charAt(0), Collectors.mapping(Employee::getId, Collectors.toList())));

        assertEquals(2, idGroupedByAlphabet.get('B').get(0));
        assertEquals(1, idGroupedByAlphabet.get('J').get(0));
        assertEquals(3, idGroupedByAlphabet.get('M').get(0));
    }

    @Test
    public void whenParallelStream_thenPerformOperationsInParallel() {
        Employee[] arrayOfEmps = {
                new Employee(1L, "Jeff Bezos", 100000.0),
                new Employee(2L, "Bill Gates", 200000.0),
                new Employee(3L, "Mark Zuckerberg", 300000.0)
        };

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(arrayOfEmps));
        for (long i = 4; i <= 10000000; i++){
            employeeList.add(new Employee(i, "Employee: " + i, 100000.0 ));
        }

        System.out.println("Size of list: " +  employeeList.size());

        long startTime = System.nanoTime();
        List<Employee> empList1 = employeeList.stream().peek(e -> e.salaryIncrement(10.0)).collect(Collectors.toList());
        long endTime = System.nanoTime();

        double duration = (endTime - startTime) / 1000000.0;
        System.out.println("Single loop duration: " + duration + " ms");

        startTime = System.nanoTime();
        List<Employee> empList2 = employeeList.stream().parallel().peek(e -> e.salaryIncrement(10.0)).collect(Collectors.toList());
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000.0;
        System.out.println("Parallel loop duration: " + duration + " ms");
    }
}
