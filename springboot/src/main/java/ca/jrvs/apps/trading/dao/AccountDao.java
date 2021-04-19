package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {

  private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);

  private final String TABLE_NAME = "account";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public AccountDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  @Override
  public SimpleJdbcInsert getSimpleJdbcInsert() {
    return simpleJdbcInsert;
  }

  @Override
  public String getTableName() {
    return TABLE_NAME;
  }

  @Override
  public String getIdColumnName() {
    return ID_COLUMN;
  }

  @Override
  Class<Account> getEntityClass() {
    return Account.class;
  }

  @Override
  public int updateOne(Account entity) {
    String update_sql = "UPDATE account SET trader_id=?, amount=? WHERE id=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(entity));
  }

  /**
   * Helper method that makes sql update values objects
   *
   * @param acc
   * @return
   */
  private Object[] makeUpdateValues(Account acc) {
    Object[] objects = new Object[3];
    objects[0] = acc.getTrader_id();
    objects[1] = acc.getAmount();
    objects[2] = acc.getId();
    return objects;
  }

  public Optional<Account> findByTraderId(Integer traderId) {
    Account account = new Account();
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE trader_id=?";

    try {
      account = jdbcTemplate.queryForObject(selectSql,
          BeanPropertyRowMapper.newInstance(Account.class), traderId);
    } catch (IncorrectResultSizeDataAccessException e) {
      logger.debug("Can't find trader id:" + traderId, e);
    }
    return Optional.of(account);
  }

  @Override
  public <S extends Account> List<S> saveAll(Iterable<S> iterable) {
    List<S> lists = new ArrayList<>();
    iterable.forEach(s -> lists.add((S) save(s)));
    return lists;
  }

  @Override
  public void delete(Account account) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Account> iterable) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
