package ca.jrvs.practice.challenges.Map;

import java.util.Map;

public class CompareTwoMaps {

  public <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V> m2) {
    return m1.equals(m2);
  }
}
