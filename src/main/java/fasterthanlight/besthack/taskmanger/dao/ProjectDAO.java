package fasterthanlight.besthack.taskmanger.dao;

import fasterthanlight.besthack.taskmanger.models.Project;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProjectDAO {
    Project getProjectById(Integer id);

    Integer setProject(@NotNull Project project);

    Integer deleteUserFromProject(@NotNull Integer projectId, @NotNull Integer userId);

    Integer deleteProject(@NotNull Integer projectId);

    List<Project> getAllProjectsByUserId(@NotNull Integer userId);
}
