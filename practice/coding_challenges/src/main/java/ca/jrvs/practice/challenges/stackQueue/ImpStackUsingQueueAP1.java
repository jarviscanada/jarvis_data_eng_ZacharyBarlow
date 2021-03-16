package ca.jrvs.practice.challenges.stackQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This approach implements the Stack using two Queues
 *
 * Methods:
 *  pop(): O(n)
 *  push(E e): O(1)
 *  peek(): O(1)
 * @param <E>
 */
public class ImpStackUsingQueueAP1<E> implements JStack<E> {

  private Queue<E> q1 = new LinkedList<>(); // main queue
  private Queue<E> q2 = new LinkedList<>(); // secondary queue
  private int size;
  private int main;

  ImpStackUsingQueueAP1() {
    this.size = 0;
    this.main = 1;
  }

  @Override
  public E pop() {
    E el;
    if (main == 1) {
      el = q1.poll();
      for(int i = 0; i < size - 2; i++) {
        q1.add(q2.poll());
      }
      main = 2;
    } else {
      el = q2.poll();
      for(int i = 0; i < size - 2; i++) {
        q2.add(q1.poll());
      }
      main = 1;
    }
    size--;
    return el;
  }

  @Override
  public void push(E e) {
    if (main == 1) {
      q1.add(e);
      if (q1.size() > 1) {
        q2.add(q1.poll());
      }
    } else {
      q2.add(e);
      if (q2.size() > 1) {
        q1.add(q2.poll());
      }
    }
    size++;
  }

  @Override
  public E peek() {
    if (main == 1) {
      return q1.peek();
    }
    return q2.peek();
  }

  public static void main(String[] args) {
    ImpStackUsingQueueAP1<String> mQ = new ImpStackUsingQueueAP1<>();
    mQ.push("1");
    mQ.push("2");
    mQ.push("3");

    System.out.println(mQ.size);
    System.out.println(mQ.peek());
    mQ.pop();
    System.out.println(mQ.peek());
  }
}
