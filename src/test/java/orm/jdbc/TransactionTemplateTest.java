package orm.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import datasource.hikari.DataSourceTest;

/**
 * 
 * 
 * @author mypiglet
 *
 */
public class TransactionTemplateTest extends DataSourceTest {

	private TransactionTemplate transactionTemplate;
	private JdbcOperations jdbc;

	@BeforeTest(enabled = false)
	public void transactionTemplateTest() {
		jdbc = new JdbcTemplate(this.getDataSource());
		this.transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(this.getDataSource()));
		
		Assert.assertNotNull(jdbc);
		Assert.assertNotNull(transactionTemplate);
	}

	@Test(enabled = false)
	public void transactionTest() {

		final String sql1 = "UPDATE demo_user SET PASSWORD='111111' WHERE NAME='李四'";
		final String sql2 = "UPDATE demo_user SET PASSWORD='222222' WHERE NAME='张三'";

		this.transactionTemplate.execute((status) -> {
			this.transactionTemplate.execute((status2) -> {
				this.jdbc.execute(sql2);
				return null;
			});
			
			this.jdbc.execute(sql1);

			return null;
		});
		
		System.out.println("事务结束！");

	}
	
	@Test(enabled = false)
	public void transactionTest2() {
		final String sql = "SELECT SERIAL_NO FROM demo_user_counter FOR UPDATE;UPDATE demo_user_counter SET SERIAL_NO = SERIAL_NO + 1; COMMIT;";
		final String sql2 = "INSERT INTO demo_user_counter(SERIAL_NO) VALUES(1)";
		Long serialNo = 0L;
		serialNo = this.jdbc.execute(new ConnectionCallback<Long>() {

			@Override
			public Long doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.execute();
				ResultSet rs = ps.getResultSet();
				if (rs != null) {
					while (rs.next()) {
						return rs.getLong(1) + 1;
					}
				}
				return 0L;
			}

		});
		// 没有该计数器，插入,这里需要考虑并发情况（保证插入唯一索引，不然报错）
		if (serialNo == 0L) {
			this.jdbc.update(sql2);
			serialNo = 1L;
		}
	}

}
