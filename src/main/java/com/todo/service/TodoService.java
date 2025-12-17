package com.todo.service;

import java.util.List;

import com.todo.dto.TodoDto;

public interface TodoService {
   TodoDto addTodo(TodoDto todoDto);
   List<TodoDto> getAllTodo();
   TodoDto getTodo(Long id);
   TodoDto updateDto(Long id, TodoDto dto);
   void deleteTodo(Long id);

}
