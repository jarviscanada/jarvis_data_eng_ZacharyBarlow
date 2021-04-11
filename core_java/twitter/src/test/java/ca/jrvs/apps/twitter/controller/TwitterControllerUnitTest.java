package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.CreateTweetUtil;
import java.util.ArrayList;
import java.util.List;
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

  Double lng = 50d;
  Double lat = 50d;

  @Test
  public void postTweet() {
    when(service.postTweet(any()))
        .thenReturn(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));
    // args != 3
    try {
      controller.postTweet(new String[]{"post", "This is a tweet"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
    // coords length != 2
    try {
      controller.postTweet(new String[]{"post", "This is a tweet", "45:56:45"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
    // invalid coord
    try {
      controller.postTweet(new String[]{"post", "This is a tweet", "45:"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
    // invalid coord
    try {
      controller.postTweet(new String[]{"post", "This is a tweet", "45:45g"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }

    Tweet tweet = controller.postTweet(new String[]{"post", "This is a tweet", "45:45"});
    assertEquals(lng, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    assertEquals("test", tweet.getText());
  }

  @Test
  public void showTweet() {
    when(service.showTweet(anyString(), any()))
        .thenReturn(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));
    // args < 2
    try {
      controller.showTweet(new String[]{"get"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
    Tweet tweet = controller.showTweet(new String[]{"post", "1375498033895444489", "45:45"});
    assertEquals(lng, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    assertEquals("test", tweet.getText());
  }

  @Test
  public void deleteTweet() {
    List<Tweet> tweets = new ArrayList<>();
    tweets.add(CreateTweetUtil.createTweet("test", new Double(50), new Double(50)));

    when(service.deleteTweets(any())).thenReturn(tweets);
    // args < 2
    try {
      controller.deleteTweet(new String[]{"delete"});
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(true);
    }
    List<Tweet> dTweets = controller.deleteTweet(new String[]{"delete"});
    assertEquals(lng, dTweets.get(0).getCoordinates().getCoordinates().get(0));
    assertEquals(lat, dTweets.get(0).getCoordinates().getCoordinates().get(1));
    assertEquals("test", dTweets.get(0).getText());
  }
}
