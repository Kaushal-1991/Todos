package com.todo.contoller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.dto.TodoDto;
import com.todo.service.TodoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
		TodoDto addTodoDto = todoService.addTodo(todoDto);
		return new ResponseEntity<>(addTodoDto,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping
	public ResponseEntity<List<TodoDto>> getAllTodo(){
		List<TodoDto> dto =  todoService.getAllTodo();
		return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("{id}")
	public ResponseEntity<TodoDto> getTodo(@PathVariable Long id){
		TodoDto dto= todoService.getTodo(id);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("{id}")
	public ResponseEntity<TodoDto> updateDto(@PathVariable Long id,@RequestBody TodoDto dto){
		TodoDto todoDto = todoService.updateDto(id,dto);
		return new ResponseEntity<>(todoDto,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable Long id){
		todoService.deleteTodo(id);
		return ResponseEntity.ok("Todo deleted successfully");
	}
}
