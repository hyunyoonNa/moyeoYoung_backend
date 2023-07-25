package com.kosta.moyoung.todo.repository;

import com.kosta.moyoung.todo.entity.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findByRoomId(Long roomId);

}
