package fasterthanlight.besthack.taskmanger.models;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Comment {
    private Integer id;
    private String text;
    private String created;
    private Integer taskId;
    private Integer authorId;
    private String authorLogin;

    public Comment(@NotNull Integer id, @NotNull String text, @NotNull String created, Integer taskId, Integer authorId, String authorLogin) {
        this.id = id;
        this.text = text;
        this.created = created;
        this.taskId = taskId;
        this.authorId = authorId;
        this.authorLogin = authorLogin;
    }

    public Comment() {

    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(created, comment.created) &&
                Objects.equals(taskId, comment.taskId) &&
                Objects.equals(authorId, comment.authorId) &&
                Objects.equals(authorLogin, comment.authorLogin);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, created, taskId, authorId, authorLogin);
    }
}
