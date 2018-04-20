package fasterthanlight.besthack.taskmanger.models;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class User {

    private String username;
    private String email;
    private String password;
    private Integer id;

    public User(@NotNull String username, @NotNull String email, @NotNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = 0;
    }

    public User(@NotNull Integer id, @NotNull String username,
                @NotNull String email, @NotNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public User() {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, email, password, id);
    }
}
