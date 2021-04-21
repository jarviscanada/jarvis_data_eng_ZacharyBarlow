package ca.jrvs.practice.fundamentals.sorting;

public class InsertionSort {
  /**
   * In-place insertionSort
   * @param input
   */
  public static void insertionSortImperative(int[] input) {
    for (int i = 1; i < input.length; i++) {
      int key = input[i];
      int j = i - 1;
      while (j >= 0 && input[j] > key) {
        input[j + 1] = input[j];
        j = j - 1;
      }
      input[j + 1] = key;
    }
  }
}
