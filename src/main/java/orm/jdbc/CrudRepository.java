package orm.jdbc;

import lang.annotation.Sql;

public interface CrudRepository {

	@Sql
	default User getUser(String name) {

		final String sql = "SELECT NAME,PASSWORD FROM DEMO_USER WHERE NAME=:NAME";
		return SqlHandler.handle(sql);

	};

}
