package ca.jrvs.practice.challenges.array;

import static org.junit.Assert.*;

import ca.jrvs.practice.challenges.Map.TwoSum;
import java.util.List;
import org.junit.Test;

public class RemoveElementTest {

  @Test
  public void removeElement() {
    RemoveElement removeElement = new RemoveElement();

    int[] n1 = {3, 2, 2, 3};
    int[] n2 = {0, 1, 2, 2, 3, 0, 4, 2};

    assertEquals(2, removeElement.removeElement(n1, 3));
    assertEquals(5, removeElement.removeElement(n2, 2));
  }
}