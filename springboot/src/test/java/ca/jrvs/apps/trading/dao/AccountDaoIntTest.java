package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
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
public class AccountDaoIntTest {

  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TraderDao traderDao;

  private Account account;
  private Trader trader;

  @Before
  public void setUp() throws Exception {

    trader = new Trader();
    trader.setId(1);
    trader.setFirstName("Jeff");
    trader.setLastName("Test");
    trader.setCountry("CAN");
    trader.setDob(new Date(System.currentTimeMillis()));
    trader.setEmail("jeff@example.com");
    traderDao.save(trader);

    account = new Account();
    account.setId(1);
    account.setTrader_id(trader.getId());
    account.setAmount(10d);
    accountDao.save(account);
  }

  @After
  public void tearDown() throws Exception {
    accountDao.deleteAll();
    traderDao.deleteById(trader.getId());
  }

  @Test
  public void getTableName() {
    assertEquals("account", accountDao.getTableName());
  }

  @Test
  public void getIdColumnName() {
    assertEquals("id", accountDao.getIdColumnName());
  }

  @Test
  public void getEntityClass() {
    assertEquals(Account.class, accountDao.getEntityClass());
  }

  @Test
  public void saveAll() {
    Account account1 = new Account();
    account1.setId(2);
    account1.setTrader_id(trader.getId());
    account1.setAmount(10d);

    Account account2 = new Account();
    account2.setId(3);
    account2.setTrader_id(trader.getId());
    account2.setAmount(10d);

    accountDao.saveAll(Arrays.asList(account1, account2));
    assertEquals(3, accountDao.count());

    List<Account> accounts = accountDao.saveAll(Arrays.asList(account1, account2));
    Account rq1 = accountDao.findById(2).get();
    Account rq2 = accountDao.findById(3).get();

    assertEquals(accounts.get(0).getId(), rq1.getId());
    assertEquals(accounts.get(1).getId(), rq2.getId());
  }

  @Test
  public void save() {
    Account account1 = new Account();
    account1.setId(2);
    account1.setTrader_id(trader.getId());
    account1.setAmount(10d);

    Account acc = accountDao.save(account1);
    assertEquals(new Integer(2), acc.getId());
    assertEquals(trader.getId(), acc.getTrader_id());
    assertEquals(new Double(10), acc.getAmount());
  }

  @Test
  public void findById() {
    Account acc = accountDao.findById(account.getId()).get();
  }

  @Test
  public void existsById() {
  }

  @Test
  public void findAll() {
  }

  @Test
  public void findAllById() {
  }

  @Test
  public void deleteById() {
  }

  @Test
  public void count() {
  }

  @Test
  public void deleteAll() {
  }
}