package fasterthanlight.besthack.taskmanger.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fasterthanlight.besthack.taskmanger.dao.ProjectToUserDAO;
import fasterthanlight.besthack.taskmanger.dao.TaskDAO;
import fasterthanlight.besthack.taskmanger.models.ApiResponse;
import fasterthanlight.besthack.taskmanger.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class TaskController {
    private TaskDAO taskDAO;
    private ProjectToUserDAO projectToUserDAO;

    public TaskController(TaskDAO taskDAO, ProjectToUserDAO projectToUserDAO) {
        this.taskDAO = taskDAO;
        this.projectToUserDAO = projectToUserDAO;
    }

    @PostMapping("/git")
    public ResponseEntity<String> getCommit(String j) throws IOException {
        if (!j.matches("commit_id") || j.lastIndexOf("\"body\"") == -1) {
            return ResponseEntity.ok("");
        }

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = new HashMap<String, Object>();

        // convert JSON string to Map
        map = mapper.readValue(j, new TypeReference<Map<String, String>>(){});

        String body = ((Map)map.get("comment")).get("body").toString();

        if (!body.matches("fix task")) {
            return ResponseEntity.ok("");
        }

        int index = body.lastIndexOf("#") + 1;
        int number = Integer.valueOf(body.substring(index));

        taskDAO.closeTask(number);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("{projectid}/task/")
    public @NotNull ResponseEntity<String> getAllTasks(@PathVariable("projectid") Integer projectId, @NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0){
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }


        final List<Task> tasks = Objects.requireNonNull(taskDAO.getAllTasksByProjectId(projectId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(tasks.toString());
    }

    @PostMapping("{projectid}/task/")
    public @NotNull ResponseEntity<String> createTask(@PathVariable("projectid") Integer projectId,
                                                         @NotNull HttpSession httpSession, @RequestBody Task task){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0){
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        taskDAO.setTask(task);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.CREATE_PROJECT_SUCCESS.getResponse());
    }

    @GetMapping("{projectid}/task/{id}")
    public @NotNull ResponseEntity<String> getTask(@PathVariable("projectid") Integer projectId,
                                                      @PathVariable("id") Integer taskId,
                                                      @NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.PROJECT_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        Task task = taskDAO.getTaskById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(task.toString());
    }

    @PostMapping("{projectid}/task/{id}")
    public @NotNull ResponseEntity<String> updateTask(@PathVariable("projectid")Integer projectId,
                                                      @PathVariable("id") Integer taskId,
                                                      @NotNull HttpSession httpSession,
                                                      @NotNull Task task){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.PROJECT_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        Task taskBefore = taskDAO.getTaskById(taskId);
        task.setId(taskId);
        if(!taskBefore.getStatus().equals(task.getStatus())) {
            taskDAO.updateTaskStatus(task);
        }
        if(!taskBefore.getTitle().equals(task.getTitle())) {
            taskDAO.updateTaskTitle(task);
        }
        if(!taskBefore.getText().equals(task.getText())) {
            taskDAO.updateTaskText(task);
        }
        return ResponseEntity.status(HttpStatus.OK).body(task.toString());
    }

    @DeleteMapping("{projectid}/task/{id}")
    public @NotNull ResponseEntity<String> deleteTask(@PathVariable("projectid") Integer projectId,
                                                         @PathVariable("id")Integer taskId,
                                                         @NotNull HttpSession httpSession) {
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.PROJECT_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(projectId, userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        taskDAO.deleteTask(taskId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.TASK_DELETE_SUCCESS.getResponse());
    }


}
