package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class PositionDao {

  private final String TABLE_NAME = "position";
  private final String ID_COLUMN = "id";

  private JdbcTemplate jdbcTemplate;
  private SimpleJdbcInsert simpleJdbcInsert;

  @Autowired
  public PositionDao(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME)
        .usingGeneratedKeyColumns(ID_COLUMN);
  }

  public boolean existsById(Integer id) {
    return findById(id).size() > 0;
  }

  public List<Position> findById(Integer id) {
    String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE account_id=?";
    return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Position.class), id);
  }

  public List<Position> findAll() {
    String sql_statement = "select * from " + TABLE_NAME;
    return jdbcTemplate.query(sql_statement,
        BeanPropertyRowMapper.newInstance(Position.class));
  }

  public long count() {
    return findAll().size();
  }

  public List<Position> findAllById(Iterable<Integer> ids) {
    List<Position> results = new ArrayList<>();
    ids.forEach(id -> {
      Position element = findById(id).get(0);
      results.add(element);
    });
    return results;
  }
}
