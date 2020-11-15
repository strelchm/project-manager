package ru.strelchm.taskmanager.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import ru.strelchm.taskmanager.model.dbo.Task;
import ru.strelchm.taskmanager.model.dbo.TaskGroup;
import ru.strelchm.taskmanager.model.dbo.TaskListGroup;
import ru.strelchm.taskmanager.model.dto.TaskDTO;
import ru.strelchm.taskmanager.model.dto.TaskGroupDTO;
import ru.strelchm.taskmanager.model.dto.TaskListDTO;
import ru.strelchm.taskmanager.model.dto.TaskListGroupDTO;

import java.util.ArrayList;
import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);

        mapper.addMappings(new PropertyMap<Task, TaskDTO>() {
            @Override
            protected void configure() {
                skip(destination.getPriority()); // приоритетность заполняем сами
            }
        });

        mapper.addConverter(getTaskListGroupDboToDtoConverter());
        mapper.addConverter(getTaskGroupDboToDtoConverter(mapper));


        return mapper;
    }

    /**
     * Конвертирование группового DBO в групповое DTO
     */
    private Converter<TaskListGroup, TaskListGroupDTO> getTaskListGroupDboToDtoConverter() {
        return context -> {
            TaskListGroup dboResponse = context.getSource();
            TaskListGroupDTO dtoResponse = context.getDestination();

            Page<TaskListDTO> page = dboResponse.getTaskLists().map(dbo -> {
                final TaskListDTO dto = new TaskListDTO();

                dto.setTitle(dbo.getTitle());
                dto.setCreateTime(dbo.getCreateTime());
                dto.setUpdateTime(dbo.getUpdateTime());
                dto.setId(dbo.getId());
                dto.setTasks(dbo.getTasks());

                return dto;
            });

            dtoResponse.setTaskLists(page);
            dtoResponse.setDoneTaskListCount(dboResponse.getDoneTaskListCount());
            dtoResponse.setTodoTaskListCount(dboResponse.getTodoTaskListCount());

            return dtoResponse;
        };
    }

    /**
     * Конвертирование группового DBO в групповое DTO
     */
    private Converter<TaskGroup, TaskGroupDTO> getTaskGroupDboToDtoConverter(ModelMapper mapper) {
        return context -> {
            TaskGroup dboResponse = context.getSource();
            TaskGroupDTO dtoResponse = context.getDestination();
            List<TaskDTO> dtoList = new ArrayList<>();
//        Type listType = new TypeToken<List<TaskDTO>>() {
//        }.getType();
//
//        List<TaskDTO> dtoList = mapper.modelMapper().map(dboResponse.getTasks(), listType); // todo сделать через маппер

            for (Task td : dboResponse.getTasks()) {
                TaskDTO task = mapper.map(td, TaskDTO.class);
                task.setPriority(td.getPriority().getValue()); //TODO : hardcode
                dtoList.add(task);
            }

            dtoResponse.setTasks(dtoList);
            dtoResponse.setDoneTotalTaskCount(dboResponse.getDoneTotalTaskCount());
            dtoResponse.setTodoTotalTaskCount(dboResponse.getTodoTotalTaskCount());

            return dtoResponse;
        };

    }
}