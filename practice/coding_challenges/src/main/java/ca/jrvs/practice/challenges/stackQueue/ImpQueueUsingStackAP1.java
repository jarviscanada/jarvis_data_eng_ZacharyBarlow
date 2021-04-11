package ca.jrvs.practice.challenges.stackQueue;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class ImpQueueUsingStackAP1<E> implements JQueue<E> {

  private Deque<E> s1 = new LinkedList<>();
  private Deque<E> s2 = new LinkedList<>();
  /**
   * This is equivalent enqueue operation in Queue ADT
   * <p>
   * Inserts the specified element into the queue represented by this deque (in other words, at the
   * tail of this deque) if it is possible to do so immediately without violating capacity
   * restrictions, returning {@code true} upon success and throwing an {@code IllegalStateException}
   * if no space is currently available.
   *
   * @param e the element to add
   * @return {@code true} (as specified by {@link Collection#add})
   * @throws NullPointerException if the specified element is null and this deque does not permit
   *                              null elements
   */

  @Override
  public boolean add(E e) {
    if (e == null) {
      throw new NullPointerException();
    }
    while (!s1.isEmpty()) {
      s2.add(s1.pop());
    }
    s1.add(e);
    while (!s2.isEmpty()) {
      s1.add(s2.pop());
    }
    return true;
  }

  /**
   * This is equivalent dequeue operation in Queue ADT
   * <p>
   * Retrieves and removes the head of the queue represented by this deque (in other words, the
   * first element of this deque).
   *
   * @return the head of the queue represented by this deque
   * @throws NoSuchElementException if this deque is empty
   */
  @Override
  public E remove() {
    return s1.pop();
  }

  /**
   * Retrieves, but does not remove, the head of the queue represented by this deque (in other
   * words, the first element of this deque), or returns {@code null} if this deque is empty.
   *
   * @return the head of the queue represented by this deque, or {@code null} if this deque is empty
   */
  @Override
  public E peek() {
    return s1.peek();
  }
}
