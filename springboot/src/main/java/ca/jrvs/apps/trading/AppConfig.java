package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableTransactionManagement
public class AppConfig {

  private Logger logger = LoggerFactory.getLogger(AppConfig.class);
  private String jdbcUrl;
  private String user;
  private String password;

  @Bean
  public MarketDataConfig marketDataConfig(){
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
    marketDataConfig.setToken(System.getenv("token"));
    return null;
  }

  @Bean
  public DataSource dataSource() {
    // jdbc:postgresql://PSQL_HOST:PSQL_PORT/PSQL_DB
    jdbcUrl =
        "jdbc:postgresql://" +
            System.getenv("PSQL_HOST") + ":" +
            System.getenv("PSQL_PORT") +
            "/" +
            System.getenv("PSQL_DB");
    user = System.getenv("PSQL_USER");
    password = System.getenv("PSQL_PASSWORD");

    //Never log your credentials/secrets. Use IDE debugger instead
    // basic implementation of javax.sql
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setUrl(jdbcUrl);
    basicDataSource.setUsername(user);
    basicDataSource.setPassword(password);
    return basicDataSource;
  }
}
