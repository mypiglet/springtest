package orm.mybatis;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	
	@Select("SELECT * FROM demo_user WHERE NAME = #{name}")
	User getUser(String name);

}
