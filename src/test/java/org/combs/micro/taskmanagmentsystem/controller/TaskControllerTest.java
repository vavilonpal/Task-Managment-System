package org.combs.micro.taskmanagmentsystem.controller;

import org.combs.micro.taskmanagmentsystem.auth.JwtService;
import org.combs.micro.taskmanagmentsystem.entity.Task;
import org.combs.micro.taskmanagmentsystem.service.PaginationAndSortingService;
import org.combs.micro.taskmanagmentsystem.service.TaskService;
import org.combs.micro.taskmanagmentsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.data.domain.Page;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    @MockBean
    private PaginationAndSortingService paginationAndSortingService;

    @Test
    @WithMockUser
    void getMyTasks_ShouldReturnTasks() throws Exception {

        Page taskPage = Mockito.mock(Page.class);
        when(taskService.getAllTasksByAuthorId(any(Long.class), any()))
                .thenReturn(taskPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/my-tasks")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", notNullValue()));
    }

    @Test
    @WithMockUser
    void createTask_ShouldReturnCreatedTask() throws Exception {
        var task = new Task();
        when(taskService.createTask(any(Task.class))).thenReturn(task);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Task\", \"description\":\"Task Description\", \"status\":\"OPEN\", \"priority\":\"HIGH\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Task")));
    }

    @Test
    @WithMockUser
    void getTasksByExecutor_ShouldReturnTasks() throws Exception {
        // Arrange
        Page<Task> taskPage = Mockito.mock(Page.class);
        when(userService.isExists(any(Long.class))).thenReturn(true);
        when(taskService.getAllTasksByExecutorId(any(Long.class), any()))
                .thenReturn(taskPage);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/by-executor/1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteTask_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        when(taskService.existsByIdAndAuthorId(any(Long.class), any(Long.class)))
                .thenReturn(true);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void updateTask_ShouldReturnUpdatedTask() throws Exception {
        // Arrange
        var task = new Task();
        task.setId(1L);
        when(taskService.existsByIdAndAuthorId(any(Long.class), any(Long.class)))
                .thenReturn(true);
        when(taskService.updateTask(any(Task.class))).thenReturn(task);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1, \"title\":\"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }
}
