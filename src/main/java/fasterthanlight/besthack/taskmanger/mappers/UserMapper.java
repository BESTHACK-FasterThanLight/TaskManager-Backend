package fasterthanlight.besthack.taskmanger.mappers;

import fasterthanlight.besthack.taskmanger.models.User;
import org.springframework.jdbc.core.RowMapper;

import javax.validation.constraints.NotNull;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @SuppressWarnings("NullableProblems")
    @Override
    public @NotNull User mapRow(@NotNull ResultSet rs, @NotNull int rowNum) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"),
                rs.getString("email"), rs.getString("password"));
    }
}
