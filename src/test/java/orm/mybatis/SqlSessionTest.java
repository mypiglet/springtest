package orm.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * 
 * @author mypiglet
 *
 */
public class SqlSessionTest extends SqlSessionFactoryTest {

	@Test(enabled = false)
	public void test() {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		User user = (User) sqlSession.selectOne("getUser", "李四");
		Assert.assertNotNull(user);
	}

	@Test(enabled = true)
	public void test2() {
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		User user = userMapper.getUser("李四");
		Assert.assertNotNull(user);
	}

}
