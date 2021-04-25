package ca.jrvs.practice.challenges.string;

public class OnlyDigits {

  public boolean containsOnlyDigits(String str) {
    // ascii codes 48 - 57
    char[] cArr = str.toCharArray();

    for (Character c : cArr) {
      if (c < 48 && c > 57) {
        return false;
      }
    }
    return true;
  }
}
