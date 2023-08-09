package sort;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinSortTask {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the number array elements");
        int totalNumbers = scanner.nextInt();
        int[] numbers = new int[totalNumbers];

        System.out.println("Please, enter each element of array");
        for (int i = 0; i < totalNumbers; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println("Unsorted array: "
                + Arrays.toString(numbers));

        MergeSortTask task = new MergeSortTask(numbers);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(task);

        System.out.println("Sorted array: "
                + Arrays.toString(task.join()));

    }
}