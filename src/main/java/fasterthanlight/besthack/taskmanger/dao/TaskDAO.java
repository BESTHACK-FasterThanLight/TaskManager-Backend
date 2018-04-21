package fasterthanlight.besthack.taskmanger.dao;

import fasterthanlight.besthack.taskmanger.models.Task;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface TaskDAO {
    @NotNull Task getTaskById(@NotNull Integer taskId);

    @NotNull List<Task> getAllTasksByProjectId(@NotNull Integer projectId);

    @NotNull Integer setTask(@NotNull Task task);

    void updateTaskStatus(@NotNull Task task);

    void updateTaskTitle(@NotNull Task task);

    void updateTaskText(@NotNull Task task);

    Integer isTaskExist(@NotNull Integer taskId);

    void deleteTask(@NotNull Integer taskId);

    public void closeTask(int index);
}
