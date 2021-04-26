package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

  private final String TABLE_NAME = "trader";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public TraderDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  @Override
  public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

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
  Class<Trader> getEntityClass() {
    return Trader.class;
  }

  @Override
  public int updateOne(Trader entity) {
    String update_sql = "UPDATE " + TABLE_NAME + " SET firstname=?, lastname=?, country=?, email=?,"
        + "dob=? WHERE " + ID_COLUMN + "=?";
    return jdbcTemplate.update(update_sql, makeUpdateValues(entity));
  }

  /**
   * Helper method that makes sql update values objects
   *
   * @param securityOrder
   * @return
   */
  private Object[] makeUpdateValues(Trader securityOrder) {
    Object[] objects = new Object[6];
    objects[0] = securityOrder.getId();
    objects[1] = securityOrder.getFirstName();
    objects[2] = securityOrder.getLastName();
    objects[3] = securityOrder.getCountry();
    objects[4] = securityOrder.getEmail();
    objects[5] = securityOrder.getDob();
    return objects;
  }

  @Override
  public <S extends Trader> List<S> saveAll(Iterable<S> iterable) {
    List<S> lists = new ArrayList<>();
    iterable.forEach(s -> lists.add((S) save(s)));
    return lists;
  }

  @Override
  public void delete(Trader entity) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void deleteAll(Iterable<? extends Trader> entities) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
