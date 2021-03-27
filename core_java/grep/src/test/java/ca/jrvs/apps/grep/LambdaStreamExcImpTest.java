package ca.jrvs.apps.grep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class LambdaStreamExcImpTest {

  LambdaStreamExcImp lambdaStreamExcImp;

  @Before
  public void setUp() throws Exception {
    lambdaStreamExcImp = new LambdaStreamExcImp();
  }

  @Test
  public void createStrStream() {
    Stream<String> stream = lambdaStreamExcImp.createStrStream("Test1", "Test2");
    List<String> strings = Arrays.asList("Test1", "Test2");
    assertEquals(strings, stream.collect(Collectors.toList()));
  }

  @Test
  public void toUpperCase() {
    Stream<String> stream = lambdaStreamExcImp.toUpperCase("Test1", "Test2");
    List<String> strings = Arrays.asList("TEST1", "TEST2");
    assertEquals(strings, stream.collect(Collectors.toList()));
  }

  @Test
  public void filter() {
    Stream<String> stream = lambdaStreamExcImp.createStrStream("Test1", "Test2");
    Stream<String> filtered = lambdaStreamExcImp.filter(stream, ".*Test.*");
    List<String> strings = Arrays.asList("Test1", "Test2");
    assertEquals(strings, filtered.collect(Collectors.toList()));
  }

  @Test
  public void createIntStream() {
    int[] arr = {1, 2, 3, 4, 5};
    IntStream intStream = lambdaStreamExcImp.createIntStream(arr);
    assertEquals(15, intStream.sum());
  }

  @Test
  public void toList() {
    Stream<String> stream = lambdaStreamExcImp.createStrStream("Test1", "Test2");
    List<String> str = lambdaStreamExcImp.toList(stream);

    assertTrue(str.contains("Test2"));
    assertTrue(str.contains("Test2"));
  }

  @Test
  public void testToList() {
    int[] arr = {1, 2, 3, 4, 5};
    IntStream intStream = lambdaStreamExcImp.createIntStream(arr);
    List<Integer> ints = lambdaStreamExcImp.toList(intStream);

    assertTrue(ints.contains(1));
    assertTrue(ints.contains(2));
    assertTrue(ints.contains(3));
    assertTrue(ints.contains(4));
    assertTrue(ints.contains(5));
  }

  @Test
  public void testCreateIntStream() {
    IntStream intStream = lambdaStreamExcImp.createIntStream(1, 3);
    assertEquals(3, intStream.sum());
  }

  @Test
  public void squareRootIncStream() {
    int[] arr = {1, 4, 9, 16};
    IntStream intStream = lambdaStreamExcImp.createIntStream(arr);
    List<String> dbls = lambdaStreamExcImp.squareRootIncStream(intStream)
        .mapToObj(Double::toString)
        .collect(Collectors.toList());

    assertTrue(dbls.contains("1.0"));
    assertTrue(dbls.contains("2.0"));
    assertTrue(dbls.contains("3.0"));
    assertTrue(dbls.contains("4.0"));
  }

  @Test
  public void getOdd() {
    IntStream intStream = lambdaStreamExcImp.createIntStream(1, 4);
    List<Integer> odd = lambdaStreamExcImp.getOdd(intStream).boxed().collect(Collectors.toList());
    List<Integer> test = Arrays.asList(1, 3);

    assertEquals(test, odd);
    assertTrue(odd.contains(1));
    assertFalse(odd.contains(2));
  }

  @Test
  public void getLambdaPrinter() {
    Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("pre>", "<suf");
    printer.accept("test");
  }

  @Test
  public void printMessages() {
    String[] arr = {"1", "2", "3", "4"};
    Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("pre>", "<suf");
    lambdaStreamExcImp.printMessages(arr, printer);
  }

  @Test
  public void printOdd() {
    Consumer<String> printer = lambdaStreamExcImp.getLambdaPrinter("pre>", "<suf");
    IntStream intStream = lambdaStreamExcImp.createIntStream(1, 4);
    lambdaStreamExcImp.printOdd(intStream, printer);
  }

  @Test
  public void flatNestedInt() {
    List<Integer> arr1 = Arrays.asList(1, 2, 3, 4);
    List<Integer> arr2 = Arrays.asList(5, 6, 7, 8);

    List<List<Integer>> arr3 = Arrays.asList(arr1, arr2);
    List<Integer> flatInts = lambdaStreamExcImp.flatNestedInt(arr3.stream())
        .collect(Collectors.toList());

    assertTrue(flatInts.contains(1));
    assertTrue(flatInts.contains(4));
    assertTrue(flatInts.contains(9));
    assertTrue(flatInts.contains(36));
  }
}