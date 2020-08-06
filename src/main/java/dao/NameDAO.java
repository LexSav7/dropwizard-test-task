package dao;

import data.mapper.PersonMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(PersonMapper.class)
public interface NameDAO {

    @SqlQuery("select * from Names")
    List<String> getNames();

    @SqlUpdate("insert into Names (name) values (:name)")
    int saveName(@Bind("name") String name);
}
