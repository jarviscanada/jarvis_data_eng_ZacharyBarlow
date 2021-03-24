package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import java.util.Arrays;

public class CreateTweetUtil {

  public Tweet createTweet(String text, Double lng, Double lat) {
    Coordinates coords = new Coordinates();
    coords.setCoordinates(Arrays.asList(lng, lat));
    coords.setType("Point");

    Tweet tweet = new Tweet();
    PercentEscaper escaper = new PercentEscaper("", false);

    tweet.setText(escaper.escape(text));
    tweet.setCoordinates(coords);
    return tweet;
  }
}
