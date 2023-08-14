import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

public class ApplyerTest {
    ForkJoinPool pool;
    double array [];
    @Test
    public void sumOfSquares() {
        int n = array.length;
        Applyer a = new Applyer(array, 0, n, null);
        pool.invoke(a);
        System.out.println(a.result);
    }
}
