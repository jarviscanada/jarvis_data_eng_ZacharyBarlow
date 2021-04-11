package ca.jrvs.practice.challenges.string;

public class ValidPalindrome {

  public boolean twoPointers(String s) {
    int r = s.length() - 1;
    int l = 0;

    while (l < r) {
      if (!Character.isLetterOrDigit(s.charAt(l))) {
        l++;
      } else if (!Character.isLetterOrDigit(s.charAt(r))) {
        r--;
      } else {
        if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
          return false;
        }
        l++;
        r--;
      }
    }
    return true;
  }

  public boolean recursionApproach(String str) {
    int r = str.length() - 1;
    int l = 0;
    if (str.length() < 2) {
      return true;
    }
    if (!Character.isLetterOrDigit(str.charAt(l))) {
      l++;
    } else if (!Character.isLetterOrDigit(str.charAt(r))) {
      r--;
    }
    if (Character.toLowerCase(str.charAt(l)) == Character.toLowerCase(str.charAt(r))) {
      if (l == r) {
        return true;
      }
      return recursionApproach(str.substring(l + 1, r));
    }
    return false;
  }
}
