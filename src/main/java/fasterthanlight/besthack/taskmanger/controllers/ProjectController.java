package fasterthanlight.besthack.taskmanger.controllers;

import fasterthanlight.besthack.taskmanger.dao.ProjectDAO;
import fasterthanlight.besthack.taskmanger.dao.ProjectToUserDAO;
import fasterthanlight.besthack.taskmanger.models.ApiResponse;
import fasterthanlight.besthack.taskmanger.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
public class ProjectController {
    private final ProjectDAO projectDAO;
    private final ProjectToUserDAO projectToUserDAO;

    public ProjectController(ProjectDAO projectDAO, ProjectToUserDAO projectToUserDAO) {
        this.projectDAO = projectDAO;
        this.projectToUserDAO = projectToUserDAO;
    }

    @GetMapping("/project/")
    public @NotNull ResponseEntity<String> projects(@NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        final List<Project> projects = Objects.requireNonNull(projectDAO.getAllProjectsByUserId(userIdInCurrentSession));
        return ResponseEntity.status(HttpStatus.OK)
                .body(projects.toString());
    }

    @PostMapping("/project/")
    public @NotNull ResponseEntity<String> createProject(@NotNull HttpSession httpSession, @RequestBody Project project){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        Integer projectId = projectDAO.setProject(project);
        projectToUserDAO.setProjectToUser(projectId, userIdInCurrentSession);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.CREATE_PROJECT_SUCCESS.getResponse());
    }

    @GetMapping("/project/{id}")
    public @NotNull ResponseEntity<String> getProject(@PathVariable("id") Integer projectId, @NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(projectDAO.isProjectExist(projectId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.PROJECT_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        Project project = projectDAO.getProjectById(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(project.toString());
    }

    @DeleteMapping("/project/{id}")
    public @NotNull ResponseEntity<String> deleteProject(@PathVariable("id") Integer projectId, @NotNull HttpSession httpSession) {
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(projectDAO.isProjectExist(projectId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.PROJECT_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        projectDAO.deleteProject(projectId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.PROJECT_DELETE_SUCCESS.getResponse());
    }
}
