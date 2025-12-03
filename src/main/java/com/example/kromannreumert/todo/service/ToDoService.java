package com.example.kromannreumert.todo.service;

import com.example.kromannreumert.logging.entity.LogAction;
import com.example.kromannreumert.logging.service.LoggingService;
import com.example.kromannreumert.todo.dto.ToDoRequestDto;
import com.example.kromannreumert.todo.dto.ToDoRequestNewToDoDto;
import com.example.kromannreumert.todo.dto.ToDoResponseDto;
import com.example.kromannreumert.todo.entity.ToDo;
import com.example.kromannreumert.todo.mapper.ToDoMapper;
import com.example.kromannreumert.todo.repository.ToDoRepository;
import com.example.kromannreumert.user.entity.Role;
import com.example.kromannreumert.user.entity.User;
import com.example.kromannreumert.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;
    private final LoggingService loggingService;
    private final UserRepository userRepository;

    /**
     * Creates a ToDoService with its required repositories, mapper, and logging service.
     *
     * <p>Initializes the service with a ToDoRepository, ToDoMapper, LoggingService, and UserRepository used
     * for data access, DTO mapping, action logging, and user lookup respectively.</p>
     */
    public ToDoService(ToDoRepository toDoRepository, ToDoMapper toDoMapper, LoggingService loggingService, UserRepository userRepository) {
        this.toDoRepository = toDoRepository;
        this.toDoMapper = toDoMapper;
        this.loggingService = loggingService;
        this.userRepository = userRepository;
    }

    /**
     * Get the total number of ToDo entities in the repository.
     *
     * @return the number of ToDo entities stored in the repository
     */
    public int getToDoSize() {
        List<ToDo> getAll = toDoRepository.findAll();
        return getAll.size();
    }

    /**
     * Retrieve non-archived todos visible to the specified user, applying role-based filtering.
     *
     * <p>If the user has only the JURIST role (and not SAGSBEHANDLER, PARTNER, or ADMIN),
     * only todos related to cases where the user is a case user are returned. Otherwise,
     * all non-archived todos are returned.</p>
     *
     * @param username the username of the requesting user whose roles determine access scope
     * @return a list of ToDoResponseDto representing the non-archived todos the user is allowed to view
     * @throws RuntimeException if the user is not found or if fetching/processing todos fails
     */
    public List<ToDoResponseDto> findAll(String username) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            Set<Role> roles = user.getRoles();

            boolean isJurist = hasRole(roles, "JURIST");
            boolean isSagsbehandler = hasRole(roles, "SAGSBEHANDLER");
            boolean isPartner = hasRole(roles, "PARTNER");
            boolean isAdmin = hasRole(roles, "ADMIN");

            List<ToDo> toDos;

            if (isJurist && !isSagsbehandler && !isPartner && !isAdmin) {
                toDos = toDoRepository.findDistinctByCaseId_Users_UsernameAndArchivedFalse(username);
            } else {
                toDos = toDoRepository.findAllByArchivedFalse();
            }

            List<ToDoResponseDto> responseDtos = toDos.stream()
                    .map(toDoMapper::toToDoResponseDto)
                    .toList();

            loggingService.log(LogAction.VIEW_ALL_TODOS, username, "Viewed todos");

            return responseDtos;
        } catch (Exception e) {
            loggingService.log(LogAction.VIEW_ALL_TODOS_FAILED, username, "Failed to view todos");
            throw new RuntimeException("Failed fetching todos", e);
        }
    }

    /**
     * Checks whether the provided roles include a role with the given name (case-insensitive).
     *
     * @param roles    the set of Role objects to check
     * @param roleName the role name to look for
     * @return `true` if any role's name equals `roleName` ignoring case, `false` otherwise
     */
    private boolean hasRole(Set<Role> roles, String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase(roleName));
    }

    /**
     * Retrieve a ToDo by its id and map it to a response DTO.
     *
     * @param name the username performing the request (used for logging)
     * @param id   the id of the ToDo to retrieve
     * @return     a ToDoResponseDto representing the found ToDo
     */
    public ToDoResponseDto findToDoById(String name, Long id) {
        try {
            ToDo toDo = toDoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

            ToDoResponseDto responseDto = toDoMapper.toToDoResponseDto(toDo);

            loggingService.log(LogAction.VIEW_ONE_TODO, name, "Viewed todo: " + toDo.getName());

            return responseDto;
        } catch (Exception e) {
            loggingService.log(LogAction.VIEW_ONE_TODO_FAILED, name, "Failed to view todo with id: " + id);

            throw new RuntimeException("Todo not found with id: " + id, e);
        }
    }

    public ToDoResponseDto createToDo(String name, ToDoRequestNewToDoDto todoRequestDto) {
        try {
            ToDo toDo = toDoMapper.toToDo(todoRequestDto);
            toDo = toDoRepository.save(toDo);

            loggingService.log(LogAction.CREATE_TODO, name, "Created a todo: " + toDo.getName());

            return toDoMapper.toToDoResponseDto(toDo);
        } catch (RuntimeException e) {
            loggingService.log(LogAction.CREATE_TODO_FAILED, name, "Failed to create todo: " + todoRequestDto.name());
            throw new RuntimeException("Could not create todo", e);
        }
    }

    /**
     * Deletes the ToDo with the given id and logs the action.
     *
     * @param name the name of the user performing the deletion (used for logging)
     * @param id   the identifier of the ToDo to delete
     * @throws RuntimeException if the ToDo cannot be found or the deletion fails
     */
    public void deleteTodo(String name, Long id) {
        try {
            ToDo toDo = toDoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Could not find todo with id: " + id));

            toDoRepository.delete(toDo);

            loggingService.log(LogAction.DELETE_TODO, name, "Deleted todo: " + toDo.getName() + ", id: " + id);
        } catch (Exception e) {
            loggingService.log(LogAction.DELETE_TODO_FAILED, name, "Failed to delete todo with id: " + id + " " + e.getMessage());
            throw new RuntimeException("Could not delete todo with id: " + id, e);
        }
    }

    /**
     * Updates fields of an existing ToDo identified by id and returns its DTO representation.
     *
     * @param id the identifier of the ToDo to update
     * @param name the username performing the update (used for logging)
     * @param todoRequestDto DTO containing the updated ToDo fields
     * @return the updated ToDo mapped to a ToDoResponseDto
     * @throws RuntimeException if the ToDo is not found or the update fails
     */
    public ToDoResponseDto updateTodo(Long id, String name, ToDoRequestDto todoRequestDto) {
        try {
            ToDo todo = toDoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Could not find todo with id: " + id));

            todo.setName(todoRequestDto.name());
            todo.setDescription(todoRequestDto.description());
            todo.setStartDate(todoRequestDto.startDate());
            todo.setEndDate(todoRequestDto.endDate());
            todo.setUsers(todoRequestDto.toDoAssignees());
            todo.setPriority(todoRequestDto.priority());
            todo.setStatus(todoRequestDto.status());
            todo.setArchived(todoRequestDto.archived());

            toDoRepository.save(todo);
            loggingService.log(LogAction.UPDATE_TODO, name, "Updated todo: " + todoRequestDto.name());

            return toDoMapper.toToDoResponseDto(todo);

        } catch (Exception e) {
            loggingService.log(LogAction.UPDATE_TODO_FAILED, name, "Failed to update todo: " + todoRequestDto.name() + " " + e.getMessage());
            throw new RuntimeException("Could not update todo: " + todoRequestDto.name(), e);
        }
    }

    /**
     * Retrieves all non-archived todos assigned to the specified user.
     *
     * @param username the username whose assigned todos will be returned
     * @return a list of ToDoResponseDto representing the user's non-archived assigned todos
     * @throws RuntimeException if fetching assigned todos fails
     */
    public List<ToDoResponseDto> findAssignedToUser(String username) {
        try {
            List<ToDo> toDos = toDoRepository.findDistinctByUsers_UsernameAndArchivedFalse(username);

            List<ToDoResponseDto> responseDtos = toDos.stream()
                    .map(toDoMapper::toToDoResponseDto)
                    .toList();

            loggingService.log(LogAction.VIEW_ALL_TODOS, username, "Viewed todos assigned to user");

            return responseDtos;
        } catch (Exception e) {
            loggingService.log(LogAction.VIEW_ALL_TODOS_FAILED, username, "Failed to view todos assigned to user");
            throw new RuntimeException("Failed fetching assigned todos", e);
        }
    }
}