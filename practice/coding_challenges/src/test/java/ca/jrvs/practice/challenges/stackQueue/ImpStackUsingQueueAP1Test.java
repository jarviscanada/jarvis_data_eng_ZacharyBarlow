package ca.jrvs.practice.challenges.stackQueue;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImpStackUsingQueueAP1Test {

  @Test
  public void pop() {
    ImpStackUsingQueueAP1<String> mQ = new ImpStackUsingQueueAP1<>();
    mQ.push("1");
    mQ.push("2");
    mQ.push("3");
    assertEquals("3", mQ.pop());
  }

  @Test
  public void peek() {
    ImpStackUsingQueueAP1<String> mQ = new ImpStackUsingQueueAP1<>();
    mQ.push("1");
    mQ.push("2");
    mQ.push("Test");
    assertEquals("Test", mQ.peek());
  }
}