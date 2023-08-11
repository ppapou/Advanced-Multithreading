import java.util.concurrent.RecursiveTask;

/**
 * Give example from RecursiveTask javadoc.
 * Write FibonacciTask that implements RecursiveTask.
 * Apply suggestion from javadoc to check minimum granularity size less or equal 10. And in that case use linear algorithm.
 * Using unit test check that your code works correctly:
 */
public class Fibonacci extends RecursiveTask<Integer> {
    final int n;
    Fibonacci(int n) { this.n = n; }
    public Integer compute() {
        if (n <= 1)
            return n;
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        return f2.compute() + f1.join();
    }
}