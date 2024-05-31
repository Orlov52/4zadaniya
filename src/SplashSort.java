public class SplashSort {
    public static void main(String[] args) {
        int[] arr = {345, 251, 512, 422, 1551};
        splashSort(arr);
        System.out.println("Sorted array:");
        printArray(arr);
    }

    static void splashSort(int[] arr) {
        int length = arr.length;
        boolean swapped;

        do {
            swapped = false;
            int left = 0, right = length - 1;
            for (int i = left; i < right; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    swapped = true;
                }
            }
            right--;

            for (int i = right; i > left; i--) {
                if (arr[i] < arr[i - 1]) {
                    swap(arr, i, i - 1);
                    swapped = true;
                }
            }
            left++;
        } while (swapped);
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
