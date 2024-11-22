
import com.laba2.FlowerComparator;
import generated.Orangery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlowerComparatorTest {

    private FlowerComparator comparator;
    private Orangery.Flower flower1;
    private Orangery.Flower flower2;
    private Orangery.Flower flower3;

    @BeforeEach
    public void setUp() {
        comparator = new FlowerComparator();

        // Creating instances of Orangery.Flower
        flower1 = new Orangery.Flower();
        flower1.setName("Rose");

        flower2 = new Orangery.Flower();
        flower2.setName("Tulip");

        flower3 = new Orangery.Flower();
        flower3.setName("Rose");
    }

    @Test
    public void testCompare_LexicographicalOrder() {
        int result = comparator.compare(flower1, flower2);
        assertEquals(true, result < 0, "Expected Rose to be lexicographically before Tulip");
    }

    @Test
    public void testCompare_SameName() {
        int result = comparator.compare(flower1, flower3);
        assertEquals(0, result, "Expected comparison to return 0 for flowers with the same name");
    }

    @Test
    public void testCompare_ReverseOrder() {
        int result = comparator.compare(flower2, flower1);

        assertEquals(true, result > 0, "Expected Tulip to be lexicographically after Rose");
    }
}
