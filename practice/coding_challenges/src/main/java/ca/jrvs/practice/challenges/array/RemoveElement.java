package ca.jrvs.practice.challenges.array;

public class RemoveElement {

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
