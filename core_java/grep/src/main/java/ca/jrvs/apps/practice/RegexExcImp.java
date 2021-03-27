package ca.jrvs.apps.practice;

import java.util.regex.*;

public class RegexExcImp implements RegexExc {

  public boolean matchJpeg(String filename) {
    return Pattern.matches("([\\w]+(\\.(?i)(jpe?g))$)", filename);
  }

  public boolean matchIp(String ip) {
    return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", ip);
  }

  public boolean isEmptyLine(String line) {
    return Pattern.matches("^(\\s)*$", line);
  }
}
