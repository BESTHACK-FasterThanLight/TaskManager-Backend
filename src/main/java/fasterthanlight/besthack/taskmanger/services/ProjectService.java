package fasterthanlight.besthack.taskmanger.services;

import fasterthanlight.besthack.taskmanger.dao.ProjectDAO;
import fasterthanlight.besthack.taskmanger.models.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService implements ProjectDAO {
    
    private JdbcTemplate jdbcTemplate;

    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project getProjectById(Integer id) {

        final String sql = "SELECT * FROM projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rwNumber) -> {
            final Project project = new Project();
            project.setId(rs.getInt("id"));
            project.setName(rs.getString("project_name"));
            return project;
        });
    }

    @Override
    public Integer setProject(@NotNull Project newProject) {

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "insert into projects(project_name)" + " values(?)" + " returning id" ;
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, newProject.getName());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteProject(@NotNull Integer projectId) {
        final String sql = "DELETE FROM projects WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, projectId);
            return pst;
        });
    }

    @Override
    public List<Project> getAllProjectsByUserId(Integer userId) {
        final String sql = "SELECT * FROM projects p JOIN projects_to_users pu ON (p.id = pu.id AND u.id = ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, (rs, rwNumber) -> {
            List<Project> projects = new ArrayList<>();
            for (int i =0; i < rwNumber; i++){
                Project project = new Project(rs.getInt("id"), rs.getString("project_name"));
                projects.add(project);
            }
            return projects;
        });
    }

    @Override
    public Integer isProjectExist(Integer projectId) {
        final String sql = "SELECT count(*) FROM projects WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{projectId}, Integer.class);
    }
}
