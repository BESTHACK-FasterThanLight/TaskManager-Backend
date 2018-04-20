package fasterthanlight.besthack.taskmanger.controllers;

import fasterthanlight.besthack.taskmanger.dao.ProjectDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    private JdbcTemplate jdbcTemplate;
    private ProjectDAO projectDAO;



}
