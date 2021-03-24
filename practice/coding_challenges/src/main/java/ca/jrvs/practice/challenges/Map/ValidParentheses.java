package ca.jrvs.practice.challenges.Map;

import java.util.HashMap;
import java.util.Stack;

/**
 * using hash map and stack/LinkedList
 */
public class ValidParentheses {

  private HashMap<Character, Character> map;

  public ValidParentheses() {
    map = new HashMap<Character, Character>();
    map.put(')', '(');
    map.put(']', '[');
    map.put('}', '{');
  }

  public boolean validCheck(String s) {
    Stack<Character> stack = new Stack<>();

    for (int i = 0, l = s.length(); i < l; i++) {
      char c = s.charAt(i);

      if (map.containsKey(c)) {

        char top = stack.isEmpty() ? '.' : stack.pop();

        if (map.get(c) != top) {
          return false;
        }
      } else {
        stack.add(c);
      }
    }
    return stack.isEmpty();
  }
}
