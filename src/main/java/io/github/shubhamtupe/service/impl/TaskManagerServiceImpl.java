package io.github.shubhamtupe.service.impl;

import io.github.shubhamtupe.dto.ApiResponseDto;
import io.github.shubhamtupe.dto.TaskRequestDto;
import io.github.shubhamtupe.dto.TaskResponseDto;
import io.github.shubhamtupe.entity.Task;
import io.github.shubhamtupe.exception.ResourceNotFoundException;
import io.github.shubhamtupe.exception.ValidationException;
import io.github.shubhamtupe.respository.ITaskManagerRepository;
import io.github.shubhamtupe.service.ITaskManagerService;
import io.github.shubhamtupe.utility.AppConstants;
import io.github.shubhamtupe.utility.AppErrorCodes;
import io.github.shubhamtupe.utility.AppMessagesConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskManagerServiceImpl implements ITaskManagerService {

    private final ITaskManagerRepository taskManagerRepository;

    public TaskManagerServiceImpl(ITaskManagerRepository taskManagerRepository) {
        this.taskManagerRepository = taskManagerRepository;
    }

    @Override
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> createTask(TaskRequestDto taskRequestDto) {
        Optional<Task> taskOptional = taskManagerRepository.findTaskByTitle(taskRequestDto.getTitle());
        if (taskOptional.isPresent()) {
            throw new ValidationException(AppErrorCodes.VALIDATION_FAILED, taskRequestDto.getTitle() + " " + AppMessagesConstants.TASK_ALREADY_EXIST);
        }

        Task savedTask = taskManagerRepository.save(convertDtoToEntity(taskRequestDto));

        ApiResponseDto<TaskResponseDto> apiResponseDto = new ApiResponseDto<>(
                true,
                AppMessagesConstants.TASK_CREATED,
                convertEntityToResponseDto(savedTask)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> getTask(Long id) {
        Optional<Task> taskOptional = taskManagerRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new ResourceNotFoundException(AppMessagesConstants.TASK_DETAILS_NOT_FOUND + " for id: " + id);
        }

        Task task = taskOptional.get();

        ApiResponseDto<TaskResponseDto> apiResponseDto = new ApiResponseDto<>(
                true,
                "Details fetch successfully",
                convertEntityToResponseDto(task)
        );

        return ResponseEntity.ok(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<List<TaskResponseDto>>> getTasks() {

        List<Task> taskList = taskManagerRepository.findAll();
        if (taskList.isEmpty()) {
            throw new ResourceNotFoundException(AppMessagesConstants.TASK_DETAILS_NOT_FOUND);
        }

        List<TaskResponseDto> askResponseDtoList = taskList.stream().map(this::convertEntityToResponseDto).toList();

        ApiResponseDto<List<TaskResponseDto>> apiResponseDto = new ApiResponseDto<>(
                true,
                "Details fetch successfully",
                askResponseDtoList
        );

        return ResponseEntity.ok(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<TaskResponseDto>> updateTaskStatus(Long id) {
        Optional<Task> taskop = taskManagerRepository.findById(id);
        if (taskop.isEmpty()) {
            throw new ValidationException(AppErrorCodes.RESOURCE_NOT_FOUND, AppMessagesConstants.TASK_DETAILS_NOT_FOUND);
        }

        Task task = taskop.get();

        if ("C".equalsIgnoreCase(task.getStatus())) {
            throw new ValidationException(AppErrorCodes.VALIDATION_FAILED, AppMessagesConstants.TASK_STATUS_ALREADY_UPDATED);
        }

        task.setModifiedDate(LocalDateTime.now());
        task.setStatus(AppConstants.COMPLETED);
        taskManagerRepository.save(task);

        ApiResponseDto<TaskResponseDto> apiResponseDto = new ApiResponseDto<>(
                true,
                AppMessagesConstants.TASK_STATUS_UPDATED,
                convertEntityToResponseDto(task)
        );

        return ResponseEntity.ok(apiResponseDto);
    }

    @Override
    public ResponseEntity<ApiResponseDto<String>> deleteTask(Long id) {
        Optional<Task> taskOptional = taskManagerRepository.findById(id);
        if (taskOptional.isEmpty()) {
            throw new ValidationException(AppErrorCodes.RESOURCE_NOT_FOUND, AppMessagesConstants.TASK_DETAILS_NOT_FOUND);
        }

        taskManagerRepository.deleteById(id);
        ApiResponseDto<String> apiResponseDto = new ApiResponseDto<>(
                true,
                AppMessagesConstants.TASK_DELETED,
                null
        );

        return ResponseEntity.ok(apiResponseDto);
    }

    private Task convertDtoToEntity(TaskRequestDto taskRequestDto) {
        Task task = new Task();
        BeanUtils.copyProperties(taskRequestDto, task);
        task.setCreatedDate(LocalDateTime.now());
        task.setStatus(AppConstants.PENDING);
        return task;
    }

    private TaskResponseDto convertEntityToResponseDto(Task task) {
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        BeanUtils.copyProperties(task, taskResponseDto);
        return taskResponseDto;
    }
}
