package ca.jrvs.practice.challenges.Map;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidParenthesesTest {

  @Test
  public void validCheck() {
    ValidParentheses check = new ValidParentheses();
    assertTrue(check.validCheck("([]{[()]})"));
    assertTrue(check.validCheck("(){}[]"));
    assertFalse(check.validCheck("([]{[()]}"));
  }
}