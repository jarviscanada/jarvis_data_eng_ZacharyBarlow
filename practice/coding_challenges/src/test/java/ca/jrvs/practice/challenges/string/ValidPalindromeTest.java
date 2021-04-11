package ca.jrvs.practice.challenges.string;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidPalindromeTest {

  @Test
  public void twoPointers() {
    ValidPalindrome validPalindrome = new ValidPalindrome();
    String s = "A man, a plan, a canal: Panama";
    String s1 = "race a car";
    String bad = "not a palindrome";
    assertTrue(validPalindrome.twoPointers(s));
    assertFalse(validPalindrome.twoPointers(s1));
    assertFalse(validPalindrome.recursionApproach(bad));
  }

  @Test
  public void recursionApproach() {
    ValidPalindrome validPalindrome = new ValidPalindrome();
    String s = "A man a plan a canal Panama";
    String s1 = "race a car";
    String bad = "not a palindrome";
    assertTrue(validPalindrome.recursionApproach(s));
    assertFalse(validPalindrome.recursionApproach(s1));
    assertFalse(validPalindrome.recursionApproach(bad));
  }
}