package fasterthanlight.besthack.taskmanger.services;

import fasterthanlight.besthack.taskmanger.dao.ProjectDAO;
import fasterthanlight.besthack.taskmanger.models.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ProjectService implements ProjectDAO {
    
    private JdbcTemplate jdbcTemplate;
    private ProjectToUserService projectToUserService;

    public ProjectService(JdbcTemplate jdbcTemplate, ProjectToUserService projectToUserService) {
        this.jdbcTemplate = jdbcTemplate;
        this.projectToUserService = projectToUserService;
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
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    "insert into projects(project_name)" + " values(?)" + " returning id",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, newProject.getName());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }


    @Override
    public Integer deleteUserFromProject(@NotNull Integer projectId, @NotNull Integer userId) {
        return null;
    }

    @Override
    public Integer deleteProject(@NotNull Integer projectId) {
        return null;
    }

    @Override
    public List<Project> getAllProjectsByUserId(Integer userId) {
        return null;
    }
}
