package ca.jrvs.practice.challenges.list;

public class LinkedJList<E> implements JList<E> {
  transient int size = 0;

  /**
   * Pointer to first node.
   * Invariant: (first == null && last == null) ||
   *            (first.prev == null && first.item != null)
   */
  transient Node<E> first;

  /**
   * Pointer to last node.
   * Invariant: (first == null && last == null) ||
   *            (last.next == null && last.item != null)
   */
  transient Node<E> last;

  /**
   * Constructs an empty list.
   */
  public LinkedJList() {
  }

  /**
   * Appends the specified element to the end of this list (optional operation).
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt>
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean add(E e) {
    if (e == null) {
      throw new NullPointerException();
    }

    Node<E> node = new Node<E>(last, e, null);
    if (first == null) {
      first = node;
    } else {
      last.next = node;
    }
    last = node;
    size++;
    return true;
  }/**
   * Appends the specified element to the end of this list (optional operation).
   *
   * @param e element to be appended to this list
   * @return <tt>true</tt>
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */

  public boolean addFront(E e) {
    if (e == null) {
      throw new NullPointerException();
    }

    Node<E> node = new Node<E>(null, e, null);
    if (first != null) {
      first.prev = node;
      node.next = first;
    }
    first = node;
    return true;
  }

  /**
   * Returns an array containing all of the elements in this list in proper sequence (from first to
   * last element).
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all of the elements in this list in proper sequence
   */
  @Override
  public Object[] toArray() {
    return new Object[0];
  }

  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index <tt>i</tt> such
   * that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param o
   */
  @Override
  public int indexOf(Object o) {
    int i = 0;
    if (o == null) {
      for (Node<E> n = first; n != null; n = n.next) {
        if (n.item == null) {
          return i;
        }
        i++;
      }
    } else {
      for (Node<E> n = first; n != null; n = n.next) {
        if (o.equals(n.item)) {
          return i;
        }
        i++;
      }
    }
    return -1;
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element. More formally, returns
   * <tt>true</tt> if and only if this list contains at least one element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * @param o element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean contains(Object o) {
    return indexOf(o) >= 0;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range (<tt>index &lt; 0 || index &gt;=
   *                                   size()</tt>)
   */
  @Override
  public E get(int index) {
    indexRange(index);

    Node<E> n;
    if (index > (size >> 1)) {
      n = last;
      for (int i = size; i > index; i--) {
        n = n.prev;
      }
    } else {
      n = first;
      for (int i = 0; i < index; i++) {
        n = n.next;
      }
    }
    return n.item;
  }

  /**
   * Removes the element at the specified position in this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices).
   *
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public E remove(int index) {
    indexRange(index);
    Node<E> n;
    if (index > (size >> 1)) {
      n = last;
      for (int i = size; i > index; i--) {
        n = n.prev;
      }
    } else {
      n = first;
      for (int i = 0; i < index; i++) {
        n = n.next;
      }
    }
    n.prev.next = n.next;
    n.next.prev = n.prev;
    size--;
    return n.item;
  }

  /**
   * Removes all of the elements from this list (optional operation). The list will be empty after
   * this call returns.
   */
  @Override
  public void clear() {
    first.next = null;
    last.prev = null;

    first = null;
    last = null;
  }

  private void indexRange(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }
  }

  private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(Node<E> prev, E element, Node<E> next) {
      this.item = element;
      this.next = next;
      this.prev = prev;
    }
  }
}
