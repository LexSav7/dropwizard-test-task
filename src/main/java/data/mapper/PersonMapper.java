package data.mapper;

import data.Person;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements ResultSetMapper<Person> {
    @Override
    public Person map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Person(resultSet.getString("NAME"), resultSet.getInt("AGE"));
    }
}
