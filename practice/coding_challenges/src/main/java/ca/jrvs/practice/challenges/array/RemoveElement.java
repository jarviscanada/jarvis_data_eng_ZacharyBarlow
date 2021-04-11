package ca.jrvs.practice.challenges.array;

/*
Given an array nums and a value val, remove all instances of that value in-place and return the new length.

Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.

The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 */
public class RemoveElement {

  /**
   *  worst case : O(n)
   * @param nums
   * @param val
   * @return
   */
  public int removeElement(int[] nums, int val) {
    int len = nums.length - 1;
    for (int i = len; i >= 0; i--) {
      if (nums[i] == val) {
        nums[i] = nums[len];
        len--;
      }
    }
    return len + 1;
  }
}
