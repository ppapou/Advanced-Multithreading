import java.util.concurrent.RecursiveAction;

/**
 * Sum of double squares
 *
 * Give last example from RecursiveAction javadoc about calculation of sum of squares in double[] array.
 * Use double array of half-billion size 500_000_000 filled by random doubles.
 * Compare speed with direct linear calculation (you may use Stream API as well):
 * double sum = 0; for (double v : ARRAY) { sum += v * v; }
 */
class Applyer extends RecursiveAction {
    final double[] array;
    final int lo, hi;
    double result;
    Applyer next; // keeps track of right-hand-side tasks
    Applyer(double[] array, int lo, int hi, Applyer next) {
        this.array = array; this.lo = lo; this.hi = hi;
        this.next = next;
    }

    double atLeaf(int l, int h) {
        double sum = 0;
        for (int i = l; i < h; ++i) // perform leftmost base step
            sum += array[i] * array[i];
        return sum;
    }

    protected void compute() {
        int l = lo;
        int h = hi;
        Applyer right = null;
        while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
            int mid = (l + h) >>> 1;
            right = new Applyer(array, mid, h, right);
            right.fork();
            h = mid;
        }

        double sum = atLeaf(l, h);

        while (right != null) {
            if (right.tryUnfork()) // directly calculate if not stolen
                sum += right.atLeaf(right.lo, right.hi);
            else {
                right.join();
                sum += right.result;
            }
            right = right.next;
        }
        result = sum;
    }
}