package fasterthanlight.besthack.taskmanger.configurations;

public enum TaskStatus {
    TODO(1),
    READY(2),
    IN_PROGRESS(3);

    Integer status;

    TaskStatus(Integer status) {
        this.status = status;
    }
}
