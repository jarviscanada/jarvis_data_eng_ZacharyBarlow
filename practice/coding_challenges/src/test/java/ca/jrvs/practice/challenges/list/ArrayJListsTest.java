package ca.jrvs.practice.challenges.list;
import ca.jrvs.practice.challenges.list.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArrayJListsTest {

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void add() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);

    assertTrue(animals.add("Cat"));
    assertTrue(animals.add("Horse"));
    assertTrue(animals.add("Dog"));
    assertTrue(animals.add("Snake"));
  }

  @Test
  public void toArray() {
  }

  @Test
  public void size() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);
    assertTrue(animals.size() == 0);

    animals.add("Cat");
    assertTrue(animals.size() == 1);
  }

  @Test
  public void isEmpty() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);
    assertTrue(animals.isEmpty());

    animals.add("Cat");
    assertFalse(animals.isEmpty());
  }

  @Test
  public void indexOf() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);

    animals.add("Cat");
    animals.add("Dog");
    animals.add("Horse");

    assertTrue(animals.indexOf("Cat") == 0);
    assertTrue(animals.indexOf("Horse") == 2);
    assertTrue(animals.indexOf("Candy") == -1);
  }

  @Test
  public void contains() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);

    animals.add("Cat");
    animals.add("Dog");
    animals.add("Horse");

    assertTrue(animals.contains("Cat"));
    assertTrue(animals.contains("Dog"));
    assertFalse(animals.contains("Candy"));
  }

  @Test
  public void get() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);

    animals.add("Cat");
    assertTrue(animals.get(0).equals("Cat"));
  }

  @Test
  public void remove() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);
    animals.add("Cat");
    animals.add("Dog");
    animals.add("Horse");

    assertTrue(animals.remove(0).equals("Cat"));
  }

  @Test
  public void clear() {
    ArrayJLists<String> animals = new ArrayJLists<>(3);
    animals.add("Cat");
    animals.add("Dog");
    animals.add("Horse");

    animals.clear();

    assertTrue(animals.size() == 0);
  }
}