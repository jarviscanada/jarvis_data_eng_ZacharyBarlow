package ca.jrvs.practice.challenges.string;

import static org.junit.Assert.*;

import org.junit.Test;

public class OnlyDigitsTest {

  @Test
  public void containsOnlyDigits() {
    OnlyDigits oD = new OnlyDigits();
    Boolean bool = oD.containsOnlyDigits("19203939");
    Boolean bool2 = oD.containsOnlyDigits("1920,3939");
    Boolean bool3 = oD.containsOnlyDigits("19203#939");
    Boolean bool4 = oD.containsOnlyDigits("192sa39");

    assertTrue(bool);
    assertTrue(bool2);
    assertTrue(bool3);
    assertTrue(bool4);
  }
}