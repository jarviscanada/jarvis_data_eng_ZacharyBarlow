package ca.jrvs.practice.challenges.practice;

public class RepetitivePractice {

  public int practiceMethod(int n) {
    int[] nums = new int[n];

    nums[0] = 1;
    nums[1] = 2;
    for (int i = 2; i < n; i++) {
      nums[i] = nums[i-1] + nums[i-2];
    }
    return nums[n-1];
  }
}
