package io.github.shubhamtupe.controller;

import io.github.shubhamtupe.dto.ApiResponseDto;
import io.github.shubhamtupe.dto.TaskRequestDto;
import io.github.shubhamtupe.dto.TaskResponseDto;
import io.github.shubhamtupe.service.ITaskManagerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskManagerController {

    private final ITaskManagerService taskManagerService;

    public TaskManagerController(ITaskManagerService taskManagerService) {
        this.taskManagerService = taskManagerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        return taskManagerService.createTask(taskRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> getTask(@PathVariable Long id) {
        return taskManagerService.getTask(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<TaskResponseDto>>> getTasks() {
        return taskManagerService.getTasks();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> updateTaskStatus(@PathVariable Long id) {
        return taskManagerService.updateTaskStatus(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteTask(@PathVariable Long id) {
        return taskManagerService.deleteTask(id);
    }

}
