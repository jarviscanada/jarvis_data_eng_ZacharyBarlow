package ca.jrvs.apps.trading.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.trading.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

  @Test
  public void getJdbcTemplate() {
  }

  @Test
  public void getSimpleJdbcInsert() {
  }

  @Test
  public void getTableName() {
  }

  @Test
  public void getIdColumnName() {
  }

  @Test
  public void getEntityClass() {
  }

  @Test
  public void updateOne() {
  }

  @Test
  public void saveAll() {
  }

  @Test
  public void delete() {
  }

  @Test
  public void deleteAll() {
  }
}