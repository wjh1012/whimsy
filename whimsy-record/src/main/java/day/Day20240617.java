package day;

import java.util.Arrays;

/**
 * @author jh.wang
 * @since 2024/6/17
 */

public class Day20240617 {
    public static void main(String[] args) {
        int[] arr = new int[]{9, 2, 3, 4, 1, 6, 7, 8, 0, -1};
        final int length = arr.length;

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (arr[i] > arr[j]) {
                    int old = arr[i];
                    arr[i] = arr[j];
                    arr[j] = old;
                }
            }
        }

        System.out.println(Arrays.toString(arr));
    }
}
