package fasterthanlight.besthack.taskmanger.models;
import javax.validation.constraints.NotNull;

@SuppressWarnings({"unused"})
public enum ApiResponse {
    USERNAME_EXIST(0, "User with that name is already exist"),
    EMAIL_EXIST(1, "User with that email is already exist"),
    USER_ALREADY_AUTHORIZED(2, "User is already authorized"),
    LOGIN_OR_EMAIL_NOT_EXIST(3, "User's login or email doesn't exist"),
    PASSWORD_INCORRECT(4, "User's password incorrect"),
    SIGNUP_SUCCESS(5, "SignUp is successful"),
    SIGNIN_SUCCESS(6, "SignIn is successful"),
    USER_NOT_AUTHORIZED(7, "You need to authorized first, my friend"),
    SIGNOUT_SUCCESS(8, "SignOut is successful"),
    LOGIN_IS_THE_SAME(9, "User login is the same"),
    CHANGE_PROFILE_SUCCESS(10, "Profile successfully changed"),
    REQUEST_FROM_SESSION_SUCCESSFUL(11, "Success"),
    PASSWORD_NOT_MATCH(12, "Password and password confirmation not match"),
    FIELD_EMPTY(13, "You're not entered some field that should be entered"),
    SIGNUP_VALIDATION_FAILED(14, "Username should be more than 5 and CAN'T contains '@' but email SHOULD contains '@'"),
    CREATE_PROJECT_SUCCESS(15, "Project created successfully"),
    PROJECT_NOT_EXIST(16, "Project not exist"),
    NOT_AVAILABLE(17, "Wrong access"),
    PROJECT_DELETE_SUCCESS(18, "Project deleted successfully"),
    TASK_DELETE_SUCCESS(19, "Task deleted successfully"),
    TASK_NOT_EXIST(20, "Task not exist"),
    COMMENT_DELETE_SUCCESS(21, "Comment successfully deleted"),
    CREATE_COMMENT_SUCCESS(22, "Comment successfully created");

    private final String response;

    ApiResponse(@NotNull Integer status, @NotNull String response) {
        this.response = response;
    }

    public @NotNull String getResponse() {
        return response;
    }
}
