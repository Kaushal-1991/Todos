package com.todo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.dto.TodoDto;
import com.todo.entity.Todo;
import com.todo.exception.ResourceNotFoundException;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		Todo todo = modelMapper.map(todoDto, Todo.class);
		Todo saveTodo = todoRepository.save(todo);
		return modelMapper.map(saveTodo, TodoDto.class);
	}

	@Override
	public List<TodoDto> getAllTodo() {
		List<Todo> findAll = todoRepository.findAll();
		return findAll.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
	}

	@Override
	public TodoDto getTodo(Long id) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo is not found with id:-"+id));
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public TodoDto updateDto(Long id, TodoDto dto) {
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo is not found with id:-"+id));
		
		todo.setTitle(dto.getTitle());
		todo.setDescription(dto.getDescription());
		todo.setCompleted(dto.getCompleted());
		
		todoRepository.save(todo);
		
		return modelMapper.map(todo, TodoDto.class);
	}

	@Override
	public void deleteTodo(Long id) {
		// TODO Auto-generated method stub
		Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo is not found with id:-"+id));
		todoRepository.delete(todo);
	}
	
	

}
