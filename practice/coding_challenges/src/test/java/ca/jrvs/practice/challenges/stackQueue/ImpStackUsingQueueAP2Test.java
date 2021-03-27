package ca.jrvs.practice.challenges.stackQueue;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImpStackUsingQueueAP2Test {

  @Test
  public void pop() {
    ImpStackUsingQueueAP2<String> mQ = new ImpStackUsingQueueAP2<>();
    mQ.push("1");
    mQ.push("2");
    mQ.push("3");

    assertEquals("3", mQ.pop());
  }

  @Test
  public void peek() {
    ImpStackUsingQueueAP2<String> mQ = new ImpStackUsingQueueAP2<>();
    mQ.push("1");
    mQ.push("2");
    mQ.push("3");

    assertEquals("3", mQ.peek());
  }
}