package ca.jrvs.practice.challenges.array;

import static org.junit.Assert.*;
import org.junit.Test;

public class findLargestSmallestTest {

  @Test
  public void findLargest1() {
    FindLargestSmallest f = new FindLargestSmallest();
    int[] nums = {1, 3, -1, 10, 4, 20, -6};
    assertEquals(20, f.findLargest1(nums));
  }

  @Test
  public void findLargest2() {
    FindLargestSmallest f = new FindLargestSmallest();
    int[] nums = {1, 3, -1, 10, 4, 20, -6};
    assertEquals(20, f.findLargest2(nums));
  }

  @Test
  public void findLargest3() {
    FindLargestSmallest f = new FindLargestSmallest();
    int[] nums = {1, 3, -1, 10, 4, 20, -6};
    assertEquals(20, f.findLargest3(nums));
  }
}