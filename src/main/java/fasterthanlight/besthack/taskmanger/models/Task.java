package fasterthanlight.besthack.taskmanger.models;

import java.util.Objects;

public class Task {
    private Integer id;
    private String created;
    private String title;
    private Integer projectId;
    private String text;
    private Integer status;

    public Task(Integer id, String created, String title, Integer projectId, String text, Integer status) {
        this.id = id;
        this.created = created;
        this.title = title;
        this.projectId = projectId;
        this.text = text;
        this.status = status;
    }

    public Task() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(created, task.created) &&
                Objects.equals(title, task.title) &&
                Objects.equals(projectId, task.projectId) &&
                Objects.equals(text, task.text) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, created, title, projectId, text, status);
    }
}
