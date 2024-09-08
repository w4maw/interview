import org.example.IteratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IteratorImplTest {
    @Test
    void positiveTest() {
        var collection = generateCollection();
        var iterator = new IteratorImpl<>(collection);
        for (int i = 0; i < collection.size(); i++) {
            Assertions.assertTrue(iterator.hasNext());
            Assertions.assertDoesNotThrow(iterator::next);
        }
    }

    @Test
    void negativeTest() {
        var collection = generateCollection();
        var iterator = new IteratorImpl<>(collection);
        IntStream.range(0, collection.size()).forEach(value -> iterator.next());
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void removeTest() {
        var collection = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7));
        var iterator = new IteratorImpl<>(collection);
        iterator.remove();
        IntStream.range(0, collection.size())
                .forEach(value -> {
                    Assertions.assertDoesNotThrow(iterator::next);
                });
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    void nextAfterRemoveTest() {
        var collection = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7));
        var iterator = new IteratorImpl<>(collection);
        IntStream.range(0, collection.size() - 1)
                .forEach(value -> {
                    Assertions.assertDoesNotThrow(iterator::next);
                });
        iterator.remove();
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }

    private Collection<Integer> generateCollection() {
        var random = new SecureRandom();
        return IntStream.range(0, random.nextInt(100))
                .mapToObj(value -> random.nextInt(10_000))
                .collect(Collectors.toSet());
    }
}
