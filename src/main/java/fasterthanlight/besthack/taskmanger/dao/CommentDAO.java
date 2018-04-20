package fasterthanlight.besthack.taskmanger.dao;

import fasterthanlight.besthack.taskmanger.models.Comment;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CommentDAO {

    @NotNull Comment getCommentByid(@NotNull Integer commentId);

    @NotNull List<Comment> getAllCommentsByTaskId(@NotNull Integer taskId);

    Integer setComment(@NotNull Comment comment);

    void deleteComment(@NotNull Integer commentId);
}
