package ca.jrvs.practice.challenges.Map;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwoSumTest {

  int[] n1, n2, n3;
  List<Integer> arr1, arr2, arr3;
  TwoSum ts = new TwoSum();

  @Before
  public void setUp() throws Exception {
    n1 = new int[]{2, 7, 11, 15};
    n2 = new int[]{3, 2, 4};
    n3 = new int[]{3, 3};
    arr1 = new ArrayList<>();
    arr1.add(0);
    arr1.add(1);

    arr2 = new ArrayList<>();
    arr2.add(1);
    arr2.add(2);

    arr3 = new ArrayList<>();
    arr3.add(0);
    arr3.add(1);
  }

  @Test
  public void approachOne() {
    assertEquals(arr1, ts.approachOne(n1, 9));
    assertEquals(arr2, ts.approachOne(n2, 6));
    assertEquals(arr3, ts.approachOne(n3, 6));
  }

  @Test
  public void approachTwo() {
  }

  @Test
  public void approachThree() {
    assertEquals(arr1, ts.approachOne(n1, 9));
    assertEquals(arr2, ts.approachOne(n2, 6));
    assertEquals(arr3, ts.approachOne(n3, 6));
  }
}