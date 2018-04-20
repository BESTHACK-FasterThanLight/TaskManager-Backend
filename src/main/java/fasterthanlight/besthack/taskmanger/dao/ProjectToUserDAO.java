package fasterthanlight.besthack.taskmanger.dao;

import java.util.List;

public interface ProjectToUserDAO {
    List<Integer> getAllProjectsIdsByUserId(Integer userId);

    List<Integer> getAllUsersIdsByProjectId(Integer projectId);

    void setProjectToUser(Integer projectId, Integer userId);

    void deleteProjectToUserConnection(Integer projectId, Integer userId);

    Integer isUserHaveAcess(Integer projectId, Integer userId);

}
