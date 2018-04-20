package fasterthanlight.besthack.taskmanger.services;

import fasterthanlight.besthack.taskmanger.dao.ProjectToUserDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@SuppressWarnings("Duplicates")
@Service
public class ProjectToUserService implements ProjectToUserDAO {
    private JdbcTemplate jdbcTemplate;

    public ProjectToUserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Integer> getAllProjectsIdsByUserId(Integer userId) {
        final String sql = "SELECT project_id FROM projects_to_users WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{userId}, Integer.class);
    }

    @Override
    public List<Integer> getAllUsersIdsByProjectId(Integer projectId) {
        final String sql = "SELECT user_id FROM projects_to_users WHERE projects_id = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{projectId}, Integer.class);
    }

    @Override
    public void setProjectToUser(Integer projectId, Integer userId) {
        final String sql = "INSERT INTO projects_to_users(project_id, user_id) VALUES(?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, userId);
            return preparedStatement;
        });
    }

    @Override
    public void deleteProjectToUserConnection(Integer projectId, Integer userId) {
        final String sql = "DELETE FROM projects_to_users WHERE project_id = ? AND user_id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, userId);
            return preparedStatement;
        });
    }
}
