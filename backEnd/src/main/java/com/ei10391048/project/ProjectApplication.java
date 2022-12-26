package com.ei10391048.project;

import com.ei10391048.project.exception.IncorrectUserException;
import com.ei10391048.project.modelo.user.UserManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class ProjectApplication {
    private static final Logger log = Logger.getLogger(ProjectApplication.class.getName());

    public static void main(String[] args) throws IncorrectUserException {
        new SpringApplicationBuilder(ProjectApplication.class).run(args);
        UserManager.getInstance().registerUser("sdk","1234a");
        System.out.println(UserManager.getInstance().getUser("sdk").getEmail());

    }
}
