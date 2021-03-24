package ca.jrvs.practice.challenges.string;

public class RotateString {

  public boolean rotateString(String A, String B) {
    return A.length() == B.length() && (A + A).contains(B); // contains is O(n^2)
  }
}
