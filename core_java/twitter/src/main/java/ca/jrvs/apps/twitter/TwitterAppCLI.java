package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import ca.jrvs.apps.twitter.util.TwitterJsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public class TwitterAppCLI {

  private Controller controller;
  private static final String USAGE = "TwitterAppCLI post/get/delete [tweet/id/ids] [longitude:latitude/x/x]";
  public TwitterAppCLI(Controller controller) {
    this.controller = controller;
  }


  public static void main(String[] args) {

    if (args.length < 1) {
      throw new IllegalArgumentException(USAGE);
    }

    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken,
        tokenSecret);
    CrdDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    TwitterController controller = new TwitterController(service);
    TwitterAppCLI app = new TwitterAppCLI(controller);

    app.run(args);
  }

  private void run(String[] args) {

    String req = args[0].toLowerCase();
    if (req == "get") {
      printTweet(controller.postTweet(args));
    } else if (req == "post") {
      printTweet(controller.showTweet(args));
    } else if (req == "delete") {
      List<Tweet> tweets = controller.deleteTweet(args);
      tweets.stream().forEach(this::printTweet);
    } else {
      throw new IllegalArgumentException(USAGE);
    }
  }

  private void printTweet(Tweet tweet) {
    try {
      System.out.println(TwitterJsonParser.toJson(tweet, true, true));
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable to parse object to Json!");
    }

  }
}
