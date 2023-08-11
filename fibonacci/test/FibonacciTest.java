import org.junit.Test;
import java.util.concurrent.ForkJoinPool;
import static junit.framework.TestCase.assertEquals;

public class FibonacciTest {

    @Test
    public void computeTest(){
        int n = 45;
        assertEquals(1134903170L, new ForkJoinPool().invoke(new Fibonacci(45)).longValue());
    }
}