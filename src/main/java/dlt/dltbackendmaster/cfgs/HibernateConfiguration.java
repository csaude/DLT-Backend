package dlt.dltbackendmaster.cfgs;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("dlt.dltbackendmaster.domain");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://dreams_host_db:3306/dreams_db");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager hibernateTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private final Properties hibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
		hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mysql://dreams_host_db:3306/dreams_db");
		hibernateProperties.setProperty("hibernate.connection.username", "root");
		hibernateProperties.setProperty("hibernate.connection.password", "root");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.connection.provider_class",
				"org.hibernate.c3p0.internal.C3P0ConnectionProvider");
		hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
		hibernateProperties.setProperty("hibernate.c3p0.min_size", "5");
		hibernateProperties.setProperty("hibernate.c3p0.max_size", "20");
		hibernateProperties.setProperty("hibernate.c3p0.timeout", "300");
		hibernateProperties.setProperty("hibernate.c3p0.max_statements", "50");
		hibernateProperties.setProperty("hibernate.c3p0.idle_test_period ", "3000");
//		hibernateProperties.setProperty("hibernate.show_sql", "true");
//		hibernateProperties.setProperty("hibernate.format_sql", "true");
		return hibernateProperties;
	}
}
