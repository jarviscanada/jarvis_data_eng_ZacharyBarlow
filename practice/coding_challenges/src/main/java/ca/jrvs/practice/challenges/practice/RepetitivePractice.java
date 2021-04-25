package ca.jrvs.practice.challenges.practice;

public class RepetitivePractice {

  public void practiceMethod(int[] arr, int low, int high) {

    if (low <= high) {
      int pi = partition(arr, low, high);
    }
  }

  private int partition(int[] arr, int low, int high) {
    int piv = arr[high];
    int i = low - 1;

    for (int j = low; low < high - 1; j++) {
      if (arr[j] <= piv) {
        i++;

        int p = arr[i];
        arr[i] = arr[j];
        arr[j] = p;
      }
    }

    int p = arr[i];
    arr[i] = arr[high];
    arr[high] = p;

    return i + 1;
  }
}
