package fasterthanlight.besthack.taskmanger.services;

import fasterthanlight.besthack.taskmanger.dao.TaskDAO;
import fasterthanlight.besthack.taskmanger.models.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService implements TaskDAO {

    private JdbcTemplate jdbcTemplate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public TaskService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public @NotNull Task getTaskById(@NotNull Integer taskId) {
        final String sql = "SELECT * FROM tasks WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, (rs, rwNumber) -> {
            final Task task = new Task();
            task.setId(rs.getInt("id"));
            task.setTitle(rs.getString("title"));
            task.setText(rs.getString("task_text"));
            Timestamp timestamp = rs.getTimestamp("created");
            task.setCreated(dateFormat.format(timestamp.getTime()));
            task.setStatus(rs.getInt("status"));
            task.setProjectId(rs.getInt("project_id"));
            return task;
        });
    }

    @Override
    public @NotNull List<Task> getAllTasksByProjectId(@NotNull Integer projectId) {
        final String sql = "SELECT * FROM tasks WHERE project_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{projectId}, (rs, rwNumber) -> {
            List<Task> tasks = new ArrayList<>();
            for (int i = 0; i < rwNumber; i++){
                Timestamp timestamp = rs.getTimestamp("created");
                Task task = new Task(rs.getInt("id"), dateFormat.format(timestamp.getTime()),
                        rs.getString("title"), rs.getInt("project_id"),
                        rs.getString("task_text"), rs.getInt("status"));
                tasks.add(task);
            }
            return tasks;
        });
    }

    @Override
    public @NotNull Integer setTask(@NotNull Task task) {

        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO tasks(title, task_text, project_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, task.getTitle());
            pst.setString(2, task.getText());
            pst.setInt(3, task.getProjectId());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public @NotNull void updateTaskStatus(@NotNull Task task) {
        final String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, task.getStatus());
            pst.setInt(2, task.getId());
            return pst;
        });
    }

    @Override
    public void updateTaskTitle(@NotNull Task task) {
        final String sql = "UPDATE tasks SET title = ? WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, task.getTitle());
            pst.setInt(2, task.getId());
            return pst;
        });
    }

    @Override
    public void updateTaskText(@NotNull Task task) {
        final String sql = "UPDATE tasks SET text = ? WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, task.getText());
            pst.setInt(2, task.getId());
            return pst;
        });
    }

    @Override
    public Integer isTaskExist(@NotNull Integer taskId) {
        final String sql = "SELECT count(*) WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, Integer.class);

    }

    @Override
    public void deleteTask(@NotNull Integer taskId) {
        final String sql = "DELETE FROM tasks WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, taskId);
            return pst;
        });
    }


}
