package ca.jrvs.practice.challenges.array;

import java.util.Arrays;

public class FindLargestSmallest {

  /**
   * Exactly 1 for loop
   * @param nums
   * @return
   */
  public int findLargest1(int[] nums) {
    int m = nums[0];

    for (int i = 1; i < nums.length; i++) {
      if (nums[i] > m) {
        m = nums[i];
      }
    }
    return m;
  }

  /**
   * Stream API
   * @param nums
   * @return
   */
  public int findLargest2(int[] nums) {
    return Arrays.stream(nums).max().getAsInt();
  }

  /**
   * Java Built in API
   * @param nums
   * @return
   */
  public int findLargest3(int[] nums) {
    return 0;
  }
}
