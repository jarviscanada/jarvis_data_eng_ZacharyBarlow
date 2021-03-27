package ca.jrvs.apps.twitter.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.CreateTweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

  @Mock
  Service service;

  @InjectMocks
  Controller controller;

  @Test
  public void postTweet() {
    when(service.postTweet(any()))
        .thenReturn(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));
    // args != 3
    // coords length != 2
    // invalid coord

  }

  @Test
  public void showTweet() {
    when(service.postTweet(any()))
        .thenReturn(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));
    // args < 2
  }

  @Test
  public void deleteTweet() {
    when(service.postTweet(any()))
        .thenReturn(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));
    // args < 2
  }
}
