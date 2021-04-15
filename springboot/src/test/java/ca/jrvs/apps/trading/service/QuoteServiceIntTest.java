package ca.jrvs.apps.trading.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;
import java.util.Arrays;
import java.util.List;
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
public class QuoteServiceIntTest {

  @Autowired
  private QuoteService quoteService;

  @Autowired
  private QuoteDao quoteDao;

  @Before
  public void setUp() throws Exception {
    quoteDao.deleteAll();
  }

  @Test
  public void findIexQuoteByTicker() {
    Quote q2 = null;
    q2.setAskPrice(5d);
    q2.setAskSize(10);
    q2.setBidPrice(5d);
    q2.setBidSize(10);
    q2.setId("msft");
    q2.setLastPrice(5d);

    IexQuote iexQuote = quoteService.findIexQuoteByTicker(q2.getId());
    assertEquals(q2.getId(), iexQuote.getSymbol());
  }

  @Test
  public void updateMarketData() {
    Quote q2 = null;
    q2.setAskPrice(5d);
    q2.setAskSize(10);
    q2.setBidPrice(5d);
    q2.setBidSize(10);
    q2.setId("msft");
    q2.setLastPrice(5d);
    quoteDao.save(q2);
    quoteService.updateMarketData();

    Quote q3 = quoteDao.findById(q2.getId()).get(); // after update
    assertEquals(q2, q3);
  }

  @Test
  public void saveQuotes() {
    List<Quote> quotes = quoteService.saveQuotes(Arrays.asList("aapl", "fb", "mfst"));
    assertEquals(3, quotes.size());
    assertEquals("aapl", quotes.get(0).getId());
    assertEquals("fb", quotes.get(1).getId());
    assertEquals("mfst", quotes.get(2).getId());
  }

  @Test
  public void saveQuote() {
    Quote quote = quoteService.saveQuote("aapl");
    assertEquals("aapl", quote.getId());
  }
}