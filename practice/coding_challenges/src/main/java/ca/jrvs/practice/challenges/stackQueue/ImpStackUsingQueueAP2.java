package ca.jrvs.practice.challenges.stackQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This implementation, implements a stack using 1 queue
 *
 * Methods:
 *  pop(): O(1)
 *  push(E e): O(n)
 *  peek(): O(1)
 * @param <E>
 */
public class ImpStackUsingQueueAP2<E> implements JStack<E> {

  private Queue<E> queue = new LinkedList<>(); ;
  private int size;

  ImpStackUsingQueueAP2() {
    this.size = 0;
  }

  @Override
  public E pop() {
    size--;
    return queue.poll();
  }

  @Override
  public void push(E e) {
    if (e == null) {
      throw new NullPointerException();
    }
    queue.add(e);
    for (int i = 0; i < size; i++) {
      queue.add(queue.poll());
    }
    size++;
  }

  @Override
  public E peek() {
    return queue.peek();
  }

  public static void main(String[] args) {
    ImpStackUsingQueueAP2<Integer> mQ = new ImpStackUsingQueueAP2<>();
    mQ.push(1);
    mQ.push(2);
    mQ.push(3);

    System.out.println(mQ.size);
    System.out.println(mQ.peek());
    mQ.pop();
    System.out.println(mQ.size);
    mQ.push(4);
    System.out.println(mQ.size);
    System.out.println(mQ.peek());
  }
}
