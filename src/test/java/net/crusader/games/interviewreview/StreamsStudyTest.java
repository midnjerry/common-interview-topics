package net.crusader.games.interviewreview;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class StreamsStudyTest {

    @Test
    public void dummy() {
        Stream<PendingOrder> pendingOrderStream = Stream.of(
                new PendingOrder(3L),
                new PendingOrder(25L),
                new PendingOrder(1L),
                new PendingOrder(36L),
                new PendingOrder(2L),
                new PendingOrder(85L),
                new PendingOrder(94L),
                new PendingOrder(45L),
                new PendingOrder(26L)
        );

        Stream<Stream<CompletedOrder>> processedOrders = Stream.of(Stream.of(
                new CompletedOrder("3", Optional.empty()),
                new CompletedOrder("25", Optional.of("DONE")),
                null,
                new CompletedOrder("35", Optional.empty()),
                new CompletedOrder("2", null),
                new CompletedOrder("26", Optional.of("done"))
        ));


        List<PendingOrder> expected = StreamsStudy.reconcile(pendingOrderStream, processedOrders).toList();
        
        assertEquals(2, e)
        
        System.out.println(expected.toList());
    }

}