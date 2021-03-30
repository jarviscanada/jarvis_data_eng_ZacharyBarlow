package ca.jrvs.practice.challenges.list;

public class NthNodeFromEnd {

  public Node removeNthFromEnd(Node head, int n) {
    Node d = new Node(0);
    d.next = head;
    Node first = d;
    Node second = d;
    for (int i = 0; i < n; i++) {
      first = first.next;
    }
    while (first != null) {
      first = first.next;
      second = second.next;
    }
    second.next = second.next.next;
    return second.next;
  }


  private static class Node {
    int val;
    Node next;

    public Node (){}

    public Node (int val) {
      this.val = val;
    }
    public Node (int val, Node next) {
      this.val = val;
      this.next = next;
    }
  }
}
