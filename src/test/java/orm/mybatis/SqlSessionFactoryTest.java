package orm.mybatis;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.testng.annotations.BeforeTest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * 
 * @author mypiglet
 *
 */
public class SqlSessionFactoryTest {

	private SqlSessionFactory sqlSessionFactory;
	private DataSource dataSource;
	private String driverClassName = "com.mysql.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/spring_rest?useUnicode=true&characterEncoding=UTF-8&useSSL=false&useServerPrepStmts=true&cachePrepStmts=true";
	private String username = "root";
	private String password = "123456";

	@BeforeTest(enabled = false)
	public void sqlSessionFactoryTest() {

		String resource = "/mybatis/mybatis-config.xml";
		InputStream is = SqlSessionFactoryTest.class.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

	}

	@BeforeTest(enabled = true)
	public void sqlSessionFactoryTest2() {

		Properties pro = new Properties();
		pro.setProperty("driverClassName", driverClassName);
		pro.setProperty("jdbcUrl", jdbcUrl);
		pro.setProperty("username", username);
		pro.setProperty("password", password);
		HikariConfig config = new HikariConfig(pro);
		dataSource = new HikariDataSource(config);

		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("dev", transactionFactory, dataSource);
		Configuration mybatisConfig = new Configuration(environment);
		mybatisConfig.addMapper(UserMapper.class);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(mybatisConfig);

	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
