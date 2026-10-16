package pb.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CPUTaskScheduling {
    public static List<Integer> maximizeEasyTasks(List<Integer> tasks) {
        if (tasks == null || tasks.size() <= 2) {
            return tasks; // 0, 1, or 2 elements can never have a local minimum
        }

        // 1. Sort the tasks to easily separate small and large elements
        List<Integer> sorted = new ArrayList<>(tasks);
        Collections.sort(sorted);

        List<Integer> rearranged = new ArrayList<>(tasks.size());

        // 2. Split into two halves using a two-pointer approach
        // 'left' points to the smallest elements (the easy tasks)
        // 'right' points to the largest elements (the high buffers)
        int n = sorted.size();
        int left = 0;
        int right = (n + 1) / 2; // Start of the larger half

        int tempLeft = left;
        int tempRight = right;

        // 3. Interleave: High, Low, High, Low...
        // We start with a High element so the first Low element has a valid left neighbor
        while (tempRight < n || tempLeft < right) {
            if (tempRight < n) {
                rearranged.add(sorted.get(tempRight++));
            }
            if (tempLeft < right) {
                rearranged.add(sorted.get(tempLeft++));
            }
        }

        return rearranged;
    }

    // Helper method to count easy tasks (local minima)
    public static int countEasyTasks(List<Integer> arr) {
        int count = 0;
        for (int i = 1; i < arr.size() - 1; i++) {
            if (arr.get(i) < arr.get(i - 1) && arr.get(i) < arr.get(i + 1)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        test0();
        //test1();
        //test2();
    }

    private static void test0(){
        List<Integer> cpuTasks = List.of(4, 5, 6, 1, 2);
        test(cpuTasks);
    }

    private static void test1(){
        List<Integer> cpuTasks = List.of(1, 2, 3, 4, 5, 6, 7);
        test(cpuTasks);
    }

    private static void test2(){
        List<Integer> cpuTasks = List.of(1, 2, 3, 4, 5, 6, 7);
        test(cpuTasks);
    }

    private static void test(List<Integer> cpuTasks){
        System.out.println("--- BEFORE REARRANGING ---");
        System.out.println("Array: " + cpuTasks);
        System.out.println("Easy Task Count: " + countEasyTasks(cpuTasks));

        List<Integer> result = maximizeEasyTasks(cpuTasks);

        System.out.println("\n--- AFTER REARRANGING ---");
        System.out.println("Array: " + result);
        System.out.println("Easy Task Count: " + countEasyTasks(result));
    }
}


