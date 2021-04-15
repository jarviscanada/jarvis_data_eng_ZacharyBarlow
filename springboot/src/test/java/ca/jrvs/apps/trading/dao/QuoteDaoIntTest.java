package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

  @Autowired
  private QuoteDao quoteDao;

  private Quote savedQuote;

  @Before
  public void insertOne() throws Exception {
    savedQuote.setAskPrice(10d);
    savedQuote.setAskSize(10);
    savedQuote.setBidPrice(10.2d);
    savedQuote.setBidSize(10);
    savedQuote.setId("aapl");
    savedQuote.setLastPrice(10.1d);
    quoteDao.save(savedQuote);
  }

  @Test
  public void findById() {
    Optional<Quote> quote = quoteDao.findById("aapl");
    assertEquals("aapl", quote.get().getId());
    assertEquals(new Double(10.1), quote.get().getLastPrice());
  }

  @Test
  public void existsById() {
    assertTrue(quoteDao.existsById("aapl"));
    assertFalse(quoteDao.existsById("fb"));
  }

  @Test
  public void findAll() {
    List<Quote> quotes = quoteDao.findAll();
    assertEquals(savedQuote, quotes.get(0));
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void saveAll() {
    Quote q1 = null;
    q1.setAskPrice(5d);
    q1.setAskSize(10);
    q1.setBidPrice(5d);
    q1.setBidSize(10);
    q1.setId("fb");
    q1.setLastPrice(5d);

    Quote q2 = null;
    q2.setAskPrice(5d);
    q2.setAskSize(10);
    q2.setBidPrice(5d);
    q2.setBidSize(10);
    q2.setId("msft");
    q2.setLastPrice(5d);

    List<Quote> quotes = quoteDao.saveAll(Arrays.asList(q1, q2));
    Quote rq1 = quoteDao.findById("fb").get();
    Quote rq2 = quoteDao.findById("msft").get();
    assertEquals(quotes.get(0), rq1);
    assertEquals(quotes.get(1), rq2);
  }

  @Test
  public void save() {
    Quote q = null;
    q.setAskPrice(5d);
    q.setAskSize(10);
    q.setBidPrice(5d);
    q.setBidSize(10);
    q.setId("fb");
    q.setLastPrice(5d);
    Quote testQ = quoteDao.save(q);

    assertEquals(testQ, q);
  }

  @Test
  public void count() {
    assertEquals(1, quoteDao.count());
  }

  @Test
  public void deleteById() {
    quoteDao.deleteById(savedQuote.getId());
    assertEquals(0, quoteDao.count());
  }

  @Test
  public void deleteAll() {
    Quote q1 = null;
    q1.setAskPrice(5d);
    q1.setAskSize(10);
    q1.setBidPrice(5d);
    q1.setBidSize(10);
    q1.setId("fb");
    q1.setLastPrice(5d);

    Quote q2 = null;
    q2.setAskPrice(5d);
    q2.setAskSize(10);
    q2.setBidPrice(5d);
    q2.setBidSize(10);
    q2.setId("msft");
    q2.setLastPrice(5d);

    quoteDao.saveAll(Arrays.asList(q1, q2));
    assertEquals(3, quoteDao.count());
    quoteDao.deleteAll();
    assertEquals(0, quoteDao.count());
  }

  @After
  public void deleteOne() throws Exception {
    quoteDao.deleteById(savedQuote.getId());
  }
}