package ca.jrvs.practice.fundamentals.search;

import java.util.Arrays;

public class BinarySearch {
  /**
   * find the the target index in a sorted array
   *
   * @param arr input array is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not ound
   */
  public int binarySearchRecursion(int[] arr, int target) {
    int l = 0;
    int r = arr.length;
    if (r >= l) {
      int mid = l + (r - l) / 2;

      // If the element is present at the
      // middle itself
      if (arr[mid] == target)
        return mid;

      // If element is smaller than mid, then
      // it can only be present in left subarray
      if (arr[mid] > target)
        return binarySearchRecursion(Arrays.copyOfRange(arr, l, mid - 1), target);

      // Else the element can only be present
      // in right subarray
      return binarySearchRecursion(Arrays.copyOfRange(arr, mid + 1, r), target);
    }

    // We reach here when element is not present
    // in array
    return -1;
  }

  /**
   * find the the target index in a sorted array
   *
   * @param arr input array is sorted
   * @param target value to be searched
   * @return target index or Optional.empty() if not ound
   */
  public int binarySearchIteration(int[] arr, int target) {
    int l = 0, r = arr.length - 1;
    while (l <= r) {
      int m = l + (r - l) / 2;

      // Check if x is present at mid
      if (arr[m] == target)
        return m;

      // If x greater, ignore left half
      if (arr[m] < target)
        l = m + 1;

      // If x is smaller, ignore right half
      else
        r = m - 1;
    }

    // if we reach here, then element was
    // not present
    return -1;
  }
}
