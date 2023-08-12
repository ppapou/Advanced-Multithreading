import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

public class ApplyerTest {
    ForkJoinPool pool;
    double array [];
    @Test
    double sumOfSquares() {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        return a.result;
    }
}
