package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelperTest;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.CreateTweetUtil;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterServiceIntTest {

  static final Logger logger = LoggerFactory.getLogger(TwitterHttpHelperTest.class);
  Service service;
  Tweet posted;
  String text;

  @Before
  public void setUp() throws Exception {
    BasicConfigurator.configure();
    String cKey = System.getenv("consumerKey");
    String cSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    HttpHelper httpHelper = new TwitterHttpHelper(cKey, cSecret, accessToken, tokenSecret);

    CrdDao dao = new TwitterDao(httpHelper);
    service = new TwitterService(dao);

    String hashtag = "#abc";
    text = "some text " + hashtag + " " + System.currentTimeMillis();
    Tweet valid = CreateTweetUtil.createTweet(text, 33d, 33d);
    posted = service.postTweet(valid);
  }

  @Test
  public void postTweet() {
    Double lng = 33d;
    Double lat = 33d;
    // check valid
    assertNotNull(posted);
    assertEquals(lng, posted.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, posted.getCoordinates().getCoordinates().get(1));
    assertEquals(text, posted.getText());
  }

  @Test
  public void showTweet() {
    // valid id
    Tweet vT = service.showTweet(posted.getIdStr(), new String[]{});
    assertEquals(posted.getIdStr(), vT.getIdStr());
    Double lng = 33d;
    Double lat = 33d;
    // check valid
    assertNotNull(vT);
    assertEquals(lng, vT.getCoordinates().getCoordinates().get(0));
    assertEquals(lat, vT.getCoordinates().getCoordinates().get(1));
    assertEquals(text, vT.getText());
  }

  @Test
  public void deleteTweets() {
    String[] ids = {posted.getIdStr()};

    List<Tweet> tweets = service.deleteTweets(ids);
    assertTrue(tweets.size() == 4);
    assertEquals(posted.getIdStr(), tweets.get(0).getIdStr());
  }
}