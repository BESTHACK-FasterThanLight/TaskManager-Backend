package fasterthanlight.besthack.taskmanger.models;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Comment {
    private Integer id;
    private String text;
    private String created;

    public Comment(@NotNull Integer id, @NotNull String text, @NotNull String created) {
        this.id = id;
        this.text = text;
        this.created = created;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) &&
                Objects.equals(text, comment.text) &&
                Objects.equals(created, comment.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, text, created);
    }
}
