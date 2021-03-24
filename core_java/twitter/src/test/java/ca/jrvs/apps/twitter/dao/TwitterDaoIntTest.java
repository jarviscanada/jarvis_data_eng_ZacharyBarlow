package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TwitterDaoIntTest {

  @BeforeClass
  public void setUp() throws Exception {
    BasicConfigurator.configure();
    String cKey = System.getenv("cKey");
    String cSecret = System.getenv("cSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
  }

  @Test
  public void create() {
  }

  @Test
  public void findById() {
  }

  @Test
  public void deleteById() {
  }
}