package net.crusader.games.interviewreview;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamsStudy {

    /**
     * Sandbox for studying and mastering streams
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> items = new ArrayList<>(Arrays.asList(1, 5, 3, 6, 3, 7, 2, 10, 23, 123, 23, 12, 63));

        System.out.println("forEach iterates through list but does not continue flow.  Does not return stream");
        items.forEach(item -> {
            // Iterates through list, but does not
            // return a Stream.
            System.out.println("Item: " + item);
        });

        Stream<Integer> s2 = items.stream().filter(n -> n % 2 == 0);

        //s2 now has a filtered stream.
        System.out.println(s2);


        System.out.println("toList() converts to a list" + s2.toList());
        // Interesting!  .toList() closes the Stream in s2

        System.out.println(Arrays.toString(items.stream().filter(n -> n % 2 == 1).toArray()));


        // Parallel allows parallel processing.

        long startTime = System.nanoTime();
        System.out.println("Starting addition");
        long sum = 0;
        long max = 1000000000L;
        for (long i = 0; i < max; i++) {
            sum += i;
        }
        System.out.println(sum);
        long endTime = System.nanoTime();
        long durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Elapsed time: " + durationInMillis + " ms");

        startTime = System.nanoTime();
        System.out.println("Starting addition");
        Long sum2 = LongStream.range(0, max).sum();
        System.out.println(sum2);
        endTime = System.nanoTime();
        durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Elapsed time: " + durationInMillis + " ms");

        startTime = System.nanoTime();
        System.out.println("Starting addition");
        Long sum3 = LongStream.range(0, max).parallel().sum();
        System.out.println(sum3);
        endTime = System.nanoTime();
        durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Elapsed time: " + durationInMillis + " ms");

        // For loop beats the stream, but parallel beats both.  Really cool
        // Output:
//        Starting addition
//        499999999500000000
//        Elapsed time: 327 ms
//        Starting addition
//        499999999500000000
//        Elapsed time: 1254 ms
//        Starting addition
//        499999999500000000
//        Elapsed time: 53 ms

        // .map() is similar to forEach but it allows you to modify the datatype of the
        // Stream.  Imagine converting it from one datatype to another.

        List<Integer> items2 = new ArrayList<>(List.of(3, 2, 3, 5, 6, 2, 3, 4, 5, 6, 2, 3));

        List<Character> charList = items2.stream().map(n -> (char) ('A' + n)).toList();
        System.out.println(charList);

        // .reduce "reduces" the Stream into one value.

        // The identity = initial value
        // The aggregate is the variable that retains states across iterations
        // can also be called "the running result"
        // The element is the current item of the loop
        sum = items2.stream().reduce(0, (aggregate, element) -> element + aggregate);
        System.out.println(sum);

        sum = items2.stream().reduce(5, (aggregate, element) -> element + aggregate);
        System.out.println(sum);

        // .findFirst()
        // It returns the first element of a stream (wrapped in an Optional), if there is one.
        // NEED to use filter, otherwise, will return first element.
        List<Integer> items3 = new ArrayList<>(List.of(3, 2, 3, 5, 6, 2, 3, 4, 5, 6, 2, 3));

        Optional<Integer> firstEvenOptional = items3.stream().filter(n -> n % 2 == 0).findFirst();
        System.out.println(firstEvenOptional);

        Optional<Integer> greaterHundredOptional = items3.stream().filter(n -> n > 100).findFirst();
        System.out.println(greaterHundredOptional);

        // Optional.ofNullable wraps a variable with an Optional.  If the variable is null
        // then the Optional is empty.
        Optional.ofNullable(sum);

        // .flatMap
        List<String> sentences = List.of(
                "Hello world",
                "Java is fun",
                "flatMap is weird"
        );

        List<String[]> listOfWordArrays = sentences.stream()
                .map(s -> s.split(" "))
                .toList();

        System.out.println(listOfWordArrays);
        // listOfWordArrays is a List of String arrays.  If we want to process each word
        // individually we can use flatMap.

        List<String> words = listOfWordArrays.stream().flatMap(Arrays::stream).toList();
        System.out.println(words);

        // FlatMap works by taking an element and running it as a stream to break it down
        // into individual elements.


        Optional<Optional<Optional<Integer>>> nestedOptional = Optional.of(Optional.of(Optional.of(5)));
        //flatMap only unwraps once.
        Optional<Optional<Integer>> optional = nestedOptional.flatMap(s -> s);


    }

    public static Stream<PendingOrder> reconcile(Stream<PendingOrder> pending, Stream<Stream<CompletedOrder>> processed) {
        Set<CompletedOrder> cleanedUp = processed
                .flatMap(s -> s) // First call flatMap to flatten nested Streams
                .filter(s -> s != null)
                .filter(s -> s.id != null && !s.id.trim().isEmpty())
                .filter(s -> s.getStatus() != null &&  s.getStatus().isPresent() && s.getStatus().get().equalsIgnoreCase("DONE"))
                .collect(Collectors.toSet());

        return pending.filter(po -> cleanedUp.stream().anyMatch(co -> co.getId() != null && !co.getId().isEmpty() && po.getId() == Long.parseLong(co.getId())));
    }
}

class PendingOrder {
    Long id;

    public PendingOrder(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PendingOrder{" +
                "id=" + id +
                '}';
    }
}

class CompletedOrder {
    String id;
    Optional<String> status;

    public CompletedOrder(String id, Optional<String> status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Optional<String> getStatus() {
        return status;
    }


}