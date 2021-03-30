package ca.jrvs.apps.twitter.controller;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.CreateTweetUtil;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TwitterControllerIntTest {
  Service service;
  Controller controller;
  String text;

  @Before
  public void setUp() throws Exception {
    String cKey = System.getenv("consumerKey");
    String cSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(cKey, cSecret, accessToken, tokenSecret);

    CrdDao dao = new TwitterDao(httpHelper);
    service = new TwitterService(dao);
    controller = new TwitterController(service);

    String hashtag = "#abc";
    text = "some text " + hashtag + " " + System.currentTimeMillis();
  }

  @Test
  public void postTweet() {
    Double lng = 33d;
    Double lat = 33d;

    Tweet posted = controller.postTweet(new String[]{"post", text, "33:33"});
    // check valid
    assertNotNull(posted);
    assertEquals(lng, posted.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, posted.getCoordinates().getCoordinates().get(1));
    assertEquals(text, posted.getText());
  }

  @Test
  public void showTweet() {
    Double lng = 50d;
    Double lat = 50d;
    Tweet t1 = CreateTweetUtil.createTweet("test", lng, lat);
    Tweet tweet = controller.showTweet(new String[]{"get", t1.getIdStr(), "fields"});
    // check valid
    assertNotNull(tweet);
    assertEquals(lng, tweet.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
    assertEquals(text, tweet.getText());
  }

  @Test
  public void deleteTweet() {
    Double lng = 50d;
    Double lat = 50d;

    Tweet t1 = CreateTweetUtil.createTweet("test", lng, lat);
    Tweet t2 = CreateTweetUtil.createTweet("test", lng, lat);
    String ids = t1.getIdStr() + t2.getIdStr();

    List<Tweet> dTweets = controller.deleteTweet(new String[]{"delete", ids});
    // check valid
    assertNotNull(dTweets);
    assertEquals(lng, dTweets.get(0).getCoordinates().getCoordinates().get(0));
    assertEquals(lat, dTweets.get(0).getCoordinates().getCoordinates().get(1));
    assertEquals(text, dTweets.get(0).getText());
  }
}