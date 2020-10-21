package ru.strelchm.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication //(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableSwagger2
public class TaskManagerApplication {
    /**
     * Точка входа
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

}
