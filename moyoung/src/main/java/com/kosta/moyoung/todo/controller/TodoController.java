package com.kosta.moyoung.todo.controller;

import com.kosta.moyoung.todo.entity.Todo;
import com.kosta.moyoung.todo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {
	private final TodoRepository repository;
	
	TodoController(TodoRepository repository) {
		this.repository = repository;
	}
	
	//투두리스트 가져오기
	@GetMapping("/todos/{roomId}")
	List<Todo> all(@PathVariable Long roomId){
		return repository.findByRoomId(roomId);
	}
	
	//투두리스트 추가하기
	@PostMapping("/todos/{roomId}")
	Todo newTodo(@PathVariable Long roomId, @RequestBody Todo newTodo){
		newTodo.setRoomId(roomId);
		newTodo.setCompleted((long) 0);
		return repository.save(newTodo);
	}
	
	//투두리스트 삭제하기
	@DeleteMapping("/todos/{id}")
	void deleteTodo(@PathVariable Long id) {
		repository.deleteById(id);
	}
	
	//투두리스트 수정하기
	@PutMapping("/todos/{id}")
	Todo updateTodo(@PathVariable Long id, @RequestBody Todo newTodo) {
		return repository.findById(id)
			.map(todo -> {
				todo.setContent(newTodo.getContent()); // 'content'로 변경
				todo.setCompleted(newTodo.getCompleted()); // 'completed' 추가
				return repository.save(todo);
			})
			.orElseGet(() -> {
				newTodo.setId(id);
				return repository.save(newTodo);
			});
	}
}
