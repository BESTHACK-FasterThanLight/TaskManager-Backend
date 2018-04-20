package fasterthanlight.besthack.taskmanger.services;

import fasterthanlight.besthack.taskmanger.dao.CommentDAO;
import fasterthanlight.besthack.taskmanger.dao.UserDao;
import fasterthanlight.besthack.taskmanger.models.Comment;
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
public class CommentService implements CommentDAO {

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public CommentService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public @NotNull Comment getCommentByid(@NotNull Integer commentId) {

        final String sql = "SELECT * FROM comments WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{commentId}, (rs, rwNumber) -> {
            final Comment comment = new Comment();
            comment.setId(rs.getInt("id"));
            comment.setText(rs.getString("comment_text"));
            Timestamp timestamp = rs.getTimestamp("created");
            comment.setCreated(dateFormat.format(timestamp.getTime()));
            comment.setTaskId(rs.getInt("task_id"));
            comment.setAuthorId(rs.getInt("author_id"));
            comment.setAuthorLogin(rs.getString("author_login"));
            return comment;
        });
    }

    @Override
    public @NotNull List<Comment> getAllCommentsByTaskId(@NotNull Integer taskId) {
        final String sql = "SELECT * FROM comments WHERE project_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{taskId}, (rs, rwNumber) -> {
            List<Comment> comments = new ArrayList<>();
            for (int i = 0; i < rwNumber; i++){
                Timestamp timestamp = rs.getTimestamp("created");
                Comment comment = new Comment(rs.getInt("id"),
                        rs.getString("comment_text"),
                        dateFormat.format(timestamp.getTime()),
                        rs.getInt("task_id"), rs.getInt("author_id"), rs.getString("author_login"));
                comments.add(comment);
            }
            return comments;
        });
    }

    @Override
    public Integer setComment(@NotNull Comment comment) {
        final GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO comments(comment_text, task_id, author_id, author_login) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(
                    sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, comment.getText());
            pst.setInt(2, comment.getTaskId());
            pst.setInt(3, comment.getAuthorId());
            pst.setString(4,userDao.getUserById(comment.getAuthorId()).getUsername());
            return pst;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void deleteComment(@NotNull Integer commentId) {
        final String sql = "DELETE FROM comments WHERE id = ?";
        jdbcTemplate.update(con -> {
            final PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, commentId);
            return pst;
        });
    }
}
