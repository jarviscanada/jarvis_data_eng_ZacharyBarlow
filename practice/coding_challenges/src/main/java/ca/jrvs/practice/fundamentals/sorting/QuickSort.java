package ca.jrvs.practice.fundamentals.sorting;

public class QuickSort {

  // quicksort(A, start, end)
  // if start < end
  // -> pivot = partition(A, start, end)
  // -> quicksort(A, start, pivot-1)
  // -> quicksort(A, pivot+1, end)

  // partition(A, start, end)
  // -> pivot = A[end]
  // -> i = start
  // -> for j = start to end-1
  //   -> if A[j] <= pivot
  //      -> exchange A[i] with A[j]
  //      -> i = i + 1
  // -> exchange A[i] with A[end]

  public void quickSort(int[] arr, int start, int end) {
    if (start < end) {
      int pivot = partition(arr, start, end);

      quickSort(arr, start, pivot - 1);
      quickSort(arr, pivot + 1, end);
    }
  }

  private int partition(int[] arr, int start, int end) {
    int pivot = arr[end];
    int i = start;
    for (int j = start; j < end - 1; j++) {
      if (arr[j] <= pivot) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        i++;
      }
    }
    int temp = arr[i];
    arr[i] = arr[end];
    arr[end] = temp;
    return i;
  }
}
