package fasterthanlight.besthack.taskmanger.models;

import java.util.Objects;

public class Task {
    private Integer id;
    private String created;
    private String name;
    private String project_id;

    public Task(Integer id, String created, String name, String project_id) {
        this.id = id;
        this.created = created;
        this.name = name;
        this.project_id = project_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(created, task.created) &&
                Objects.equals(name, task.name) &&
                Objects.equals(project_id, task.project_id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, created, name, project_id);
    }
}
