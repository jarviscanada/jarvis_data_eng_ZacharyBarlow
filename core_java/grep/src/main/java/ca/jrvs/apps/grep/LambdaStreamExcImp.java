package ca.jrvs.apps.grep;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings).map(String::toUpperCase);
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(s -> Pattern.matches(pattern, s));
  }

  @Override
  public IntStream createIntStream(int[] arr) {
    IntStream intStream = Arrays.stream(arr);
    return intStream;
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    IntStream intStream = IntStream.range(start, end);
    return intStream;
  }

  @Override
  public DoubleStream squareRootIncStream(IntStream intStream) {
    return intStream.mapToDouble(i -> Math.sqrt(i));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i -> i % 2 != 0);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    Consumer<String> printer = (String msg) -> System.out.println(prefix + msg + suffix);
    return printer;
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    Arrays.stream(messages).forEach(m -> printer.accept(m));
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).mapToObj(Integer::toString).forEach(s -> printer.accept(s));
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    Stream<Integer> st = ints.flatMap(l -> l.stream().map(i -> i * i));
    return st;
  }
}
