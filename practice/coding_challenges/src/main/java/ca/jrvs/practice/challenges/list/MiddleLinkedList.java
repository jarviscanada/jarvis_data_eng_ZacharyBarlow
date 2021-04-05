package ca.jrvs.practice.challenges.list;

public class MiddleLinkedList {
/*
  Given a non-empty, singly linked list with head node head, return a middle node of linked list.

  If there are two middle nodes, return the second middle node.
 */

  /**
   *
   * @param head
   * @return
   */
  public Node middleNode(Node head) {
    Node curr = head;
    Node prev = head;
    while (curr.next != null && curr != null) {
      prev = prev.next;
      curr = curr.next.next;
    }
    return prev;
  }

  private static class Node {
    int val;
    MiddleLinkedList.Node next;

    public Node (){}

    public Node (int val) {
      this.val = val;
    }
    public Node (int val, MiddleLinkedList.Node next) {
      this.val = val;
      this.next = next;
    }
  }
}
