package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TwitterHttpHelper implements HttpHelper {

  /**
   * Dependencies are specified as private member variables
   */
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  /**
   * Constructor Setup dependencies using secrets
   *
   * @param consumerKey
   * @param consumerSecret
   * @param accessToken
   * @param tokenSecret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken,
      String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    /**
     * Default = single connection. Discuss source code if time permit
     */
    httpClient = new DefaultHttpClient();
  }

  /**
   * Execute a HTTP Post call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpPost(URI uri) {
    try {
      HttpPost request = new HttpPost(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } catch (IOException | OAuthExpectationFailedException | OAuthMessageSignerException |
        OAuthCommunicationException ex) {
      throw new RuntimeException();
    }
  }

  /**
   * Execute a HTTP Get call
   *
   * @param uri
   * @return
   */
  @Override
  public HttpResponse httpGet(URI uri) {
    try {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    } catch (IOException | OAuthExpectationFailedException | OAuthMessageSignerException |
        OAuthCommunicationException ex) {
      throw new RuntimeException();
    }
  }

  /**
   * Main Class
   *
   * @param args
   */
  public static void main(String[] args) throws Exception {
    String cKey = System.getenv("cKey");
    String cSecret = System.getenv("cSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    TwitterHttpHelper tweet = new TwitterHttpHelper(cKey, cSecret, accessToken, tokenSecret);
    HttpResponse response = tweet.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=TESTING"));

    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}
