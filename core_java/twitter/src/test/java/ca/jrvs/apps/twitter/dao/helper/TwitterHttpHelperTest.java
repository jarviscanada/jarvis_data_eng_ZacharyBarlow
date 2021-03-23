package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

public class TwitterHttpHelperTest {

  static final Logger logger = LoggerFactory.getLogger(TwitterHttpHelperTest.class);
  TwitterHttpHelper tweet;

  @Before
  public void setUp() throws Exception {
    BasicConfigurator.configure();
    String cKey = System.getenv("cKey");
    String cSecret = System.getenv("cSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    logger.debug(cKey + ";" + cSecret + ";" + accessToken + ";" + tokenSecret + ";");
    tweet = new TwitterHttpHelper(cKey, cSecret, accessToken, tokenSecret);
  }

  @Test
  public void httpPost() throws Exception {
    HttpResponse response = tweet.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=TESTING3"));
    logger.debug(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void httpGet() throws Exception {
    HttpResponse response = tweet.httpGet(new URI("https://api.twitter.com/1.1/statuses/show.json?id=1374032223507116039"));
    logger.debug(EntityUtils.toString(response.getEntity()));
  }
}