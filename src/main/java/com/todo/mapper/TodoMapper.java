package com.todo.mapper;

import com.todo.dto.TodoDto;
import com.todo.entity.Todo;

public class TodoMapper {
   public static TodoDto mapToDto(Todo todo) {
	   return new TodoDto(
		  todo.getId(),
		  todo.getTitle(),
		  todo.getDescription(),
		  todo.getCompleted()
	   );
   }
   
   public static Todo mapToEntity(TodoDto todoDto) {
	   return new Todo(
		   todoDto.getId(),
		   todoDto.getTitle(),
		   todoDto.getDescription(),
		   todoDto.getCompleted()
	   );
   }
}
