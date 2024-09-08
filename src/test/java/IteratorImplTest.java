import org.example.IteratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.NoSuchElementException;
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

    private Collection<Integer> generateCollection() {
        var random = new SecureRandom();
        return IntStream.range(0, random.nextInt(100))
                .mapToObj(value -> random.nextInt(10_000))
                .collect(Collectors.toSet());
    }
}
