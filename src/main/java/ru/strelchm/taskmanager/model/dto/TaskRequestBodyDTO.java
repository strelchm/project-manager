package ru.strelchm.taskmanager.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

@ApiModel(description = "Представление задания")

@NoArgsConstructor
@Getter
@Setter
public class TaskRequestBodyDTO {
    @ApiModelProperty(notes = "Название задания", example = "Some name...")
    @Size(min = 0, max = 256, message = "String length is too long")
    private String title;

    @ApiModelProperty(notes = "Описание задания", example = "Some description...")
    @Size(min = 0, max = 256, message = "String length is too long")
    private String description;

    @ApiModelProperty(notes = "Приоритетность. От 1 до 5", example = "2")
    @Min(value = 1, message = "Wrong priority bounds")
    @Max(value = 5, message = "Wrong priority bounds")
    private Integer priority;

//    @ApiModelProperty(notes = "Флаг готовности. При создании не заполнять", example = "true")
//    private Boolean done;

    @ApiModelProperty(notes = "Идентификатор списка заданий", example = "08827999-f46e-4045-9c2a-59fad8db2cb3")
    private UUID taskListId;
}