package ru.strelchm.taskmanager.service.impl;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.strelchm.taskmanager.repository.TaskListRepository;
import ru.strelchm.taskmanager.service.api.TaskListService;

class TaskListServiceImplTest {
    @TestConfiguration
    static class TaskListServiceImplTestContextConfiguration {
        @Bean
        public TaskListService employeeService() {
            return new TaskListServiceImpl();
        }
    }

    @Autowired
    private TaskListService taskListService;

    @MockBean
    private TaskListRepository taskListRepository;

    @Before(value = "")
    public void setUp() {
//        Employee alex = new Employee("alex");
//        Mockito.when(taskListRepository.findByName(alex.getName())).thenReturn(alex);
    }

}