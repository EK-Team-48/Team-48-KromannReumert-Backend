package com.example.kromannreumert.todo.repository;

import com.example.kromannreumert.todo.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    /**
 * Retrieve all ToDo entities that are not archived.
 *
 * @return a list of ToDo entities whose `archived` property is `false`.
 */
List<ToDo> findAllByArchivedFalse();

    /**
 * Finds distinct ToDo items associated with a case that includes a user with the given username and that are not archived.
 *
 * @param username the username to match among users associated with the ToDo's case
 * @return a list of distinct ToDo entities matching the username and not archived; an empty list if none are found
 */
List<ToDo> findDistinctByCaseId_Users_UsernameAndArchivedFalse(String username);

    /**
 * Finds distinct ToDo items assigned to the user with the specified username that are not archived.
 *
 * @param username the username to filter assigned ToDo items by
 * @return a list of distinct ToDo entities assigned to the specified user where `archived` is false
 */
List<ToDo> findDistinctByUsers_UsernameAndArchivedFalse(String username);
}