package ca.jrvs.practice.challenges.Map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Employee {

  private int id;
  private String name;
  private int age;
  private long salary;

  public Employee(){}

  public Employee(int id, String name, int age, long salary) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.salary = salary;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Employee employee = (Employee) o;
    return id == employee.id && age == employee.age && salary == employee.salary && Objects
        .equals(name, employee.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, age, salary);
  }

  public static void main(String[] args) {
    HashMap<Employee, List<String>> em = new HashMap<>();

    List<String> employers = Arrays.asList("IBM", "TD", "RBC");
    Employee em1 = new Employee(1, "Jeff", 28, 100000);

    List<String> employers2 = Arrays.asList("IBM", "TD", "CIBC");
    Employee em2 = new Employee(2, "Bob", 24, 100000);

    em.put(em1, employers);
    em.put(em2, employers2);

    System.out.println("Hashcode: " + em1.hashCode());
    System.out.println("Value: " + em.get(em1).toString());

    System.out.println("Hashcode: " + em2.hashCode());
    System.out.println("Value: " + em.get(em2).toString());
  }
}
