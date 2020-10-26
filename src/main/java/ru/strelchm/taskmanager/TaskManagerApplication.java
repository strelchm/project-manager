package ru.strelchm.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
//@EnableJpaAuditing // включение аудита (аннотации создания / изменения)
public class TaskManagerApplication {
    /**
     * Точка входа
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
