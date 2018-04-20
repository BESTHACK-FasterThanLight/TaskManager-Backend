package fasterthanlight.besthack.taskmanger.controllers;

import fasterthanlight.besthack.taskmanger.dao.CommentDAO;
import fasterthanlight.besthack.taskmanger.dao.ProjectToUserDAO;
import fasterthanlight.besthack.taskmanger.dao.TaskDAO;
import fasterthanlight.besthack.taskmanger.models.ApiResponse;
import fasterthanlight.besthack.taskmanger.models.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@RestController
public class CommentController {

    private CommentDAO commentDAO;
    private ProjectToUserDAO projectToUserDAO;
    private TaskDAO taskDAO;

    public CommentController(CommentDAO commentDAO, ProjectToUserDAO projectToUserDAO, TaskDAO taskDao) {
        this.commentDAO = commentDAO;
        this.projectToUserDAO = projectToUserDAO;
        this.taskDAO = taskDao;
    }

    @GetMapping("{taskid}/comment/")
    public @NotNull ResponseEntity<String> getAllComments(@PathVariable("taskid") Integer taskId, @NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.TASK_NOT_EXIST.getResponse());
        }

        if(projectToUserDAO.isUserHaveAcess(taskDAO.getTaskById(taskId).getProjectId(), userIdInCurrentSession) <= 0){
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }


        final List<Comment> tasks = Objects.requireNonNull(commentDAO.getAllCommentsByTaskId(taskId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(tasks.toString());
    }

    @PostMapping("{taskid}/comment/")
    public @NotNull ResponseEntity<String> createComment(@PathVariable("taskId") Integer taskId,
                                                         @NotNull HttpSession httpSession,
                                                         @RequestBody Comment comment){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.TASK_NOT_EXIST.getResponse());
        }

        if(projectToUserDAO.isUserHaveAcess(taskDAO.getTaskById(taskId).getProjectId(), userIdInCurrentSession) <= 0){
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        commentDAO.setComment(comment);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.CREATE_COMMENT_SUCCESS.getResponse());
    }

    @GetMapping("{taskid}/comment/{id}")
    public @NotNull ResponseEntity<String> getComment(@PathVariable("taskid") Integer taskId,
                                                   @PathVariable("id") Integer commentId,
                                                   @NotNull HttpSession httpSession){
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.TASK_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(taskDAO.getTaskById(taskId).getProjectId(), userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        Comment comment = commentDAO.getCommentByid(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(comment.toString());
    }

    @DeleteMapping("{taskid}/comment/{id}")
    public @NotNull ResponseEntity<String> deleteComment(@PathVariable("taskid") Integer taskId,
                                                      @PathVariable("id")Integer commentId,
                                                      @NotNull HttpSession httpSession) {
        final Integer userIdInCurrentSession = (Integer) httpSession.getAttribute("id");

        if (userIdInCurrentSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.USER_NOT_AUTHORIZED.getResponse());
        }

        if(taskDAO.isTaskExist(taskId) <= 0) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.TASK_NOT_EXIST.getResponse());
        }

        if( projectToUserDAO.isUserHaveAcess(taskDAO.getTaskById(taskId).getProjectId(), userIdInCurrentSession) <= 0) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.NOT_AVAILABLE);
        }

        commentDAO.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.TASK_DELETE_SUCCESS.getResponse());
    }
}
