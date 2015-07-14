package it.sevenbits.config;

import it.sevenbits.core.repository.UserInPostgreSQLRepository;
import it.sevenbits.core.repository.UserRepository;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by awemath on 7/8/15.
 */
@Configuration
@MapperScan(basePackages = "it.sevenbits.core.mappers")
public class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

   /* @Bean
    public UserRepository userInPostgreSQLrepository() {
        return new UserInPostgreSQLRepository();
    }*/

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }
}
