package sort;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

//thread in Java, inherit OR create the Runnable
//concurrency(synchronise vs locks)
//fork/join pattern -> idea of this pattern
//thread vs process vs virt threads
//worker/pool/thread pattern(in concurrency see que and executers how it works!!)
//stream API(non terminal and terminal operators in streams), the price of the parallel interface
//completable future(callable interface vs runnable!)


//OPTIONAL for FUTURE -> reactive framework(web-flux and functional programming/Reactive extension standard)
class MergeSortTask extends RecursiveTask<int[]> {

    int[] arrayToDivide;

    public MergeSortTask(int[] arrayToDivide) {
        this.arrayToDivide = arrayToDivide;
    }

    @Override
    protected int[] compute() {

        /*
         * Divide the array till it has only 1 element.
         */
        if (arrayToDivide.length > 1) {

            List<int[]> partitionedArray = partitionArray();
            System.out.println("The First Element of partitioned array: " + Arrays.toString(partitionedArray.get(0)));
            System.out.println("The Second Element of partitioned array: " + Arrays.toString(partitionedArray.get(1)));
            System.out.println("Length of initial array: " + arrayToDivide.length);
            MergeSortTask task1 = new MergeSortTask(partitionedArray.get(0));
            MergeSortTask task2 = new MergeSortTask(partitionedArray.get(1));
            invokeAll(task1, task2);

            //Wait for results from both the tasks
            int[] array1 = task1.join();
            int[] array2 = task2.join();

            //Initialize a merged array
            int[] mergedArray =
                    new int[array1.length + array2.length];

            mergeArrays(task1.join(), task2.join(), mergedArray);

            return mergedArray;
        }
        return arrayToDivide;
    }

    private List<int[]> partitionArray(){

        int [] partition1 = Arrays.copyOfRange(arrayToDivide, 0,
                arrayToDivide.length / 2);

        int [] partition2 = Arrays.copyOfRange(arrayToDivide,
                arrayToDivide.length / 2,
                arrayToDivide.length);
        return Arrays.asList(partition1,partition2);

    }

    private void mergeArrays(
            int[] array1,
            int[] array2,
            int[] mergedArray) {

        int i = 0, j = 0, k = 0;

        while ((i < array1.length) && (j < array2.length)) {

            if (array1[i] < array2[j]) {
                mergedArray[k] = array1[i++];
            } else {
                mergedArray[k] = array2[j++];
            }
            k++;
        }

        if (i == array1.length) {

            for (int a = j; a < array2.length; a++) {
                mergedArray[k++] = array2[a];
            }

        } else {

            for (int a = i; a < array1.length; a++) {
                mergedArray[k++] = array1[a];
            }
        }
    }
}