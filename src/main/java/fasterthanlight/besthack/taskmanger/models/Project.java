package fasterthanlight.besthack.taskmanger.models;

import java.util.Objects;

public class Project {
    private Integer id;
    private String name;

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Project() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(name, project.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
